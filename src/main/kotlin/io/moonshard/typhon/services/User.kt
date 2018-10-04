package io.moonshard.typhon.services

import io.moonshard.typhon.models.User
import io.moonshard.typhon.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


class EmailAlreadyExists : Exception("EmailAlreadyExists")
class PhoneNumberAlreadyExists : Exception("PhoneNumberAlreadyExists")
class DuplicateInfo(msg: String) : Exception(msg)

@Service
class UserService(val userRepository: UserRepository) {
    fun register(user: User) {
        checkForDuplicates(user)
                .map {
                    if (it is DuplicateInfo) {
                        it
                    } else {
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