package com.github.final60.model

import com.google.gson.annotations.SerializedName

/**
 * Encapsulates a single POD service provider.
 *
 * @author Chris Mepham
 */
class Provider(@SerializedName("url") val uri: String,
               @SerializedName("icon_bg") val iconBackground: String,
               @SerializedName("title_color") val titleColor: String,
               @SerializedName("policyURL") val policyUri: String,
               @SerializedName("btn_bg") val buttonBackground: String,
               @SerializedName("btn_color") val buttonColor: String,
               val title: String,
               val description: String,
               val icon: String,
               val type: String,
               val countryCode: String) {

    constructor() : this("","","","","","","","", "", "", "")
}


