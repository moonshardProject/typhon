package io.moonshard.typhon.services

import io.moonshard.typhon.repository.DeviceRepository
import org.springframework.stereotype.Service


@Service
class DeviceService(val deviceRepository: DeviceRepository) {}