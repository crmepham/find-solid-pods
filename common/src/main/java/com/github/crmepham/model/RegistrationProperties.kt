package com.github.crmepham.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/** The registration properties. */
@Component
@ConfigurationProperties(prefix = "registration")
class RegistrationProperties {

    /** Duplicate POD service error. */
    lateinit var duplicateError: String

    /** Title error. */
    lateinit var titleError: String

    /** Description error. */
    lateinit var descriptionError: String

    /** URI error. */
    lateinit var uriError: String

    /** Policy URI error. */
    lateinit var policyUriError: String

    /** Icon URI error. */
    lateinit var iconUriError: String

    /** Icon background color error. */
    lateinit var iconBackgroundColorError: String

    /** Button color error. */
    lateinit var buttonColorError: String

    /** Title color error. */
    lateinit var titleColorError: String

    /** Button background color error. */
    lateinit var buttonBackgroundColorError: String

    /** Captcha error. */
    lateinit var captchaError: String

    /** Success message. */
    lateinit var success: String
}

