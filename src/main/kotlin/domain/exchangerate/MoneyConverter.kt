package domain.exchangerate

import domain.moneytransfer.Money

interface MoneyConverter {
    fun convert(money:Money, currency:String) :Money
}
