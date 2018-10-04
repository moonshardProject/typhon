package io.moonshard.typhon.controllers

import io.moonshard.typhon.models.User
import io.moonshard.typhon.services.UserService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono





@RestController
class UserController(val userService:UserService){
    fun register(@RequestBody req: User): Mono<*> {
        if (!req.isValid()) {
            // return error
        }
        return Mono.just("")
    }
}