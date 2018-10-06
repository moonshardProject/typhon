package io.moonshard.typhon.controllers

import io.moonshard.typhon.User
import io.moonshard.typhon.services.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

class LoginReq(var email: String?, var password: String?)
class RequestIsNotValid : Exception()
class NewTokenRequest(var email: String? = null,
                      var password: String? = null)


@RestController("/user")
class UserController(val userService: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody req: User): Mono<Any> {
        return if (!req.isValid()) {
            Mono.just(RequestIsNotValid())
        } else {
            userService.register(req)
        }

    }
    @PostMapping("/token/new")
    fun newTokenRequest(@RequestBody req: NewTokenRequest) {

    }
}