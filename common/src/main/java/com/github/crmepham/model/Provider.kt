package com.github.crmepham.model

import com.google.gson.annotations.SerializedName
import java.util.*
import javax.persistence.*

/**
 * Encapsulates a single POD service provider.
 *
 * @author Chris Mepham
 */
@Entity
class Provider {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", unique = true, nullable = false, columnDefinition = "bigint(11)")
        var id: Long? = null
        @Column(name = "uri")
        @SerializedName("url") var uri: String? = null
        @Column(name = "icon_background_color")
        @SerializedName("icon_bg") var iconBackgroundColor: String? = null
        @Column(name = "title_color")
        @SerializedName("title_color") var titleColor: String? = null
        @Column(name = "policy_uri")
        @SerializedName("policyURL") var policyUri: String? = null
        @Column(name = "button_background_color")
        @SerializedName("btn_bg") var buttonBackgroundColor: String? = null
        @Column(name = "button_color")
        @SerializedName("btn_color") var buttonColor: String? = null
        @Column(name = "icon_uri")
        @SerializedName("icon") var iconUri: String? = null
        @Column(name = "title")
        var title: String? = null
        @Column(name = "description")
        var description: String? = null
        @Column(name = "type")
        var type: String? = null
        @Column(name = "country_code")
        var countryCode: String? = null
        @Column(name = "active")
        var active: Boolean = false
        @Column(name = "indexed")
        var indexed: Boolean = false
        @Column(name = "created")
        val created: Date = Date()
}


