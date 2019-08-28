package com.yerko.infrastructure.persistance

import com.yerko.application.moneytransfer.entity.MoneyTransferDto
import com.yerko.application.moneytransfer.entity.MoneyTransferWriteRepository

class MoneyTransferWriteRepositoryImpl : MoneyTransferWriteRepository {
    override suspend fun save(moneyTransferDto: MoneyTransferDto) : Int{
        return 1
    }

}
