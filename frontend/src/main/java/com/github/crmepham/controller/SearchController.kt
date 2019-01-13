package com.github.crmepham.controller

import com.github.crmepham.model.Provider
import com.github.crmepham.model.SearchFilter
import com.github.crmepham.service.ProviderService
import com.github.crmepham.util.ProviderUtils
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
        model.addAttribute("countryCodes", Locale.getISOCountries())
        model.addAttribute("types", ProviderUtils.getAllTypes())
        model.addAttribute("showRegister", true)
        model.addAttribute("showSearch", true)
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