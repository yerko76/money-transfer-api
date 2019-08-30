package com.yerko.infrastructure.configuration

import com.yerko.application.account.command.UnableToCreateAccountException
import com.yerko.application.account.query.AccountNotFoundException
import com.yerko.application.rest.HealthCheckResource
import com.yerko.application.rest.account.AccountResource
import com.yerko.domain.moneytransfer.validator.exception.MoneyTransferValidationException
import com.yerko.infrastructure.configuration.routers.accountResourceRoutes
import com.yerko.infrastructure.configuration.routers.healthCheckRoutes
import com.yerko.infrastructure.persistance.PersistanceException
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.routing
import org.kodein.di.generic.instance
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

fun Application.mainModule() {
    val log = LoggerFactory.getLogger(Application::class.java)

    val healthCheckResource by ModulesConfiguration.kodein.instance<HealthCheckResource>()
    val accountResource by ModulesConfiguration.kodein.instance<AccountResource>()

    install(CallLogging) {
        level = Level.TRACE
        callIdMdc("X-Request-ID")
    }
    install(CallId) {
        generate(10)
    }


    install(DefaultHeaders)
    install(ContentNegotiation) {
        jackson {
        }
    }
    install(StatusPages) {
        exception<UnableToCreateAccountException> { cause ->
            log.error("Unable to create account due to {}", cause.message, cause)
            call.respond(HttpStatusCode.BadRequest, cause.message)
        }
        exception<MoneyTransferValidationException> { cause ->
            log.error("Unable to transfer money due to {}", cause.message, cause)
            call.respond(HttpStatusCode.BadRequest,cause.message)
        }
        exception<AccountNotFoundException> { cause ->
            log.error("Account does not exist, error was {}", cause.message, cause)
            call.respond(HttpStatusCode.BadRequest, cause.message)
        }
        exception<PersistanceException> { cause ->
            log.error("Error ocurred trying to save entity, error was {}", cause.message, cause)
            call.respond(HttpStatusCode.ServiceUnavailable, cause.message)
        }
        exception<Exception> { cause ->
            log.error("Unhandled error, due to  {}", cause.message, cause)
            cause.message?.let { call.respond(HttpStatusCode.InternalServerError, it) }
        }
    }

    DatabaseFactory.init()

    routing {
        healthCheckRoutes(healthCheckResource)
        accountResourceRoutes(accountResource)
    }
}
