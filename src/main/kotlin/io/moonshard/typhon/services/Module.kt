package io.moonshard.typhon.services

import io.moonshard.typhon.models.Event
import io.moonshard.typhon.models.Module
import io.moonshard.typhon.models.User
import io.moonshard.typhon.repository.EventRepository
import io.moonshard.typhon.repository.ModuleRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.Exception

class AddModuleRequest(email: String? = null, token: String? = null, moduleName: String? = null)


@Service
class ModuleService(val moduleRepository: ModuleRepository, val tokenService: TokenService, val eventRepository: EventRepository) {
    fun add(email: String, token: String, moduleName: String): Mono<*> {

        return tokenService.isValid(TokenValidRequest(email, token))
                .doOnError {
                    Mono.just("${it.message}")
                }
                .map {
                    val user: User? = it as User
                    val module = Module(moduleId = null, userId = it.userId, moduleName = moduleName)
                    moduleRepository.save(module)
                }
    }

    fun events(email: String, token: String): Mono<Flux<Event>> {
        return tokenService.isValid(TokenValidRequest(email, token))
                .doOnError { Mono.just("${it.message}") }
                .map {
                    val user : User? = it as User
                    eventRepository
                            .findByUserId(it.userId!!)
                            .switchIfEmpty(Mono.error(Exception("NoEventFound")))
                            .doOnError {Mono.just("${it.message}")}
                }
    }
}