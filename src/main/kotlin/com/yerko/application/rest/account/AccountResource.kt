package com.yerko.application.rest.account
import com.yerko.domain.account.command.CreateAccount
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.util.*

class AccountResource {
    private val log = LoggerFactory.getLogger(AccountResource::class.java)

    fun create(account: CreateAccountRequest): AccountResponse {
        val createAccount = CreateAccount(UUID.randomUUID(), account.balance, account.customerId, LocalDateTime.now())
        log.info("Creating account for customer {}", createAccount.customerId)
        val accountResponse = AccountResponse(createAccount.accountId)
        return accountResponse
    }

}
