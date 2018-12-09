package com.github.final60.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

open class BaseService

fun <T: Any> logger(clazz: KClass<T>): Logger {
    return LoggerFactory.getLogger(clazz::class.java)
}

