package io.moonshard.typhon.services

import io.moonshard.typhon.repository.ModuleRepository
import org.springframework.stereotype.Service

@Service
class ModuleService(val moduleRepository: ModuleRepository)