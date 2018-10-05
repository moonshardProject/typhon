package io.moonshard.typhon.services

import io.moonshard.typhon.controllers.NewTokenRequest
import io.moonshard.typhon.models.Token
import io.moonshard.typhon.repository.TokenRepository
import io.moonshard.typhon.repository.UserRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*


class PasswordIsNotValid : Exception("PasswordIsNotValid")
class TokenNotFound : Exception("TokenNotFound")
class NotFound(param: String) : Exception("%sNotFound".format(param))
class TokenValidRequest(var email: String? = null, var token: String? = null)
class TokenIsNotValid : Exception("TokenIsNotValid")


fun checkPassword(candid: String, password: String) = BCrypt.checkpw(candid, password)
fun randomString() = UUID.randomUUID().toString().replace("-", "")

@Service
class TokenService(val tokenRepository: TokenRepository,
                   val userRepository: UserRepository) {
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
}

