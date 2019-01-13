package com.github.crmepham.repository

import com.github.crmepham.model.DefaultProperties
import com.github.crmepham.model.Provider
import com.github.crmepham.model.SearchFilter
import com.google.gson.Gson
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

const val GET_ALL = "provider/all"
const val GET_FILTERED = "provider/filtered"
const val PERSIST = "provider/persist"

/** Fetches providers from ElasticSearch. */
@Component
open class ProviderRepository(private val properties: DefaultProperties, private val restTemplate: RestTemplate) : BaseRepository() {

    fun getAll() = restTemplate.exchange(properties.backend + GET_ALL, HttpMethod.GET, HttpEntity<Any>(HttpHeaders()), object :
                   ParameterizedTypeReference<Set<Provider>>() {}).body

    fun getFiltered(filter: SearchFilter) : Set<Provider> {

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        return restTemplate.exchange(properties.backend + GET_FILTERED,
                HttpMethod.POST, HttpEntity<Any>(Gson().toJson(filter), headers), object :
                ParameterizedTypeReference<Set<Provider>>() {}).body
    }

    fun persist(provider: Provider) = restTemplate.exchange(properties.backend + PERSIST, HttpMethod.POST, postJson(provider), Provider::class.java)
}

