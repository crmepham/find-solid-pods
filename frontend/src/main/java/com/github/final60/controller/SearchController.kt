package com.github.final60.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SearchController

    @GetMapping("/")
    fun search() = "search"


