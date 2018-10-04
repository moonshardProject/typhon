package io.moonshard.typhon.controllers

import io.moonshard.typhon.services.UserService
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userService:UserService){}