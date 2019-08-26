package com.yerko

import io.micronaut.runtime.Micronaut

object MoneyTransferApplication {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
            .packages("com.yerko")
            .mainClass(MoneyTransferApplication.javaClass)
            .start()
    }
}
