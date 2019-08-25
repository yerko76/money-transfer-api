package domain.exchangerate

import domain.exchangerate.query.ExchangeRateQuery
import domain.moneytransfer.Money
import java.math.RoundingMode

class MoneyConverterImpl(private val exchangeRateQuery: ExchangeRateQuery) : MoneyConverter {
    private val scale = 0
    override fun convert(money: Money, currency: String): Money {
        val exchangeRate = exchangeRateQuery.findByBaseAndDestinationCurrency(currency, money.currency)
            ?: throw ExchangeRateNotFoundException("Exchange rate information not found for currencies $currency and ${money.currency}")

        val convertedMoney = money.amount.multiply(exchangeRate.rate.rate)
        return Money(convertedMoney.setScale(scale, RoundingMode.HALF_UP), currency)
    }

}
