package com.github.crmepham.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
open class FrontendBeanConfiguration {

    @Bean
    open fun restTemplate() = RestTemplate()

}

