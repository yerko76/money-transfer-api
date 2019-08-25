package com.yerko.domain.exchangerate

import java.math.BigDecimal
import java.time.LocalDateTime

data class ExchangeRate(val baseCurrency:String, val rate: Rate, val date:LocalDateTime)
data class Rate(val currency:String, val rate:BigDecimal)
