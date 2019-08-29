package com.yerko.domain.moneytransfer.exchangerate

import com.yerko.domain.exchangerate.ExchangeRate
import com.yerko.domain.exchangerate.ExchangeRateNotFoundException
import com.yerko.domain.exchangerate.MoneyConverterImpl
import com.yerko.domain.exchangerate.Rate
import com.yerko.domain.exchangerate.query.ExchangeRateQuery
import com.yerko.domain.moneytransfer.Money
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MoneyConverterImplTest {

    private lateinit var moneyConverterCommandImpl : MoneyConverterImpl
    private lateinit var exchangeRateQuery: ExchangeRateQuery
    private val clpCurrency = "CLP"
    private val usdCurrency = "USD"

    @BeforeEach
    fun setUp() {
        exchangeRateQuery = mockk()
        moneyConverterCommandImpl = MoneyConverterImpl(exchangeRateQuery)
    }

    @Test
    fun `Should convert from CLP to USD` () {
        val clp = Money(BigDecimal.valueOf(700), clpCurrency)
        val expectedResult = Money(BigDecimal.valueOf(1), usdCurrency)
        val exchangeRate = ExchangeRate(
            usdCurrency,
            Rate(clpCurrency, BigDecimal.valueOf(0.0014)),
            DateTime.now()
        )
        every { exchangeRateQuery.findByBaseAndDestinationCurrency(usdCurrency, clpCurrency) } returns exchangeRate

        val result = moneyConverterCommandImpl.convert(clp, usdCurrency)

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `Should throw Exception when currency is not found`() {
        val clp = Money(BigDecimal.valueOf(700), "CLP")
        val notFoundCurrency = "VAL"
        every { exchangeRateQuery.findByBaseAndDestinationCurrency(notFoundCurrency, clpCurrency) } returns null

        val exception = Assertions.catchThrowable {
            moneyConverterCommandImpl.convert(clp, notFoundCurrency)
        }

        Assertions.assertThat(exception)
            .isInstanceOf(ExchangeRateNotFoundException::class.java)
            .hasMessageContaining("Exchange rate information not found for currencies VAL and CLP")
    }
}
