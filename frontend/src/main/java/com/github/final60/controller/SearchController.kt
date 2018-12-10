package com.github.final60.controller

import com.github.final60.service.ProviderService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SearchController(private val providerService: ProviderService) {

    @GetMapping("/")
    fun search() : String {

        val providers = providerService.getAllProviders()

        return "search"
    }

}