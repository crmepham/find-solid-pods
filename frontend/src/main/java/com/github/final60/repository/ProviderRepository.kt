package com.github.final60.repository

import com.github.final60.model.Provider
import com.github.final60.model.ProviderProperties
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

const val GET_ALL = "provider/all"

@Repository
class ProviderRepository(private val properties: ProviderProperties, private val restTemplate: RestTemplate) {

    fun getAll() = restTemplate.exchange(properties.backend + GET_ALL, HttpMethod.GET, HttpEntity<Any>(HttpHeaders()), object :
                   ParameterizedTypeReference<Set<Provider>>() {}).body
}

