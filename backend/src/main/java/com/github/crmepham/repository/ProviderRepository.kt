package com.github.crmepham.repository

import com.github.crmepham.model.Provider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProviderRepository : JpaRepository<Provider, Long> {
    fun findByUriAndTypeAndActiveTrue(uri: String, type: String) : Provider?
    fun findByIndexedFalseAndActiveTrue() : Set<Provider>
}