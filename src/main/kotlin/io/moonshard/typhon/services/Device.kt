package io.moonshard.typhon.services

import io.moonshard.typhon.*
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.Exception

class AddModuleRequest(email: String? = null, token: String? = null, moduleName: String? = null)

@Service
class DeviceService(val deviceRepository: DeviceRepository,
                    val eventRepository: DeviceRepository,
                    val userService: UserService,
                    val moduleRepository: ModuleRepository) {
    fun allDevices(email: String, token: String) {
        userService
                .isValid(TokenValidRequest(email, token))
                .doOnError {
                    "${it.message}"
                }
                .map {
                    val token: Token? = it as Token
                    deviceRepository
                            .findByUserId(it.UserId!!)
                }
    }

    fun addModule(email: String, token: String, moduleName: String): Mono<*> {
        return userService.isValid(TokenValidRequest(email, token))
                .doOnError {
                    Mono.just("${it.message}")
                }
                .map {
                    val user: Token? = it as Token
                    val module = Module(moduleId = null, userId = it.UserId, moduleName = moduleName)
                    moduleRepository.save(module)
                }
    }

    fun allEvents(email: String, token: String): Mono<Flux<Device>> {
        return userService.isValid(TokenValidRequest(email, token))
                .doOnError { Mono.just("${it.message}") }
                .map {
                    val user: User? = it as User
                    eventRepository
                            .findByUserId(it.userId!!)
                            .switchIfEmpty(Mono.error(Exception("NoEventFound")))
                            .doOnError { Mono.just("${it.message}") }
                }
    }

}