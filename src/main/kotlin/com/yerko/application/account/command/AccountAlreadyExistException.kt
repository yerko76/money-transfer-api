package com.yerko.application.account.command

class AccountAlreadyExistException (override var message: String): Exception(message)
