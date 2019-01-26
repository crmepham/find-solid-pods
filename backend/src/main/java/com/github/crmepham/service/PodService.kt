package com.github.crmepham.service

import com.github.crmepham.model.Provider
import com.github.crmepham.model.ProviderProperties
import com.github.crmepham.model.ProviderWrapper
import com.github.crmepham.model.SearchFilter
import com.github.crmepham.repository.ProviderRepository
import com.github.crmepham.service.ElasticSearchService.Companion.PROVIDER
import com.google.gson.Gson
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class PodService(private val properties: ProviderProperties,
                 private val restTemplate: RestTemplate,
                 private val elasticSearchService: ElasticSearchService,
                 private val providerRepository: ProviderRepository) {

    private val logger = LoggerFactory.getLogger(PodService::class.java)

    @Synchronized fun update() {

        logger.debug("Retrieving providers from {} known sources.", properties.sources?.size)

        val newProviders = getNewProviders()

        logger.debug("Retrieved {} providers from {} known sources.", newProviders.size, properties.sources?.size)

        val knownProviders = elasticSearchService.list(Provider::class.java, PROVIDER, SearchFilter())

        val sum = newProviders + knownProviders

        var filtered = sum.groupBy { it.uri }
                          .filter { it.value.size == 1 }
                          .flatMap { it.value }

        validate(filtered)

        val newDatabaseProviders = providerRepository.findByIndexedFalseAndActiveTrue()

        logger.debug("Found {} new active providers waiting to be indexed.", newDatabaseProviders.size)
        if (newDatabaseProviders.isEmpty()) {
            return
        }

        filtered = filtered.plus(newDatabaseProviders)

        if (filtered.isNotEmpty()) {
            filtered.forEach {
                it.indexed = true
                it.active = true
            }
            elasticSearchService.bulkIndex(filtered.toMutableSet(), PROVIDER)
        }

        if (newDatabaseProviders.isNotEmpty()) {
            providerRepository.saveAll(newDatabaseProviders)
            logger.info("Persisted {} providers from {} known sources.", filtered.size, properties.sources?.size)
        } else {
            logger.debug("Persisted {} providers from {} known sources.", filtered.size, properties.sources?.size)
        }

    }

    private fun validate(filtered: List<Provider>) {
        filtered.forEach{
            validateUri(it)
        }
    }

    private fun validateUri(provider: Provider) {
        val uri = provider.uri
        if (uri == null || !uri.startsWith("http://") && !uri.startsWith("https://")) {
            provider.active = false
        }
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
