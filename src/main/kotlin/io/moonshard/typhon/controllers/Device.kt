package io.moonshard.typhon.controllers

import io.moonshard.typhon.services.DeviceService
import org.springframework.web.bind.annotation.RestController

@RestController
class DeviceController(val deviceService: DeviceService){}