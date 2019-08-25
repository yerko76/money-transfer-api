package domain.exchangerate.command

import domain.moneytransfer.Money

interface MoneyConverterCommand {
    fun convert(money:Money, currency:String) :Money
}
