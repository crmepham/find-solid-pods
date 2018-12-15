package com.github.final60.controller

import com.github.final60.model.Provider
import com.github.final60.model.SearchFilter
import com.github.final60.service.ElasticSearchService
import com.github.final60.service.ElasticSearchService.Companion.PROVIDER
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProviderController(private val elasticSearchService: ElasticSearchService) {

    @GetMapping("/provider/all")
    fun all() = elasticSearchService.list(Provider::class.java, PROVIDER, SearchFilter())

    @PostMapping("/provider/filtered")
    fun filtered(@RequestBody() filter: SearchFilter) = elasticSearchService.list(Provider::class.java, PROVIDER, filter)
}

