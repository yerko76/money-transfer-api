package com.yerko.application.rest.moneytransfer


import com.yerko.application.moneytransfer.command.MoneyTransferCommandHandler
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import org.slf4j.LoggerFactory

class MoneyTransferResource(private val moneyMoneyTransferCommandHandler: MoneyTransferCommandHandler) {
    private val log = LoggerFactory.getLogger(MoneyTransferResource::class.java)

    suspend fun transfer(context: ApplicationCall) {
        val createAccountRequest = context.receive<CreateMoneyTransferRequest>()
        addLogForMoneyTransferRequest(createAccountRequest)
        val response = moneyMoneyTransferCommandHandler.transferMoney(createAccountRequest)
        addLogForMoneyTransferResponse(response)
        context.respond(HttpStatusCode.OK, response)
    }

    private fun addLogForMoneyTransferRequest(createAccountRequest: CreateMoneyTransferRequest) {
        log.info(
            "Request to transfer money from account {} to account {}",
            createAccountRequest.fromAccountId,
            createAccountRequest.toAccountId
        )
    }

    private fun addLogForMoneyTransferResponse(response: MoneyTransferResponse) {
        log.info(
            "Transfered {} from account {} to account {}",
            response.transferredAmount.amount,
            response.fromAccountId,
            response.toAccountId
        )
    }
}
