package com.github.final60.service

import com.github.final60.model.ElasticSearchQuery
import com.github.final60.model.Provider
import com.github.final60.model.ProviderProperties
import com.github.final60.model.ProviderWrapper
import com.github.final60.service.ElasticSearchService.Companion.PROVIDER
import com.google.gson.Gson
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.stream.Collectors.toList

@Service
class PodService(private val properties: ProviderProperties, private val restTemplate: RestTemplate, private val elasticSearchService: ElasticSearchService) {

    private val logger = LoggerFactory.getLogger(PodService::class.java)

    fun update() {

        logger.debug("Retrieving providers from {} known sources.", properties.sources?.size)

        // Get list of potentially new providers.
        val newProviders = getNewProviders()

        logger.debug("Retrieved {} providers from {} known sources.", newProviders.size, properties.sources?.size)

        val knownProviders = elasticSearchService.getAll(Provider::class.java, PROVIDER, ElasticSearchQuery())

        elasticSearchService.bulkDelete(PROVIDER)

        val filtered = mutableSetOf<Provider>()
        knownProviders.filterTo(filtered) {!knownProviders.stream()
                                                          .map(Provider::uri)
                                                          .collect(toList())
                                                          .contains(it.uri)}

        elasticSearchService.bulkIndex(filtered.toMutableSet(), PROVIDER)

        val knownProviders2 = elasticSearchService.getAll(Provider::class.java, PROVIDER, ElasticSearchQuery())

        println(knownProviders2.size)
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
