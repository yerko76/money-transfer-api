package com.yerko.rest.com.yerko

import com.typesafe.config.ConfigFactory
import com.yerko.application.account.entity.AccountEntity
import com.yerko.application.moneytransfer.entity.MoneyTransactionEntity
import io.ktor.config.HoconApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.experimental.transaction

class DbUtil{

    @KtorExperimentalAPI
    suspend fun clearDb(){
        val config = HoconApplicationConfig(ConfigFactory.load("application-test.conf"))
        val dbUrl = config.property("db.jdbcUrl").getString()
        val dbUser = config.property("db.dbUser").getString()
        val dbPassword = config.property("db.dbPassword").getString()
        Database.connect(dbUrl, driver = "org.postgresql.Driver", user = dbUser, password = dbPassword)

        transaction {
            MoneyTransactionEntity.deleteAll()
            AccountEntity.deleteAll()
        }
    }
}
