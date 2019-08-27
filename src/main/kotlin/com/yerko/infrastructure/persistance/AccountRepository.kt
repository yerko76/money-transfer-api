package com.yerko.infrastructure.persistance

import com.yerko.application.account.entity.AccountEntity
import com.yerko.application.account.entity.AccountReadRepository
import com.yerko.application.account.entity.AccountWriteRepository
import java.util.*

class AccountRepository : AccountWriteRepository, AccountReadRepository {
    override fun save(account: AccountEntity): UUID {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(id: UUID): AccountEntity? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByCustomerId(customerId: String): AccountEntity? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
