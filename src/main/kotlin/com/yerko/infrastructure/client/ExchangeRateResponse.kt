package com.yerko.infrastructure.client

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExchangeRateResponse(val baseCurrency:String, val rate: RateResponse, val date: Date)
data class RateResponse(val currency:String, val rate: BigDecimal)
