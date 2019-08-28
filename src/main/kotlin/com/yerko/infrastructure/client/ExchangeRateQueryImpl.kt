package com.yerko.infrastructure.client

import com.yerko.domain.exchangerate.ExchangeRate
import com.yerko.domain.exchangerate.query.ExchangeRateQuery

class ExchangeRateQueryImpl(): ExchangeRateQuery{
    override fun findByBaseAndDestinationCurrency(
        baseCurrencyCode: String,
        destinationCurrencyCode: String
    ): ExchangeRate? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
