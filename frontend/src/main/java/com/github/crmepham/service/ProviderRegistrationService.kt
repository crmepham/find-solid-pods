package com.github.crmepham.service

import com.github.crmepham.model.Provider
import com.github.crmepham.model.RegisterForm
import com.github.crmepham.model.RegistrationProperties
import com.github.crmepham.repository.ProviderDatabaseRepository
import com.github.crmepham.repository.ProviderRepository
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils.hasText
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class ProviderRegistrationService(private val properties: RegistrationProperties,
                                  private val providerDatabaseRepository: ProviderDatabaseRepository,
                                  private val providerRepository: ProviderRepository,
                                  private val restTemplate: RestTemplate) {

    private val logger = LoggerFactory.getLogger(ProviderRegistrationService::class.java)

    fun validate(form: RegisterForm, captcha: String) {
        if (!hasText(captcha)) {
            form.containsError = true
            form.captchaError = properties.captchaError
            return
        }
        if (!hasText(form.title)) {
            form.containsError = true
            form.titleError = properties.titleError
        }
        if (!hasText(form.description)) {
            form.containsError = true
            form.descriptionError = properties.descriptionError
        }
        if (!hasText(form.uri)) {
            form.containsError = true
            form.uriError = properties.uriError
        }
        if (!hasText(form.policyUri)) {
            form.containsError = true
            form.policyUriError = properties.policyUriError
        }
        if (!validHexColor(form.buttonColor)) {
            form.containsError = true
            form.buttonColorError = properties.buttonColorError
        }
        if (!validHexColor(form.buttonBackgroundColor)) {
            form.containsError = true
            form.buttonBackgroundColorError = properties.buttonBackgroundColorError
        }
        if (!validHexColor(form.iconBackgroundColor)) {
            form.containsError = true
            form.iconBackgroundColorError = properties.iconBackgroundColorError
        }
        if (!validHexColor(form.titleColor)) {
            form.containsError = true
            form.titleColorError = properties.titleColorError
        }
        if (!validUri(form.uri)) {
            form.containsError = true
            form.uriError = properties.uriError
        }
        if (!validUri(form.policyUri)) {
            form.containsError = true
            form.policyUriError = properties.policyUriError
        }
        if (hasText(form.iconUri) && !validUri(form.iconUri)) {
            form.containsError = true
            form.iconUriError = properties.iconUriError
        }
        if (isDuplicate(form.uri, form.type)) {
            form.containsError = true
            form.duplicateError = String.format(properties.duplicateError, form.type, form.uri)
        }
        if (!form.containsError) {
            form.success = true
            logger.info(properties.success)
        }
    }

    fun persist(form: RegisterForm) = providerRepository.persist(bind(form))

    private fun bind(form: RegisterForm) : Provider {
        val provider = Provider()
        provider.active = true
        provider.buttonBackgroundColor = form.buttonBackgroundColor
        provider.buttonColor = form.buttonColor
        provider.countryCode = form.countryCode
        provider.description = form.description
        provider.titleColor = form.titleColor
        provider.iconUri = form.iconUri
        provider.type = form.type
        provider.uri = form.uri
        provider.policyUri = form.policyUri
        provider.iconBackgroundColor = form.iconBackgroundColor
        provider.iconUri = form.iconUri
        provider.title = form.title
        return provider
    }

    private fun isDuplicate(uri: String?, type: String?) : Boolean {
        return providerDatabaseRepository.getProvider(uri, type) != null
    }

    private fun validUri(input: String?) : Boolean {
        if (input == null || !input.startsWith("http://") && !input.startsWith("https://")) {
            return false
        }
        if (!uriExists(input)) {
            return false
        }
        return true
    }

    private fun uriExists(input: String) : Boolean {
        try {
            val res = restTemplate.exchange(input, HttpMethod.GET, HttpEntity<Any>(HttpHeaders()), object : ParameterizedTypeReference<String>() {})
            if (res.statusCodeValue != 200) {
                return false
            }
        } catch (e: HttpClientErrorException) {
            return false
        }
        return true
    }

    private fun validHexColor(input: String?) : Boolean {
        if (input == null || !input.startsWith('#')) {
            return false
        }
        if (input.length < 7 || input.length > 7) {
            return false
        }
        if (!input.matches(Regex("^(#[a-zA-Z0-9]+)"))) {
            return false
        }
        return true
    }

}

