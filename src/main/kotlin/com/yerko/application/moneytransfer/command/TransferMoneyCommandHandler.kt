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
import kotlinx.coroutines.runBlocking

class TransferMoneyCommandHandler(private val accountQueryHandler: AccountQueryHandler,
                                  private val moneyTransferCommand: MoneyTransferCommand){
    suspend fun transferMoney(createMoneyTransferRequest: CreateMoneyTransferRequest) : MoneyTransferResponse{
        val transferRequest = prepareTransaction(createMoneyTransferRequest)
        val transferResponse = moneyTransferCommand.transferAmount(transferRequest)
        val persistedResponse = persistMoneyTransfer(transferResponse)
        return MoneyTransferResponse(1,
            transferResponse.originAccount.accountId,
            transferResponse.destinationAccount.accountId,
            transferResponse.destinationAccount.balance
            )
    }

    private fun persistMoneyTransfer(transferResponse: MoneyTransferDetail): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun prepareTransaction(createMoneyTransferRequest: CreateMoneyTransferRequest): CreateMoneyTransfer {
        val fromAccount = runBlocking { accountQueryHandler.findById(createMoneyTransferRequest.fromAccountId) }
        val toAccount = runBlocking { accountQueryHandler.findById(createMoneyTransferRequest.toAccountId) }
        return CreateMoneyTransfer(convertToAccount(fromAccount), convertToAccount(toAccount), createMoneyTransferRequest.amount)
    }

    private fun convertToAccount(accountDto: AccountDto) : Account{
        return Account(accountDto.accountId, Money(accountDto.moneyDto.amount, accountDto.moneyDto.currency))
    }

}
