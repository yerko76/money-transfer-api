package com.yerko.application.moneytransfer.command

import com.yerko.application.account.command.UpdateAccountCommandHandler
import com.yerko.application.moneytransfer.entity.MoneyTransferDto
import com.yerko.application.moneytransfer.entity.MoneyTransferWriteRepository
import com.yerko.domain.account.Account
import com.yerko.domain.account.command.UpdateAccount
import com.yerko.domain.moneytransfer.MoneyTransferDetail
import java.util.*

interface MoneyTransferService {
    suspend fun saveTransaction(moneyTransferDetail: MoneyTransferDetail) : Int
}

class MoneyTransferServiceImpl(private val updateAccountCommandHandler: UpdateAccountCommandHandler,
                               private val moneyTransferWriteRepository: MoneyTransferWriteRepository) : MoneyTransferService{

    override suspend fun saveTransaction(moneyTransferDetail: MoneyTransferDetail): Int {
            val originAccountId = updateAccountBalance(moneyTransferDetail.originAccount)
            val destinationAccountId =updateAccountBalance(moneyTransferDetail.destinationAccount)

            return moneyTransferWriteRepository.save(
                MoneyTransferDto(
                    null,
                    originAccountId,
                    destinationAccountId,
                    moneyTransferDetail.transferAmount
                )
            )!!
    }

    private suspend fun updateAccountBalance(account: Account) : UUID {
        return updateAccountCommandHandler.update(
            UpdateAccount(
                account.accountId,
                account.balance
            )
        )
    }

}
