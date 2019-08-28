package com.yerko.infrastructure.configuration

import com.yerko.application.account.command.CreateAccountCommandHandler
import com.yerko.application.account.command.UpdateAccountCommandHandler
import com.yerko.application.account.entity.AccountReadRepository
import com.yerko.application.account.entity.AccountWriteRepository
import com.yerko.application.account.query.AccountQueryHandler
import com.yerko.application.moneytransfer.command.TransferMoneyCommandHandler
import com.yerko.application.moneytransfer.command.TransferMoneyServiceImpl
import com.yerko.application.moneytransfer.entity.MoneyTransferWriteRepository
import com.yerko.application.rest.HealthCheckResource
import com.yerko.application.rest.account.AccountResource
import com.yerko.domain.exchangerate.MoneyConverter
import com.yerko.domain.exchangerate.MoneyConverterImpl
import com.yerko.domain.exchangerate.query.ExchangeRateQuery
import com.yerko.domain.moneytransfer.command.*
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

    private val transferMoneyModule = Kodein.Module("TransferMoneyModule") {
        bind<MoneyTransferWriteRepository>() with singleton { MoneyTransferWriteRepositoryImpl() }
        bind<TransferMoneyCommandHandler>() with singleton { TransferMoneyCommandHandler(instance(), instance()) }
        bind<TransferMoneyServiceImpl>() with singleton { TransferMoneyServiceImpl(instance(), instance()) }

    }

    private val domainModule = Kodein.Module("DomainModule") {
        bind<WithDrawMoneyCommand>() with singleton { WithdrawMoneyCommandImpl() }
        bind<ExchangeRateQuery>() with singleton { ExchangeRateQueryImpl() }
        bind<MoneyConverter>() with singleton { MoneyConverterImpl(instance()) }
        bind<TransferMoneyCommand>() with singleton { TransferMoneyCommandImpl(instance()) }
        bind<MoneyTransferCommand>() with singleton { MoneyTransferCommandImpl(instance(), instance(), instance()) }
    }

    private val accountResourceModule = Kodein.Module("AccountModule") {
        bind<AccountWriteRepository>() with singleton { AccountWriteRepositoryImpl() }
        bind<AccountReadRepository>() with singleton { AccountReadRepositoryImpl() }
        bind() from singleton { AccountQueryHandler(instance()) }
        bind() from singleton { AccountResource(instance(), instance()) }
        bind() from singleton { CreateAccountCommandHandler(instance(), instance()) }
        bind<UpdateAccountCommandHandler>() with singleton { UpdateAccountCommandHandler(instance()) }
    }

    internal val kodein = Kodein {
        import(healthCheckModule)
        import(accountResourceModule)
        import(transferMoneyModule)
        import(domainModule)
    }
}
