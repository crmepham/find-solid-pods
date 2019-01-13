package com.github.crmepham.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/** The provider properties. */
@Component
@ConfigurationProperties(prefix = "provider")
class ProviderProperties {

    /** The list of POD provider sources. */
    val sources: List<String>? = ArrayList()
}

