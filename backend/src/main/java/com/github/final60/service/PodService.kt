package com.github.final60.service

import com.github.final60.model.Provider
import com.github.final60.model.ProviderProperties
import com.github.final60.model.ProviderWrapper
import com.github.final60.model.SearchFilter
import com.github.final60.service.ElasticSearchService.Companion.PROVIDER
import com.google.gson.Gson
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class PodService(private val properties: ProviderProperties, private val restTemplate: RestTemplate, private val elasticSearchService: ElasticSearchService) {

    private val logger = LoggerFactory.getLogger(PodService::class.java)

    fun update() {

        logger.debug("Retrieving providers from {} known sources.", properties.sources?.size)

        val newProviders = getNewProviders()

        logger.debug("Retrieved {} providers from {} known sources.", newProviders.size, properties.sources?.size)

        val knownProviders = elasticSearchService.list(Provider::class.java, PROVIDER, SearchFilter())

        val sum = newProviders + knownProviders

        val filtered = sum.groupBy { it.uri }
                          .filter { it.value.size == 1 }
                          .flatMap { it.value }

        if (filtered.isNotEmpty()) {
            elasticSearchService.bulkIndex(filtered.toMutableSet(), PROVIDER)
        }

        logger.debug("Persisted {} providers from {} known sources.", filtered, properties.sources?.size)
    }

    /**
     * Get a list of providers from each known source. This
     * currently only supports a single source from solid-idp-list.
     */
    private fun getNewProviders(): Set<Provider> {
        val providers = mutableSetOf<Provider>()
        properties.sources?.forEach { s -> providers.addAll(getProviders(s)) }
        return providers
    }

    /** Get a list of providers from a single remote source.  */
    private fun getProviders(uri: String): Set<Provider> {

        logger.debug("Retrieving providers from source {}.", properties.sources?.size)

        val res = restTemplate.exchange(uri, GET, HttpEntity<Any>(HttpHeaders()), object : ParameterizedTypeReference<String>() {})

        val wrapper = Gson().fromJson<ProviderWrapper>(res.body, ProviderWrapper::class.java)

        logger.debug("Retrieved {} providers from source {}.", wrapper.idps.size, uri)

        return wrapper.idps
    }
}
