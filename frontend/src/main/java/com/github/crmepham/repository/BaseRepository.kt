package com.github.crmepham.repository

import com.google.gson.Gson
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

abstract class BaseRepository {

    protected fun postJson(instance: Any): HttpEntity<*> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return HttpEntity(Gson().toJson(instance), headers)
    }
}