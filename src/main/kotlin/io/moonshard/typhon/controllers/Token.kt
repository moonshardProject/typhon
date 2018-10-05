package io.moonshard.typhon.controllers

import io.moonshard.typhon.services.TokenService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


class NewTokenRequest(var email: String? = null,
                      var password: String? = null)

@RestController
class TokenController(var tokenService: TokenService) {

    fun new(@RequestBody req: NewTokenRequest) {

    }
}