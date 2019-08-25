package domain.exchangerate.command

import domain.exchangerate.ExchangeRateNotFoundException
import domain.exchangerate.ExchangeRateRepository
import domain.moneytransfer.Money
import java.math.RoundingMode

class MoneyConverterCommandImpl(private val exchangeRateRepository: ExchangeRateRepository) : MoneyConverterCommand {
    private val scale = 0
    override fun convert(money: Money, currency: String): Money {
        val exchangeRate = exchangeRateRepository.findByBaseAndDestinationCurrency(currency, money.currency)
            ?: throw ExchangeRateNotFoundException("Exchange rate information not found for currencies $currency and ${money.currency}")

        val convertedMoney = money.amount.multiply(exchangeRate.rate.rate)
        return Money(convertedMoney.setScale(scale, RoundingMode.HALF_UP), currency)
    }


}
