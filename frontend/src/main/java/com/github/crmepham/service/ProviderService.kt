package com.github.crmepham.service

import com.github.crmepham.model.Provider
import com.github.crmepham.model.SearchFilter
import com.github.crmepham.repository.ProviderRepository
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

