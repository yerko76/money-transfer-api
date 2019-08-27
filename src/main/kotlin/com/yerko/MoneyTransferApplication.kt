package com.yerko

import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty


object MoneyTransferApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
    }
}
