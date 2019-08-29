workspace=$(shell pwd)

include makefiles/Makefile.base
include makefiles/Makefile.test
include makefiles/Makefile.db
include makefiles/Makefile.docker

APP_NAME="money-transfer"
DB_SERVICE="money-transfer-db"
EXCHANGE_STUBS="exchange-service"

configure:
	./configure.sh
