package com.github.crmepham.configuration

import com.github.crmepham.interceptor.DefaultPropertiesInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class InterceptorConfiguration(private val defaultPropertiesInterceptor: DefaultPropertiesInterceptor) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry?) {
        registry!!.addInterceptor(defaultPropertiesInterceptor)
    }
}