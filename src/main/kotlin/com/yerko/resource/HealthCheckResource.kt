package com.yerko.resource

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
class HealthCheckResource {

    @Get("/health")
    fun health() : String {
        return "OK"
    }
}
