package io.moonshard.typhon.repository

import io.moonshard.typhon.models.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository : ReactiveMongoRepository<User, String> {
    fun findByEmail(email: String): Mono<User>
    fun findByPhoneNumber(phone: String): Mono<User>
}