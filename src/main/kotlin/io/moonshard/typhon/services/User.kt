package io.moonshard.typhon.services

import io.moonshard.typhon.models.User
import io.moonshard.typhon.repository.UserRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

class EmailAlreadyExists : Exception("EmailAlreadyExists")
class PhoneNumberAlreadyExists : Exception("PhoneNumberAlreadyExists")
class DuplicateInfo(msg: String) : Exception(msg)


fun hashPassword(password: String) = BCrypt.hashpw(password, BCrypt.gensalt())


@Service
class UserService(val userRepository: UserRepository) {
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