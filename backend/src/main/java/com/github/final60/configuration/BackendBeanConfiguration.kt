package com.github.final60.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
open class BackendBeanConfiguration {

    @Bean
    open fun restTemplate() = RestTemplate()

}

