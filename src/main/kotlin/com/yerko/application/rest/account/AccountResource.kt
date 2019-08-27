package com.yerko.application.rest.account
import com.yerko.domain.account.command.CreateAccount
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.util.*

class AccountResource {
    private val log = LoggerFactory.getLogger(AccountResource::class.java)

    suspend fun create(context: ApplicationCall) {
        val createAccountRequest = context.receive<CreateAccountRequest>()
        val createAccount = CreateAccount(UUID.randomUUID(),
            createAccountRequest.balance,
            createAccountRequest.customerId,
            LocalDateTime.now())
        log.info("Creating account for customer {}", createAccount.customerId)
        val accountResponse = AccountCreatedResponse(createAccount.accountId)
        context.respond(HttpStatusCode.Created, accountResponse)
    }

}
