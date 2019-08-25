package domain.account.query

import domain.account.Account
import java.util.*

interface AccountQuery {
    fun findById(accountId:UUID): Account?
}
