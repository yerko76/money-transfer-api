package com.yerko.application.rest.account
import com.yerko.application.account.query.AccountQuery
import com.yerko.domain.account.command.CreateAccount
import com.yerko.domain.account.command.CreateAccountCommand
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import org.slf4j.LoggerFactory
import java.util.*

class AccountResource(private val accountCommand: CreateAccountCommand,
                      private val accountQuery: AccountQuery) {
    private val log = LoggerFactory.getLogger(AccountResource::class.java)

    suspend fun create(context: ApplicationCall) {
        val createAccountRequest = context.receive<CreateAccountRequest>()
        val createAccount = CreateAccount(createAccountRequest.balance, createAccountRequest.customerId)
        log.info("Creating account for customer {}", createAccount.customerId)
        val accountResponse = accountCommand.create(createAccount)
        log.info("Account with id {} created for customer {}", accountResponse,createAccount.customerId)
        context.respond(HttpStatusCode.Created, accountResponse)
    }

    suspend fun get(context: ApplicationCall) {
        val accountId = context.parameters["account-id"]
        log.info("Getting account information for account: {}", accountId)
        val accountInformation = accountQuery.findById(UUID.fromString(accountId))
        log.info("Returning account information for customer {}", accountInformation.customerId)
        context.respond(HttpStatusCode.OK, accountInformation)
    }

}
