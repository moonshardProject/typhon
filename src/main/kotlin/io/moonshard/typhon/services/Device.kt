package io.moonshard.typhon.services

import io.moonshard.typhon.models.Token
import io.moonshard.typhon.repository.DeviceRepository
import org.springframework.stereotype.Service


@Service
class DeviceService(val deviceRepository: DeviceRepository,
                    val tokenService: TokenService) {
    fun allDevices(email:String, token:String){
        tokenService
                .isValid(TokenValidRequest(email,token))
                .doOnError {
                    "${it.message}"
                }
                .map {
                    val token: Token? = it as Token
                    deviceRepository
                            .findByUserId(it.UserId!!)
                }
    }
}