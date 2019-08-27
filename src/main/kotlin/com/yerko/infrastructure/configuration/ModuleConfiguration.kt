package com.yerko.infrastructure.configuration

import com.yerko.application.rest.HealthCheckResource
import com.yerko.application.rest.account.AccountResource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

object ModulesConfiguration {
    private val healthCheckModule = Kodein.Module("HealthCheck") {
        bind() from singleton { HealthCheckResource() }
    }

    private val accountResourceModule = Kodein.Module("AccountResource") {
        bind() from singleton { AccountResource() }
    }

    internal val kodein = Kodein {
        import(healthCheckModule)
        import(accountResourceModule)
    }
}
