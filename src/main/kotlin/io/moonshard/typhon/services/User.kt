package io.moonshard.typhon.services

import io.moonshard.typhon.repository.UserRepository
import org.springframework.stereotype.Service


@Service
class UserService(val userRepository: UserRepository) {}