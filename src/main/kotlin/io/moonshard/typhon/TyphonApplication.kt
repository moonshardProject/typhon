package io.moonshard.typhon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TyphonApplication

fun main(args: Array<String>) {
    runApplication<TyphonApplication>(*args)
}
