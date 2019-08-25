package domain.exchangerate.query

import domain.exchangerate.ExchangeRate

interface ExchangeRateQuery {
    fun findByBaseAndDestinationCurrency(baseCurrencyCode:String, destinationCurrencyCode:String) : ExchangeRate?
}
