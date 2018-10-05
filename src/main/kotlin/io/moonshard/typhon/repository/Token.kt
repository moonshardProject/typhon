package io.moonshard.typhon.repository

import io.moonshard.typhon.models.Token
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface TokenRepository : ReactiveMongoRepository<Token, String> {
    fun findByData(data: String): Mono<Token>
}