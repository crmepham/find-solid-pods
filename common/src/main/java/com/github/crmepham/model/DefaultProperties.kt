package com.github.crmepham.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/** The provider properties. */
@Component
@ConfigurationProperties(prefix = "default")
class DefaultProperties {

    /** The default provider button background color. */
    lateinit var buttonBackgroundColor: String

    /** The default provider button text color. */
    lateinit var buttonColor: String

    /** The default provider button text color. */
    lateinit var titleColor: String

    /** The default provider icon background color. */
    lateinit var iconBackgroundColor: String

    /** The base uri of the backend API. */
    lateinit var backend: String

    /** The Solid logo URI. */
    lateinit var solidLogoUri: String

    /** The Google captcha site key. */
    lateinit var captchaSiteKey: String

    /** The Google captcha secret key. */
    lateinit var captchaSecretKey: String
}

