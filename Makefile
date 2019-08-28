workspace=$(shell pwd)

include makefiles/Makefile.base
include makefiles/Makefile.test
include makefiles/Makefile.db

docker_app="money-transfer"
DB_SERVICE=money-transfer-db

configure:
	./configure.sh
