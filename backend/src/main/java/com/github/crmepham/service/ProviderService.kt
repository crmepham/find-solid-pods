package com.github.crmepham.service

import com.github.crmepham.model.Provider
import com.github.crmepham.repository.ProviderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class ProviderService(private val providerRepository: ProviderRepository) {
    fun getByUriAndType(uri: String, type: String) : Provider? {
        return providerRepository.findByUriAndTypeAndActiveTrue(uri, type)
    }

    fun persist(provider: Provider) = providerRepository.save(provider)
}