package com.github.final60.repository

import com.github.final60.model.Provider
import com.github.final60.model.ProviderProperties
import com.github.final60.model.SearchFilter
import com.google.gson.Gson
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

const val GET_ALL = "provider/all"
const val GET_FILTERED = "provider/filtered"

@Repository
class ProviderRepository(private val properties: ProviderProperties, private val restTemplate: RestTemplate) {

    fun getAll() = restTemplate.exchange(properties.backend + GET_ALL, HttpMethod.GET, HttpEntity<Any>(HttpHeaders()), object :
                   ParameterizedTypeReference<Set<Provider>>() {}).body

    fun getFiltered(filter: SearchFilter) : Set<Provider> {

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        return restTemplate.exchange(properties.backend + GET_FILTERED,
                HttpMethod.POST, HttpEntity<Any>(Gson().toJson(filter), headers), object :
                ParameterizedTypeReference<Set<Provider>>() {}).body
    }
}

