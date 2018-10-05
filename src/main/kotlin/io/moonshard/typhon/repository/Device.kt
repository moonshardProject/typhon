package io.moonshard.typhon.repository

import io.moonshard.typhon.models.Device
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface DeviceRepository : ReactiveMongoRepository<Device, String> {
    fun findByUserId(userId: ObjectId): Flux<Device>
}