package com.yerko.application.rest.moneytransfer


import com.yerko.application.moneytransfer.command.MoneyTransferCommandHandler
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import org.slf4j.LoggerFactory

class MoneyTransferResource(private val moneyMoneyTransferCommandHandler: MoneyTransferCommandHandler) {
    private val log = LoggerFactory.getLogger(MoneyTransferResource::class.java)

    suspend fun create(context: ApplicationCall) {
        val createAccountRequest = context.receive<CreateMoneyTransferRequest>()
        log.info("Request to transfer money from account {} to account {}",
            createAccountRequest.fromAccountId,createAccountRequest.toAccountId)
        val response = moneyMoneyTransferCommandHandler.transferMoney(createAccountRequest)
        context.respond(HttpStatusCode.OK, response)
    }
}
