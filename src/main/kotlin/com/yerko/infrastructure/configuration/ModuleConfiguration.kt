package com.yerko.infrastructure.configuration

import com.yerko.application.rest.HealthCheckResource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

object ModulesConfiguration {
    private val healthCheckModule = Kodein.Module("HealthCheck") {
        bind() from singleton { HealthCheckResource() }
    }

    internal val kodein = Kodein {
        import(healthCheckModule)
    }
}
