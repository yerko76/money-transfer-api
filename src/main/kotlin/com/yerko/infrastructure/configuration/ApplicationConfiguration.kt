package com.yerko.infrastructure.configuration

import com.yerko.application.rest.HealthCheckResource
import com.yerko.application.rest.account.AccountResource
import com.yerko.infrastructure.configuration.routers.accountResourceRoutes
import com.yerko.infrastructure.configuration.routers.healthCheckRoutes
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import org.kodein.di.generic.instance

fun Application.mainModule() {
    val healthCheckResource by ModulesConfiguration.kodein.instance<HealthCheckResource>()
    val accountResource by ModulesConfiguration.kodein.instance<AccountResource>()

    install(DefaultHeaders)
    install(ContentNegotiation) {
        jackson {
        }
    }

    DatabaseFactory.init()

    routing {
        healthCheckRoutes(healthCheckResource)
        accountResourceRoutes(accountResource)
    }
}
