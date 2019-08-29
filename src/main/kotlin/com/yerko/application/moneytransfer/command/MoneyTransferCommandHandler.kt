package com.yerko.application.moneytransfer.command

import com.yerko.application.account.entity.AccountDto
import com.yerko.application.account.query.AccountQueryHandler
import com.yerko.application.rest.moneytransfer.CreateMoneyTransferRequest
import com.yerko.application.rest.moneytransfer.MoneyTransferResponse
import com.yerko.domain.account.Account
import com.yerko.domain.moneytransfer.CreateMoneyTransfer
import com.yerko.domain.moneytransfer.Money
import com.yerko.domain.moneytransfer.MoneyTransferDetail
import com.yerko.domain.moneytransfer.command.MoneyTransferCommand

class MoneyTransferCommandHandler(private val accountQueryHandler: AccountQueryHandler,
                                  private val moneyTransferCommand: MoneyTransferCommand,
                                  private val moneyTransferService: MoneyTransferService){

    suspend fun transferMoney(createMoneyTransferRequest: CreateMoneyTransferRequest) : MoneyTransferResponse {
        val transferRequest = prepareTransaction(createMoneyTransferRequest)
        val transferResponse = moneyTransferCommand.transferAmount(transferRequest)
        val transactionId = persistMoneyTransfer(transferResponse)

        return MoneyTransferResponse(transactionId,
            transferResponse.originAccount.accountId,
            transferResponse.destinationAccount.accountId,
            transferResponse.destinationAccount.balance
        )
    }

    private suspend fun persistMoneyTransfer(transferResponse: MoneyTransferDetail): Int {
        return moneyTransferService.saveTransaction(moneyTransferDetail = transferResponse)
    }

    private suspend fun prepareTransaction(createMoneyTransferRequest: CreateMoneyTransferRequest): CreateMoneyTransfer {
        val fromAccount = accountQueryHandler.findById(createMoneyTransferRequest.fromAccountId)
        val toAccount = accountQueryHandler.findById(createMoneyTransferRequest.toAccountId)
        return CreateMoneyTransfer(convertToAccount(fromAccount), convertToAccount(toAccount), createMoneyTransferRequest.amount)
    }

    private fun convertToAccount(accountDto: AccountDto) : Account{
        return Account(accountDto.accountId, Money(accountDto.moneyDto.amount, accountDto.moneyDto.currency))
    }

}
