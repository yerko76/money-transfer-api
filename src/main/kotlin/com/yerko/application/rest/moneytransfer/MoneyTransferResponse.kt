package com.yerko.application.rest.moneytransfer

import com.yerko.domain.moneytransfer.Money
import java.util.*

class MoneyTransferResponse(val transactionId:Int,
                            val fromAccountId:UUID,
                            val toAccountId:UUID,
                            val transferredAmount: Money){

}
