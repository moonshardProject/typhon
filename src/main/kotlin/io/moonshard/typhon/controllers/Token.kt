package io.moonshard.typhon.controllers

import io.moonshard.typhon.services.TokenService
import org.springframework.web.bind.annotation.RestController

@RestController
class TokenController(var tokenService: TokenService)