package io.moonshard.typhon.repository

import io.moonshard.typhon.models.Device
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DeviceRepository: ReactiveMongoRepository<Device, String>