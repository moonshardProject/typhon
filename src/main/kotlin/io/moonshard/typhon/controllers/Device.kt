package io.moonshard.typhon.controllers

import io.moonshard.typhon.Validator
import io.moonshard.typhon.services.DeviceService
import org.springframework.web.bind.annotation.RestController

@RestController
class DeviceController(val deviceService: DeviceService){
    fun getAllModules(){

    }
    fun getAllDevices(){}
    fun sendCommand(){}
    fun getModule(){}
    fun addModule() {}
    fun getEvents() {}
}