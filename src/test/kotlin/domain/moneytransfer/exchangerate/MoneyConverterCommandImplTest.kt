package domain.moneytransfer.exchangerate

import domain.exchangerate.ExchangeRate
import domain.exchangerate.ExchangeRateNotFoundException
import domain.exchangerate.ExchangeRateRepository
import domain.exchangerate.Rate
import domain.exchangerate.command.MoneyConverterCommandImpl
import domain.moneytransfer.Money
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDateTime

class MoneyConverterCommandImplTest {

    private lateinit var moneyConverterCommandImpl : MoneyConverterCommandImpl
    private lateinit var exchangeRateRepository: ExchangeRateRepository
    private val clpCurrency = "CLP"
    private val usdCurrency = "USD"

    @Before
    fun setUp() {
        exchangeRateRepository = mockk()
        moneyConverterCommandImpl = MoneyConverterCommandImpl(exchangeRateRepository)
    }

    @Test
    fun `Should convert from CLP to USD` () {
        val clp = Money(BigDecimal.valueOf(700), clpCurrency)
        val expectedResult = Money(BigDecimal.valueOf(1), usdCurrency)
        val exchangeRate = ExchangeRate(
            usdCurrency,
            Rate(clpCurrency, BigDecimal.valueOf(0.0014)),
            LocalDateTime.now()
        )
        every { exchangeRateRepository.findByBaseAndDestinationCurrency(usdCurrency, clpCurrency) } returns exchangeRate

        val result = moneyConverterCommandImpl.convert(clp, usdCurrency)

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `Should throw Exception when currency is not found`() {
        val clp = Money(BigDecimal.valueOf(700), "CLP")
        val notFoundCurrency = "VAL"
        every { exchangeRateRepository.findByBaseAndDestinationCurrency(notFoundCurrency, clpCurrency) } returns null

        val exception = Assertions.catchThrowable {
            moneyConverterCommandImpl.convert(clp, notFoundCurrency)
        }

        Assertions.assertThat(exception)
            .isInstanceOf(ExchangeRateNotFoundException::class.java)
            .hasMessageContaining("Exchange rate information not found for currencies VAL and CLP")
    }
}
