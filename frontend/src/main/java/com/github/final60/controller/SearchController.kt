package com.github.final60.controller

import com.github.final60.model.Provider
import com.github.final60.model.SearchFilter
import com.github.final60.service.ProviderService
import com.github.final60.util.ProviderUtils
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
class SearchController(private val providerService: ProviderService) {

    @GetMapping("/")
    fun home(model: Model) : String {
        model.addAttribute("countries", Locale.getISOCountries())
        model.addAttribute("types", ProviderUtils.getAllTypes())
        return "search"
    }

    @PostMapping("/search")
    @ResponseBody
    fun search(model: Model,
               @RequestParam("type") type: String,
               @RequestParam("country") country: String,
               @RequestParam("term") term: String) : ModelAndView {

        val providers = providerService.getFilteredProviders(SearchFilter(type, country, term))
        val model = HashMap<String, Set<Provider>>()
        model.put("providers", providers)
        return ModelAndView("search/results", "providers", providers)
    }

}