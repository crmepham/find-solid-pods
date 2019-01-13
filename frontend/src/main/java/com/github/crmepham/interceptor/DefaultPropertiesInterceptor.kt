package com.github.crmepham.interceptor

import com.github.crmepham.model.DefaultProperties
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class DefaultPropertiesInterceptor(private val properties: DefaultProperties) : HandlerInterceptor {

    override fun postHandle(request: HttpServletRequest?,
                            response: HttpServletResponse?, handler: Any?,
                            modelAndView: ModelAndView?) {

        modelAndView?.addObject("properties", properties)

    }
}