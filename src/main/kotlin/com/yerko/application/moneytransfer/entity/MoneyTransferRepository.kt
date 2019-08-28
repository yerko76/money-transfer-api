package com.yerko.application.moneytransfer.entity

interface MoneyTransferWriteRepository{
    suspend fun save(moneyTransferDto : MoneyTransferDto) :Int
}
