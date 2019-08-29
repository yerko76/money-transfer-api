package com.yerko.application.rest.account
import com.yerko.application.account.query.AccountQueryHandler
import com.yerko.application.moneytransfer.command.MoneyTransferCommandHandler
import com.yerko.application.rest.moneytransfer.CreateMoneyTransferRequest
import com.yerko.application.rest.moneytransfer.MoneyTransferResponse
import com.yerko.domain.account.command.CreateAccount
import com.yerko.domain.account.command.CreateAccountCommand
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import org.slf4j.LoggerFactory
import java.util.*

class AccountResource(private val accountCommand: CreateAccountCommand,
                      private val accountQueryHandler: AccountQueryHandler,
                      private val moneyMoneyTransferCommandHandler: MoneyTransferCommandHandler) {

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
        val accountInformation = accountQueryHandler.findById(UUID.fromString(accountId))
        log.info("Returning account information for customer {}", accountInformation.customerId)
        context.respond(HttpStatusCode.OK, AccountInformationResponse(accountInformation))
    }

    suspend fun transfer(context: ApplicationCall) {
        val accountId = UUID.fromString(context.parameters["account-id"])
        val request = context.receive<CreateMoneyTransferRequest>()
        if (accountId != null) {
            addLogForMoneyTransferRequest(accountId, request.toAccountId)
        }
        val response = moneyMoneyTransferCommandHandler.transferMoney(
            CreateMoneyTransferRequest(accountId, request.toAccountId, request.amount))
        addLogForMoneyTransferResponse(response)
        context.respond(HttpStatusCode.OK, response)
    }

    private fun addLogForMoneyTransferRequest(accountId:UUID, toAccountId: UUID) {
        log.info(
            "Request to transfer money from account {} to account {}",
            accountId,
            toAccountId
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
