package domain.moneytransfer

import domain.account.Account
import java.util.*


data class TransferDetail(val id:UUID, val originAccount: Account, val destinationAccount: Account)
