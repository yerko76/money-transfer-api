package com.yerko.infrastructure.configuration.routers

import com.yerko.application.rest.HealthCheckResource
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route

fun Routing.healthCheck(healthCheckResource: HealthCheckResource){
    route("/api/v1/health"){
        get { healthCheckResource.getHealthCheck(this.context) }
    }
}
