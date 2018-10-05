package io.moonshard.typhon.repository

import io.moonshard.typhon.models.Event
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface EventRepository : ReactiveMongoRepository<Event, String> {
    fun findByUserId(userId: ObjectId): Flux<Event>
}