package io.moonshard.typhon.controllers

import io.moonshard.typhon.services.ModuleService
import org.springframework.web.bind.annotation.RestController

@RestController
class ModuleController(val moduleService: ModuleService){
    fun addModule(){}
    //should get a date and return events after that date
    fun getEvents(){}
}