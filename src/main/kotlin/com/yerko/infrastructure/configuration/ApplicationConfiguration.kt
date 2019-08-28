package com.yerko.infrastructure.configuration

import com.yerko.application.account.command.UnableToCreateAccountException
import com.yerko.application.account.query.AccountNotFoundException
import com.yerko.application.rest.HealthCheckResource
import com.yerko.application.rest.account.AccountResource
import com.yerko.application.rest.moneytransfer.MoneyTransferResource
import com.yerko.domain.moneytransfer.validator.exception.MoneyTransferValidationException
import com.yerko.infrastructure.configuration.routers.accountResourceRoutes
import com.yerko.infrastructure.configuration.routers.healthCheckRoutes
import com.yerko.infrastructure.configuration.routers.moneyTansferResourceRoutes
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.routing
import org.kodein.di.generic.instance

fun Application.mainModule() {
    val healthCheckResource by ModulesConfiguration.kodein.instance<HealthCheckResource>()
    val accountResource by ModulesConfiguration.kodein.instance<AccountResource>()
    val moneyTransferResource by ModulesConfiguration.kodein.instance<MoneyTransferResource>()

    install(DefaultHeaders)
    install(ContentNegotiation) {
        jackson {
        }
    }
    install(StatusPages) {
        exception<UnableToCreateAccountException> { cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message)
        }
        exception<MoneyTransferValidationException> { cause ->
            call.respond(HttpStatusCode.BadRequest,cause.message)
        }
        exception<AccountNotFoundException> { cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message)
        }
        exception<Exception> { cause ->
            cause.message?.let { call.respond(HttpStatusCode.InternalServerError, it) }
        }
    }

    DatabaseFactory.init()

    routing {
        healthCheckRoutes(healthCheckResource)
        accountResourceRoutes(accountResource)
        moneyTansferResourceRoutes(moneyTransferResource)
    }
}
