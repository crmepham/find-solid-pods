package com.github.crmepham.model

class RegisterForm {
    var title: String? = null
    var description: String? = null
    var uri: String? = null
    var type: String? = null
    var countryCode: String? = null
    var policyUri: String? = null
    var iconUri: String? = null
    var iconBackgroundColor: String? = null
    var buttonColor: String? = null
    var titleColor: String? = null
    var buttonBackgroundColor: String? = null

    var titleError: String? = null
    var descriptionError: String? = null
    var uriError: String? = null
    var policyUriError: String? = null
    var iconUriError: String? = null
    var iconBackgroundColorError: String? = null
    var buttonColorError: String? = null
    var titleColorError: String? = null
    var buttonBackgroundColorError: String? = null
    var duplicateError: String? = null
    var captchaError: String? = null

    var containsError: Boolean = false
    var success: Boolean = false
}