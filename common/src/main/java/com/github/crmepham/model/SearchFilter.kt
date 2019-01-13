package com.github.crmepham.model

data class SearchFilter(val type: String?, val countryCode: String?, val term: String?) {
    constructor() : this(null, null, null)
}

