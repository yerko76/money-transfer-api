package com.yerko.domain.exchangerate

import com.yerko.domain.moneytransfer.Money

interface MoneyConverter {
    fun convert(money: Money, currency:String) : Money
}
