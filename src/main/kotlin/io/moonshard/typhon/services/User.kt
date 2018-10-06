package io.moonshard.typhon.services

import io.moonshard.typhon.Token
import io.moonshard.typhon.TokenRepository
import io.moonshard.typhon.User
import io.moonshard.typhon.UserRepository
import io.moonshard.typhon.NewTokenRequest
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

class EmailAlreadyExists : Exception("EmailAlreadyExists")
class PhoneNumberAlreadyExists : Exception("PhoneNumberAlreadyExists")
class DuplicateInfo(msg: String) : Exception(msg)
class PasswordIsNotValid : Exception("PasswordIsNotValid")
class TokenNotFound : Exception("TokenNotFound")
class NotFound(param: String) : Exception("%sNotFound".format(param))
class TokenValidRequest(var email: String? = null, var token: String? = null)
class TokenIsNotValid : Exception("TokenIsNotValid")


fun checkPassword(candid: String, password: String) = BCrypt.checkpw(candid, password)
fun randomString() = UUID.randomUUID().toString().replace("-", "")
fun hashPassword(password: String) = BCrypt.hashpw(password, BCrypt.gensalt())


@Service
class UserService(val userRepository: UserRepository,
                  val tokenRepository: TokenRepository) {
    fun register(user: User): Mono<Any> {
        return checkForDuplicates(user)
                .map {
                    if (it is DuplicateInfo) {
                        it
                    } else {
                        user.password = hashPassword(user.password!!)
                        userRepository.save(user)
                    }
                }
    }

    fun isValid(req: TokenValidRequest): Mono<Any> {
        return userRepository.findByEmail(req.email!!)
                .map {
                    val user = it
                    tokenRepository.findByData(req.token!!)
                            .map {
                                if (it.UserId!! != user.userId) {
                                    TokenIsNotValid()
                                } else {
                                    it
                                }
                            }
                            .switchIfEmpty(Mono.error(TokenNotFound()))
                }

    }

    fun new(req: NewTokenRequest): Mono<Any> {
        return userRepository.findByEmail(req.email!!)
                .map {
                    if (checkPassword(req.password!!, it.password!!)) {
                        tokenRepository.save(Token(it.userId!!, randomString()))
                    } else {
                        PasswordIsNotValid()
                    }
                }
                .switchIfEmpty(Mono.error(NotFound("email")))
    }

    fun checkForDuplicates(user: User): Mono<*> {
        return doesEmailExist(user.email!!)
                .map {
                    if (it is EmailAlreadyExists) {
                        DuplicateInfo(it.message!!)
                    } else {
                        doesPhoneNumberExist(user.phoneNumber!!)
                                .map {
                                    if (it is PhoneNumberAlreadyExists) {
                                        DuplicateInfo(it.message!!)
                                    }
                                }
                    }
                }
    }

    fun doesPhoneNumberExist(phone: String): Mono<*> {
        return userRepository.findByPhoneNumber(phone)
                .map {
                    PhoneNumberAlreadyExists()
                }

    }

    fun doesEmailExist(email: String): Mono<*> {
        return userRepository.findByEmail(email)
                .map {
                    EmailAlreadyExists()
                }

    }
}