package com.github.crmepham.service

import com.github.crmepham.model.ElasticSearchProperties
import com.github.crmepham.model.RegistrationProperties
import com.github.crmepham.model.SearchFilter
import com.google.gson.GsonBuilder
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.support.WriteRequest
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.TransportAddress
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders.*
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.sort.SortOrder
import org.elasticsearch.transport.client.PreBuiltTransportClient
import org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import java.net.InetAddress
import java.net.InetSocketAddress
import java.util.Arrays.asList

/**
 * Contains all operations related to Elastic Search.
 *
 * @author Chris Mepham
 */
@Service
@Scope(SCOPE_SINGLETON)
class ElasticSearchService(private val properties: ElasticSearchProperties) {

    companion object {

        const val PROVIDER              = "provider"
        const val INDEX                 = "find-solid-pods"
        const val MAX_RESULTS           = 10000000
        const val RESULT_WINDOW         = 10000
        const val PROVIDER_URI          = "uri"
        const val PROVIDER_TYPE         = "type"
        const val PROVIDER_COUNTRY_CODE = "country_code"
        const val PROVIDER_ACTIVE       = "active"
        const val PROVIDER_TITLE        = "title"
        const val PROVIDER_DESCRIPTION  = "description"
        const val WILDCARD              = "*"
        const val SORT_SCORE            = "_score"
        const val GSON_DATE_FORMAT      = "yyyy-MM-dd"

    }

    /** The Elastic Search client. */
    private val client: TransportClient = initializeTransportClient()

    init {

        createIndex()
    }

    /** Get all the elements in the specified document. */
    fun <T> list(returnType: Class<T>, document: String, filter: SearchFilter) : Set<T> {
        val response = client.prepareSearch()
                             .setTypes(document)
                             .setQuery(queryBuilder(filter))
                             .addSort(SORT_SCORE, SortOrder.DESC)
                             .get()

        return inflate(returnType, response)
    }

    /** Index all of the specified elements in bulk. */
    fun <T> bulkIndex(elements: Set<T>, document: String) {
        val bulkRequest = client.prepareBulk()
        elements.forEach {
            bulkRequest.add(client.prepareIndex(INDEX, document).setSource(getSource(it), XContentType.JSON))
        }
        bulkRequest.get()
    }

    /** Delete all elements in the specified document. */
    fun bulkDelete(document: String) {

        val searchBuilder = client
                .prepareSearch()
                .setIndices(INDEX)
                .setTypes(document)
                .setQuery(matchAllQuery())
                .setSize(RESULT_WINDOW)

        var deleteResponse = searchBuilder.get()

        val brb = client.prepareBulk()

        while (deleteResponse.hits.totalHits > 0) {
            for (hit in deleteResponse.hits) {
                brb.add(DeleteRequest(hit.index, hit.type, hit.id))
            }

            brb.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE).get()

            deleteResponse = searchBuilder.get()
        }
    }

    /** Convert object to JSON. */
    private fun <T> getSource(element: T): String {
        return GsonBuilder().setDateFormat(GSON_DATE_FORMAT).create().toJson(element)
    }

    /** Inflate the hits into the required objects. */
    private fun <T> inflate(clazz: Class<T>, response: SearchResponse): Set<T> {
        val results = asList<SearchHit>(*response.hits.hits)
        val providers = mutableSetOf<T>()
        results.forEach {
            val gson = GsonBuilder().setDateFormat(GSON_DATE_FORMAT).create()
            providers.add(gson.fromJson(it.sourceAsString, clazz))
        }
        return providers
    }

    /** Dynamically build the Elastic Search query based on the supplied parameters. */
    private fun queryBuilder(filter: SearchFilter): BoolQueryBuilder {
        val queryBuilder                        = boolQuery()
        val uriWildCard                         = wildcardQuery(PROVIDER_URI, WILDCARD + filter.term + WILDCARD)
        val titleWildCard                       = wildcardQuery(PROVIDER_TITLE, WILDCARD + filter.term + WILDCARD)
        val descriptionWildCard                 = wildcardQuery(PROVIDER_DESCRIPTION, WILDCARD + filter.term + WILDCARD)
        val wildCardUriAndTitleAndDescription   = boolQuery().should(uriWildCard).should(titleWildCard).should(descriptionWildCard)

        if (!filter.term.isNullOrEmpty()) {
            queryBuilder.must(wildCardUriAndTitleAndDescription)
        }

        if (!filter.type.isNullOrEmpty() && !filter.type.equals("Any")) {
            queryBuilder.must(prefixQuery(PROVIDER_TYPE, filter.type))
        }

        if (!filter.countryCode.isNullOrEmpty() && !filter.countryCode.equals("Any")) {
            queryBuilder.must(prefixQuery(PROVIDER_COUNTRY_CODE, filter.countryCode))
        }

        queryBuilder.must(termQuery(PROVIDER_ACTIVE, true))

        return queryBuilder
    }

    /** Create the Elastic Search index. */
    private fun createIndex() {

        /*val deleteRequest =  DeleteIndexRequest(INDEX)
        client.admin().indices().delete(deleteRequest)*/

        val res = client.admin().indices().prepareExists(INDEX).get()
        if (!res.isExists) {
            val request = CreateIndexRequest(INDEX).settings(Settings.builder().put("index.max_result_window", MAX_RESULTS))
            client.admin().indices().create(request).actionGet()
            client.admin().indices().refresh(RefreshRequest(INDEX)).actionGet()
        }
    }

    /** Initialize the single transport client. */
    private fun initializeTransportClient() : TransportClient {
        return PreBuiltTransportClient(settings())
                .addTransportAddresses(TransportAddress(InetSocketAddress(InetAddress.getByName(properties.host), properties.port.toInt())))
    }

    /** Define the global Elastic Search settings here. */
    private fun settings(): Settings? {
        return Settings.builder().put("cluster.name", properties.clusterName).build()
    }
}