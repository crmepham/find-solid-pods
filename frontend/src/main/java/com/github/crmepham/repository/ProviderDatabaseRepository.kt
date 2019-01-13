package com.github.crmepham.repository

import com.github.crmepham.model.DefaultProperties
import com.github.crmepham.model.Provider
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

const val GET_DB = "provider/get-from-db"

/** Fetches providers from the database. These has usually been registered on the website. */
@Component
open class ProviderDatabaseRepository(private val properties: DefaultProperties, private val restTemplate: RestTemplate) : BaseRepository() {
    fun getProvider(uri: String?, type: String?) : Provider? {
        return restTemplate.exchange(properties.backend + GET_DB, HttpMethod.POST, postJson(hashMapOf("uri" to uri, "type" to type)), object :
                ParameterizedTypeReference<Provider>() {}).body
    }
}

