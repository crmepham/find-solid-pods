package com.github.crmepham.controller

import com.github.crmepham.model.Provider
import com.github.crmepham.model.SearchFilter
import com.github.crmepham.service.ElasticSearchService
import com.github.crmepham.service.ElasticSearchService.Companion.PROVIDER
import com.github.crmepham.service.ProviderService
import com.google.gson.Gson
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ProviderController(private val elasticSearchService: ElasticSearchService, private val providerService: ProviderService) {

    @GetMapping("/provider/all")
    fun all() = elasticSearchService.list(Provider::class.java, PROVIDER, SearchFilter())

    @PostMapping("/provider/filtered")
    fun filtered(@RequestBody() filter: SearchFilter) = elasticSearchService.list(Provider::class.java, PROVIDER, filter)

    @PostMapping("/provider/get-from-db", consumes = ["application/json"])
    fun providerDb(@RequestBody() body: HashMap<String, String>) : ResponseEntity<Provider?> {
        return ResponseEntity(providerService.getByUriAndType(body.getOrDefault("uri", ""), body.getOrDefault("type", "")), HttpStatus.OK)
    }

    @PostMapping(value = "/provider/persist", consumes = ["application/json"])
    fun persist(@RequestBody payload: String) {
        providerService.persist(Gson().fromJson(payload, Provider::class.java))
    }
}

