package domain.account

import domain.moneytransfer.Money
import java.util.*

data class Account(val accountId:UUID, val balance: Money)
