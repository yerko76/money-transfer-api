package com.yerko.domain.exchangerate.query

import com.yerko.domain.exchangerate.ExchangeRate

interface ExchangeRateQuery {
    fun findByBaseAndDestinationCurrency(baseCurrencyCode:String, destinationCurrencyCode:String) : ExchangeRate?
}
