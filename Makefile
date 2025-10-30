# Makefile para Dog API Tests
.PHONY: test test-docker report report-build clean

test: ## Executa testes localmente
	mvn clean test

test-docker: ## Executa testes no Docker
	./scripts/docker-test.sh

report: ## Gera e serve relatório Allure interativo
	mvn allure:serve

report-build: ## Gera arquivos do relatório Allure estáticos (sem servidor)
	mvn allure:report

clean: ## Limpa arquivos de build
	mvn clean