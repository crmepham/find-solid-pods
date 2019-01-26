package com.github.crmepham.controller

import com.github.crmepham.model.RegisterForm
import com.github.crmepham.service.ProviderRegistrationService
import com.github.crmepham.service.ProviderService
import com.github.crmepham.util.ProviderUtils
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.util.StringUtils.hasText
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
class RegisterController(private val providerService: ProviderService, private val providerRegistrationService: ProviderRegistrationService) {

    @GetMapping("/register")
    fun get(model: Model) : String {
        model.addAttribute("countryCodes", Locale.getISOCountries())
        model.addAttribute("types", ProviderUtils.getAllTypes())
        model.addAttribute("item", RegisterForm())
        return "register"
    }

    @PostMapping("/register")
    fun post(@ModelAttribute("registerForm") form: RegisterForm,  model: Model) : String {
        providerRegistrationService.validate(form)
        if (!form.containsError) {
            providerRegistrationService.persist(form)
        }
        model.addAttribute("countryCodes", Locale.getISOCountries())
        model.addAttribute("types", ProviderUtils.getAllTypes())
        model.addAttribute("item", form)
        return "register"
    }

}