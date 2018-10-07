package io.moonshard.typhon

import org.omg.CosNaming.NamingContextPackage.NotFound
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.Exception


class AddModuleRequest(email: String? = null, token: String? = null, moduleName: String? = null)

@Service
class Services(val deviceRepository: DeviceRepository,
               val tokenRepository: TokenRepository,
               val userRepository: UserRepository,
               val eventRepository: DeviceRepository,
               val moduleRepository: ModuleRepository) {
    fun allDevices(email: String, token: String) {
        isValid(TokenValidRequest(email, token))
                .doOnError {
                    "${it.message}"
                }
                .map {
                    val token: Token? = it as Token
                    deviceRepository
                            .findByUserId(it.UserId!!)
                }
    }

    fun addModule(email: String, token: String, moduleName: String): Mono<*> {
        return isValid(TokenValidRequest(email, token))
                .doOnError {
                    Mono.just("${it.message}")
                }
                .map {
                    val user: Token? = it as Token
                    val module = Module(moduleId = null, userId = it.UserId, moduleName = moduleName)
                    moduleRepository.save(module)
                }
    }

    fun allEvents(email: String, token: String): Mono<Flux<Device>> {
        return isValid(TokenValidRequest(email, token))
                .doOnError { Mono.just("${it.message}") }
                .map {
                    val user: User? = it as User
                    eventRepository
                            .findByUserId(it.userId!!)
                            .switchIfEmpty(Mono.error(Exception("NoEventFound")))
                            .doOnError { Mono.just("${it.message}") }
                }
    }

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
                        DuplicateInfo()
                    } else {
                        doesPhoneNumberExist(user.phoneNumber!!)
                                .map {
                                    if (it is PhoneNumberAlreadyExists) {
                                        DuplicateInfo()
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

class NotFound(param: String) : Exception("${param} not found")
class TokenNotFound : Exception("TokenIsNotFound")
class TokenIsNotValid : Exception("TokenIsNotValid")
class EmailAlreadyExists : Exception("EmailAlreadyExists")
class PhoneNumberAlreadyExists : Exception("PhoneNumberAlreadyExists")
class DuplicateInfo : Exception("DuplicateInfo")
class TokenValidRequest(val email: String, val token: String)
class PasswordIsNotValid : Exception("PasswordIsNotValid")
