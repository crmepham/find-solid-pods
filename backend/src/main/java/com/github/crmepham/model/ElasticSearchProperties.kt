package com.github.crmepham.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/** The provider properties. */
@Component
@ConfigurationProperties(prefix = "elasticsearch")
class ElasticSearchProperties {

    /** The cluster name. */
    lateinit var clusterName: String

    /** The host. */
    lateinit var host: String

    /** The port. */
    lateinit var port: Integer
}

