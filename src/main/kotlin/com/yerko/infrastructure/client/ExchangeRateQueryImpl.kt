package com.yerko.infrastructure.client

import com.yerko.domain.exchangerate.ExchangeRate
import com.yerko.domain.exchangerate.Rate
import com.yerko.domain.exchangerate.query.ExchangeRateQuery
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

class ExchangeRateQueryImpl: ExchangeRateQuery{
    private val log = LoggerFactory.getLogger(ExchangeRateQueryImpl::class.java)
    private val exchangeRateServiceUrl = "http://exchange-rate-service:8882"

    override fun findByBaseAndDestinationCurrency(baseCurrencyCode: String, destinationCurrencyCode: String) : ExchangeRate? {
        return try {
            log.info("Requesting exchange rate information for {} {}", baseCurrencyCode, destinationCurrencyCode)
            val url = "${exchangeRateServiceUrl}/exchangeratesapi/v1/rates?from=${baseCurrencyCode}&to=${destinationCurrencyCode}"
            val client = getHttpClient()

            val response = runBlocking { client.get<ExchangeRateResponse>(url) }
            client.close()
            return ExchangeRate(response.baseCurrency, Rate(response.rate.currency, response.rate.rate) )

        } catch (e: Exception) {
            log.error("Unable to get exchange rate information due to: {}", e.message, e)
            null
        }

    }

    private fun getHttpClient(): HttpClient {
        return HttpClient {
            install(JsonFeature) {
                serializer = JacksonSerializer {}
            }
        }
    }

}
