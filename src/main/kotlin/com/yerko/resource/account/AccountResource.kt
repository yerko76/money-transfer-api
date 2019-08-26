package com.yerko.resource.account

import com.yerko.domain.account.command.CreateAccount
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.util.*

@Controller
class AccountResource {
    private val log = LoggerFactory.getLogger(AccountResource::class.java)

    @Post("/account")
    fun create(account: CreateAccountRequest): HttpResponse<AccountResponse> {
        val createAccount = CreateAccount(UUID.randomUUID(), account.balance, account.customerId, LocalDateTime.now())
        log.info("Creating account for customer {}", createAccount.customerId)
        val accountResponse = AccountResponse(createAccount.accountId)
        return ok(accountResponse)
    }

}
