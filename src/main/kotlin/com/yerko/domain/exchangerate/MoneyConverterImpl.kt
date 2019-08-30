package com.yerko.domain.exchangerate

import com.yerko.domain.exchangerate.query.ExchangeRateQuery
import com.yerko.domain.moneytransfer.Money
import org.slf4j.LoggerFactory
import java.math.RoundingMode

class MoneyConverterImpl(private val exchangeRateQuery: ExchangeRateQuery) :
    MoneyConverter {
    private val log = LoggerFactory.getLogger(MoneyConverterImpl::class.java)
    private val scale = 0

    override fun convert(money: Money, destinationCurrency: String): Money {
        val exchangeRate = exchangeRateQuery.findByBaseAndDestinationCurrency(money.currency, destinationCurrency)
            ?: throw ExchangeRateNotFoundException("Exchange rate information not found for currencies $money.currency and $destinationCurrency")
        log.info("Converting {} {} to {} with rate {}", money.currency, money.amount, destinationCurrency, exchangeRate.rate.rate)
        val convertedMoney = money.amount.multiply(exchangeRate.rate.rate)
        return Money(convertedMoney.setScale(scale, RoundingMode.HALF_UP), destinationCurrency)
    }

}
