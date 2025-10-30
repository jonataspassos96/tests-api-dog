#!/bin/bash

# Script para executar testes Dog API com Docker
# Uso: ./docker-test.sh

echo "🐳 Executando testes Dog API com Docker..."

# Limpar diretório target
echo "🧹 Limpando diretório target..."
rm -rf target
mkdir -p target

# Executar testes no Docker com correção automática de permissões
echo "🧪 Executando testes e gerando relatórios..."
# Primeiro, executar o Maven e verificar se passou
if docker run --rm \
    -v $(pwd):/workspace \
    -w /workspace \
    openjdk:21-jdk-slim \
    bash -c "apt-get update -qq && apt-get install -y -qq maven && mvn clean test"; then
    
    # Se Maven executou com sucesso, corrigir permissões
    echo "🔧 Corrigindo permissões dos arquivos gerados..."
    docker run --rm \
        -v $(pwd):/workspace \
        -w /workspace \
        openjdk:21-jdk-slim \
        bash -c "chown -R $(id -u):$(id -g) target/ || true"
    echo "✅ Testes executados com sucesso!"
    echo " Relatórios disponíveis em:"
    echo "   - Surefire: ./target/surefire-reports/"
    echo "   - Allure results: ./target/allure-results/"
    echo ""
    echo "🌐 Para gerar e visualizar o relatório Allure:"
    echo "   make report-build  # Gera arquivos estáticos"
    echo "   make report        # Gera e abre no navegador"
else
    echo "❌ Falha na execução dos testes Maven"
    echo "💡 Possíveis causas:"
    echo "   - Problemas de conectividade de rede"
    echo "   - Dependências indisponíveis no repositório Maven"
    echo "   - Falhas nos testes propriamente ditos"
    echo ""
    echo "🔄 Tente executar novamente: make test-docker"
    exit 1
fi