package io.moonshard.typhon.repository

import io.moonshard.typhon.models.Module
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ModuleRepository: ReactiveMongoRepository<Module, String>{
}