package com.yerko.domain.exchangerate

import java.math.BigDecimal


data class ExchangeRate(val baseCurrency:String, val rate: Rate)
data class Rate(val currency:String, val rate:BigDecimal)
