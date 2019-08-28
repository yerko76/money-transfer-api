package com.yerko.application.moneytransfer.command

import com.yerko.application.account.command.UpdateAccountCommandHandler
import com.yerko.application.moneytransfer.entity.MoneyTransferWriteRepository
import com.yerko.domain.account.Account
import com.yerko.domain.account.command.UpdateAccount
import com.yerko.domain.moneytransfer.MoneyTransferDetail

interface TransferMoneyService {
    fun saveTransaction(moneyTransferDetail: MoneyTransferDetail) : Int
}

class TransferMoneyServiceImpl(private val updateAccountCommandHandler: UpdateAccountCommandHandler,
                               private val moneyTransferWriteRepository: MoneyTransferWriteRepository
) : TransferMoneyService{
    override fun saveTransaction(moneyTransferDetail: MoneyTransferDetail): Int {
        val originAccountId = updateAccountBalance(moneyTransferDetail.originAccount)
        val destinationAccountId =updateAccountBalance(moneyTransferDetail.destinationAccount)
        return 10
//        moneyTransferWriteRepository.save(
//            MoneyTransferDto(
//                    null,
//                    originAccountId,
//                    destinationAccountId,
//                    moneyTransferDetail.originAccount.balance
//                )
//            )
    }

    private fun updateAccountBalance(account: Account) {
        updateAccountCommandHandler.update(
            UpdateAccount(
                account.accountId,
                account.balance
            )
        )
    }

}
