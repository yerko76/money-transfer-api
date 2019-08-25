package domain.exchangerate

interface ExchangeRateRepository {
    fun findByBaseAndDestinationCurrency(baseCurrencyCode:String, destinationCurrencyCode:String) :ExchangeRate?
}
