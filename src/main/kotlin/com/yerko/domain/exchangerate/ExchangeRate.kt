package com.yerko.domain.exchangerate

import org.joda.time.DateTime
import java.math.BigDecimal

data class ExchangeRate(val baseCurrency:String, val rate: Rate, val date:DateTime)
data class Rate(val currency:String, val rate:BigDecimal)
