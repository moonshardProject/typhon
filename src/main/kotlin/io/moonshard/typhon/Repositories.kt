package io.moonshard.typhon

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface DeviceRepository : ReactiveMongoRepository<Device, String> {
    fun findByUserId(userId: ObjectId): Flux<Device>
}

@Repository
interface EventRepository : ReactiveMongoRepository<Event, String> {
    fun findByUserId(userId: ObjectId): Flux<Event>
}

@Repository
interface ModuleRepository: ReactiveMongoRepository<Module, String>{
}


@Repository
interface TokenRepository : ReactiveMongoRepository<Token, String> {
    fun findByData(data: String): Mono<Token>
}


@Repository
interface UserRepository : ReactiveMongoRepository<User, String> {
    fun findByEmail(email: String): Mono<User>
    fun findByPhoneNumber(phone: String): Mono<User>
}