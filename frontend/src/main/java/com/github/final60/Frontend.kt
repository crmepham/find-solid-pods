package com.github.final60

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class Frontend

fun main(args: Array<String>) {
    runApplication<Frontend>(*args)
}