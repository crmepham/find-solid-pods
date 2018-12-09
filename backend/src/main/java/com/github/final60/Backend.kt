package com.github.final60

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
open class Backend

fun main(args: Array<String>) {
    runApplication<Backend>(*args)
}

