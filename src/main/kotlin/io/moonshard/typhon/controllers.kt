package io.moonshard.typhon

import io.moonshard.typhon.services.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

class RequestIsNotValid : Exception()
class NewTokenRequest(var email: String? = null,
                      var password: String? = null)


@RestController
class UserController(val userService: UserService) {

    @PostMapping("/user/register")
    fun register(@RequestBody req: User): Mono<Any> {
        return if (!req.isValid()) {
            Mono.just(RequestIsNotValid())
        } else {
            userService.register(req)
        }

    }

    @PostMapping("/user/token/new")
    fun newTokenRequest(@RequestBody req: NewTokenRequest) {
    }

    @PostMapping("/modules/all")
    fun getAllModules() {
    }

    @PostMapping("/devices/all")
    fun getAllDevices() {
    }

    @PostMapping("/module/send_cmd")
    fun sendCommand() {
    }

    @PostMapping("/module/get")
    fun getModule() {
    }

    @PostMapping("/module")
    fun addModule() {
    }

    @PostMapping("/events/all")
    fun getEvents() {
    }
}