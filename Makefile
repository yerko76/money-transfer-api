workspace=$(shell pwd)

include makefiles/Makefile.base
include makefiles/Makefile.test

docker_app="money-transfer-api"

configure:
	./configure.sh
