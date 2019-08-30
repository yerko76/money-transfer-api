package com.yerko.domain.moneytransfer.command

import com.yerko.domain.account.Account
import com.yerko.domain.moneytransfer.CreateMoneyTransfer
import com.yerko.domain.moneytransfer.Money
import com.yerko.domain.moneytransfer.MoneyTransferDetail
import com.yerko.domain.moneytransfer.validator.MoneyTransferValidator
import java.math.BigDecimal

class MoneyTransferCommandImpl(private val moneyTransferValidator: MoneyTransferValidator,
                               private val withDrawMoneyCommand: WithDrawMoneyCommand,
                               private val transferMoneyCommand: TransferMoneyCommand) : MoneyTransferCommand {

    override fun transferAmount(createTransfer: CreateMoneyTransfer): MoneyTransferDetail {
        val originAccount = createTransfer.originAccount
        val destinationAccount = createTransfer.destinationAccount
        val transferAmount = createTransfer.transferAmount
        this.moneyTransferValidator.validate(originAccount.balance, transferAmount)
        return executeMoneyTransfer(originAccount, transferAmount, destinationAccount)
    }

    private fun executeMoneyTransfer(originAccount: Account,
                                     transferAmount: BigDecimal,
                                     destinationAccount: Account): MoneyTransferDetail {
        val withDraw = withDrawMoneyCommand.withDrawMoney(originAccount, transferAmount)
        val transferredAmount = transferMoneyCommand.transferMoney(destinationAccount, transferAmount, originAccount.balance.currency)
        return MoneyTransferDetail(
                Account(originAccount.accountId, withDraw),
                Account(destinationAccount.accountId, transferredAmount),
                Money(transferAmount, originAccount.balance.currency))
    }

}
