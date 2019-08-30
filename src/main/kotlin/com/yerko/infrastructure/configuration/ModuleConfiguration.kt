package com.yerko.infrastructure.configuration

import com.yerko.application.account.command.CreateAccountCommandHandler
import com.yerko.application.account.command.UpdateAccountCommandHandler
import com.yerko.application.account.entity.AccountReadRepository
import com.yerko.application.account.entity.AccountWriteRepository
import com.yerko.application.account.query.AccountQueryHandler
import com.yerko.application.moneytransfer.command.MoneyTransferCommandHandler
import com.yerko.application.moneytransfer.command.MoneyTransferService
import com.yerko.application.moneytransfer.command.MoneyTransferServiceImpl
import com.yerko.application.moneytransfer.entity.MoneyTransferWriteRepository
import com.yerko.application.rest.HealthCheckResource
import com.yerko.application.rest.account.AccountResource
import com.yerko.domain.exchangerate.MoneyConverter
import com.yerko.domain.exchangerate.MoneyConverterImpl
import com.yerko.domain.exchangerate.query.ExchangeRateQuery
import com.yerko.domain.moneytransfer.command.*
import com.yerko.domain.moneytransfer.validator.MoneyTransferValidator
import com.yerko.domain.moneytransfer.validator.MoneyTransferValidatorImpl
import com.yerko.infrastructure.client.ExchangeRateQueryImpl
import com.yerko.infrastructure.persistance.AccountReadRepositoryImpl
import com.yerko.infrastructure.persistance.AccountWriteRepositoryImpl
import com.yerko.infrastructure.persistance.MoneyTransferWriteRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

object ModulesConfiguration {
    private val healthCheckModule = Kodein.Module("HealthCheckModule") {
        bind() from singleton { HealthCheckResource() }
    }

    private val moneyTransferModule = Kodein.Module("TransferMoneyModule") {
        bind<MoneyTransferWriteRepository>() with singleton { MoneyTransferWriteRepositoryImpl() }
        bind<MoneyTransferValidator>() with singleton { MoneyTransferValidatorImpl() }
        bind<MoneyTransferService>() with singleton { MoneyTransferServiceImpl(instance(), instance()) }
        bind<MoneyTransferCommandHandler>() with singleton { MoneyTransferCommandHandler(instance(), instance(), instance()) }
        bind<MoneyTransferServiceImpl>() with singleton { MoneyTransferServiceImpl(instance(), instance()) }
        bind<ExchangeRateQuery>() with singleton { ExchangeRateQueryImpl() }
    }

    private val domainModule = Kodein.Module("DomainModule") {
        bind<WithDrawMoneyCommand>() with singleton { WithdrawMoneyCommandImpl() }
        bind<MoneyConverter>() with singleton { MoneyConverterImpl(instance()) }
        bind<TransferMoneyCommand>() with singleton { TransferMoneyCommandImpl(instance()) }
        bind<MoneyTransferCommand>() with singleton { MoneyTransferCommandImpl(instance(), instance(), instance()) }
    }

    private val accountResourceModule = Kodein.Module("AccountModule") {
        bind<AccountWriteRepository>() with singleton { AccountWriteRepositoryImpl() }
        bind<AccountReadRepository>() with singleton { AccountReadRepositoryImpl() }
        bind() from singleton { AccountQueryHandler(instance()) }
        bind() from singleton { AccountResource(instance(), instance(), instance()) }
        bind() from singleton { CreateAccountCommandHandler(instance(), instance()) }
        bind<UpdateAccountCommandHandler>() with singleton { UpdateAccountCommandHandler(instance()) }
    }

    internal val kodein = Kodein {
        import(healthCheckModule)
        import(accountResourceModule)
        import(moneyTransferModule)
        import(domainModule)
    }
}
