package com.github.final60.model

import com.google.gson.annotations.SerializedName

/**
 * Encapsulates a single POD service provider.
 *
 * @author Chris Mepham
 */
class Provider {

    /** The URI that the user will be redirected to.  */
    @SerializedName("url")
    val uri: String? = null

    /** The URI of the icon.  */
    private val icon: Boolean? = false

    /** The color (e.g #FFFFFF) of the icon background.  */
    @SerializedName("icon_bg")
    private val iconBackground: String? = null

    /** The title of the service.  */
    private val title: String? = null

    /** The color (e.g. #FFFFFF) of the title text .  */
    @SerializedName("title_color")
    private val titleColor: String? = null

    /** The URI for where the user can read the service policy.  */
    @SerializedName("policyURL")
    private val policyUri: String? = null

    /** The description of the service.  */
    private val description: String? = null

    /** The color (e.g. #FFFFFF) of the button background.  */
    @SerializedName("btn_bg")
    private val buttonBackground: String? = null

    /** The color (e.g. #FFFFFF) of the button text.  */
    @SerializedName("btn_color")
    private val buttonColor: String? = null

}


