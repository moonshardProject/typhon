package io.moonshard.typhon.repository

import io.moonshard.typhon.models.Token
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository: ReactiveMongoRepository<Token, String>