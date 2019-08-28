package com.yerko.application.moneytransfer.command

import com.yerko.application.account.entity.AccountWriteRepository
import com.yerko.application.moneytransfer.entity.MoneyTransferWriteRepository
import com.yerko.domain.moneytransfer.MoneyTransferDetail

interface TransferMoneyService {
    fun saveTransaction(moneyTransferDetail: MoneyTransferDetail) : Int
}

class TransferMoneyServiceImpl(private val accountWriteRepository: AccountWriteRepository,
                               private val moneyTransferWriteRepository: MoneyTransferWriteRepository) : TransferMoneyService{
    override fun saveTransaction(moneyTransferDetail: MoneyTransferDetail): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
