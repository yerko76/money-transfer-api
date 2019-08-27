package com.yerko.infrastructure.configuration

import com.yerko.application.account.command.CreateAccountCommandHandler
import com.yerko.application.account.entity.AccountReadRepository
import com.yerko.application.account.entity.AccountWriteRepository
import com.yerko.application.account.query.AccountQuery
import com.yerko.application.rest.HealthCheckResource
import com.yerko.application.rest.account.AccountResource
import com.yerko.infrastructure.persistance.AccountRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

object ModulesConfiguration {
    private val healthCheckModule = Kodein.Module("HealthCheckModule") {
        bind() from singleton { HealthCheckResource() }
    }

    private val accountResourceModule = Kodein.Module("AccountModule") {
        bind<AccountWriteRepository>() with singleton { AccountRepository() }
        bind<AccountReadRepository>() with singleton { AccountRepository() }
        bind() from singleton { AccountQuery(instance()) }
        bind() from singleton { AccountResource(instance(), instance()) }
        bind() from singleton { CreateAccountCommandHandler(instance()) }
    }

    internal val kodein = Kodein {
        import(healthCheckModule)
        import(accountResourceModule)
    }
}
