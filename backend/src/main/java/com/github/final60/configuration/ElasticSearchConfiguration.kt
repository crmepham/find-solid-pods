/*package com.github.final60.configuration

import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.TransportAddress
import org.elasticsearch.transport.client.PreBuiltTransportClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
import java.net.InetAddress
import java.net.InetSocketAddress


@Configuration
@EnableElasticsearchRepositories(basePackages = ["com.github.final60.repository"])
open class ElasticSearchConfiguration {

    @Value("\${elasticsearch.home:/usr/local/Cellar/elasticsearch/5.6.0}")
    private val elasticsearchHome: String? = null

    @Value("\${elasticsearch.host}")
    private val host: String? = "localhost"

    @Value("\${elasticsearch.port}")
    private val port: Int = 9300

    @Value("\${elasticsearch.clustername}")
    private val clusterName: String? = "find-solid-pods-cluster"

    @Bean
    @Throws(Exception::class)
    open fun client(): Client {
        val settings = Settings.builder()
                .put("client.transport.sniff", true)
                .put("path.home", elasticsearchHome)
                .put("cluster.name", clusterName).build()
        return PreBuiltTransportClient(settings).addTransportAddress(TransportAddress(InetSocketAddress(InetAddress.getByName(host), port)))
    }

    @Bean
    @Throws(Exception::class)
    open fun elasticsearchTemplate() = ElasticsearchTemplate(client())
}*/

