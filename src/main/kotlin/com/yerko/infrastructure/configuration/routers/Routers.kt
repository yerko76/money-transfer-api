package com.yerko.infrastructure.configuration.routers

import com.yerko.application.rest.HealthCheckResource
import com.yerko.application.rest.account.AccountResource
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

fun Routing.healthCheckRoutes(healthCheckResource: HealthCheckResource){
    route("/api/v1/health"){
        get { healthCheckResource.getHealthCheck(this.context) }
    }
}

fun Routing.accountResourceRoutes(accountResource: AccountResource){
    route("/api/v1/accounts"){
        post { accountResource.create(this.context) }
    }
    route("/api/v1/accounts/{account-id}") {
        get { accountResource.get(this.context) }
    }
}
