package com.github.final60.service

import com.github.final60.model.Provider
import com.github.final60.model.SearchFilter
import com.github.final60.repository.ProviderRepository
import org.springframework.stereotype.Service

@Service
class ProviderService(private val providerRepository: ProviderRepository) {

    /** Fetch all known providers. */
    fun getAllProviders() : Set<Provider> {
        return providerRepository.getAll()
    }

    /** Fetch filtered providers. */
    fun getFilteredProviders(filter: SearchFilter) : Set<Provider> {
        return providerRepository.getFiltered(filter)
    }

}

