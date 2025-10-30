#!/bin/bash

# Script para executar os testes da Dog API
# Uso: ./run-tests.sh [opcoes]
#
# Opções:
#   --help, -h          Mostra esta mensagem de ajuda
#   --clean, -c         Limpa o projeto antes de executar
#   --report, -r        Gera relatório Allure após os testes
#   --serve, -s         Abre o relatório no navegador
#   --test CLASS        Executa apenas uma classe de teste específica
#   --docker, -d        Executa os testes usando Docker

set -e

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Variáveis
CLEAN=false
REPORT=false
SERVE=false
DOCKER=false
TEST_CLASS=""
MAVEN_ARGS=""

# Função para mostrar ajuda
show_help() {
    echo "Script para executar os testes da Dog API"
    echo ""
    echo "Uso: $0 [opções]"
    echo ""
    echo "Opções:"
    echo "  --help, -h          Mostra esta mensagem de ajuda"
    echo "  --clean, -c         Limpa o projeto antes de executar"
    echo "  --report, -r        Gera relatório Allure após os testes"
    echo "  --serve, -s         Abre o relatório no navegador"
    echo "  --test CLASS        Executa apenas uma classe de teste específica"
    echo "  --docker, -d        Executa os testes usando Docker"
    echo ""
    echo "Exemplos:"
    echo "  $0                           # Executa todos os testes"
    echo "  $0 --clean --report          # Limpa, executa e gera relatório"
    echo "  $0 --test BreedsListTest     # Executa apenas BreedsListTest"
    echo "  $0 --docker                  # Executa usando Docker"
    echo ""
}

# Função para log colorido
log() {
    local color=$1
    local message=$2
    echo -e "${color}[$(date +'%Y-%m-%d %H:%M:%S')] ${message}${NC}"
}

# Função para verificar pré-requisitos
check_prerequisites() {
    if [ "$DOCKER" = true ]; then
        if ! command -v docker &> /dev/null; then
            log $RED "Docker não encontrado. Instale o Docker primeiro."
            exit 1
        fi
        log $GREEN "Docker encontrado"
        return
    fi
    
    # Verificar Java
    if ! command -v java &> /dev/null; then
        log $RED "Java não encontrado. Instale Java 17 ou superior."
        exit 1
    fi
    
    java_version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
    if [ "$java_version" -lt 21 ]; then
        log $RED "Java 21 ou superior é necessário. Versão atual: $java_version"
        exit 1
    fi
    log $GREEN "Java $java_version encontrado"
    
    # Verificar Maven
    if ! command -v mvn &> /dev/null; then
        log $RED "Maven não encontrado. Instale Maven 3.8 ou superior."
        exit 1
    fi
    
    maven_version=$(mvn --version | head -n 1 | awk '{print $3}')
    log $GREEN "Maven $maven_version encontrado"
}

# Função para executar com Docker
run_with_docker() {
    log $BLUE "Executando testes com Docker..."
    
    # Build da imagem se não existir
    if ! docker images | grep -q "dog-api-tests"; then
        log $YELLOW "Criando imagem Docker..."
        docker build -t dog-api-tests .
    fi
    
    # Executar testes
    log $BLUE "Executando testes..."
    docker run --rm \
        -v $(pwd)/target:/app/target \
        dog-api-tests \
        mvn clean test
    
    log $GREEN "Testes executados com sucesso!"
    
    if [ "$REPORT" = true ]; then
        log $YELLOW "Gerando relatório Allure..."
        docker run --rm \
            -v $(pwd)/target:/app/target \
            dog-api-tests \
            mvn allure:report
        log $GREEN "Relatório gerado em target/site/allure-maven-plugin/"
    fi
}

# Função para executar localmente
run_locally() {
    log $BLUE "Executando testes localmente..."
    
    # Preparar comando Maven
    cmd="mvn"
    
    if [ "$CLEAN" = true ]; then
        cmd="$cmd clean"
    fi
    
    cmd="$cmd test"
    
    if [ ! -z "$TEST_CLASS" ]; then
        cmd="$cmd -Dtest=$TEST_CLASS"
    fi
    
    # Executar testes
    log $BLUE "Comando: $cmd"
    $cmd
    
    log $GREEN "Testes executados com sucesso!"
    
    # Gerar relatório se solicitado
    if [ "$REPORT" = true ]; then
        log $YELLOW "Gerando relatório Allure..."
        mvn allure:report
        log $GREEN "Relatório gerado em target/site/allure-maven-plugin/"
        
        if [ "$SERVE" = true ]; then
            log $BLUE "Abrindo relatório no navegador..."
            mvn allure:serve
        fi
    fi
}

# Parse dos argumentos
while [[ $# -gt 0 ]]; do
    case $1 in
        --help|-h)
            show_help
            exit 0
            ;;
        --clean|-c)
            CLEAN=true
            shift
            ;;
        --report|-r)
            REPORT=true
            shift
            ;;
        --serve|-s)
            SERVE=true
            REPORT=true  # serve implica report
            shift
            ;;
        --test)
            TEST_CLASS="$2"
            shift 2
            ;;
        --docker|-d)
            DOCKER=true
            shift
            ;;
        *)
            log $RED "Opção desconhecida: $1"
            show_help
            exit 1
            ;;
    esac
done

# Banner
echo ""
log $BLUE "🐕 Dog API Test Suite 🐕"
echo ""

# Verificar pré-requisitos
check_prerequisites

# Executar testes
if [ "$DOCKER" = true ]; then
    run_with_docker
else
    run_locally
fi

echo ""
log $GREEN "✅ Execução concluída!"
echo ""