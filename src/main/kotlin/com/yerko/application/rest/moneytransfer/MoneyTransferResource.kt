package com.yerko.application.rest.moneytransfer


import io.ktor.application.ApplicationCall
import io.ktor.request.receive
import org.slf4j.LoggerFactory

class MoneyTransferResource {
    private val log = LoggerFactory.getLogger(MoneyTransferResource::class.java)

    suspend fun create(context: ApplicationCall) {
        val createAccountRequest = context.receive<CreateMoneyTransferRequest>()
//        val createMoneyTransfer = CreateMoneyTransfer(createAccountRequest.originAccount, createAccountRequest.destinationAcco
//        Money(createAccountRequest.amount))

//        log.info("Money transfered from account {} to account {}", accountResponse,createAccount.customerId)
//        context.respond(HttpStatusCode.OK, accountResponse)
    }
}
