# Dog API Tests - Teste Técnico QA

Este projeto contém testes automatizados para validar os endpoints da Dog API, desenvolvido como parte de um teste técnico para a posição de QA.

## 📋 Descrição

O projeto implementa testes automatizados para os seguintes endpoints da [Dog API](https://dog.ceo/dog-api/documentation):

- `GET /breeds/list/all` - Lista todas as raças de cães disponíveis
- `GET /breed/{breed}/images` - Busca imagens de uma raça específica  
- `GET /breeds/image/random` - Retorna uma imagem aleatória de cão

## 🏗️ Arquitetura do Projeto

Este projeto segue o padrão **API Test Framework** com separação clara de responsabilidades:

- **📡 Client Layer**: Encapsula todas as chamadas HTTP à Dog API
- **📋 Model Layer**: DTOs para serialização/deserialização das respostas JSON
- **🧪 Test Layer**: Casos de teste organizados por endpoint e funcionalidade
- **🔧 Utils Layer**: Constantes, configurações e utilitários compartilhados

**Padrões aplicados:**
- ✅ **Single Responsibility**: Cada classe tem uma responsabilidade específica
- ✅ **DRY (Don't Repeat Yourself)**: Reutilização de código via cliente HTTP
- ✅ **Page Object Model adaptado**: Para testes de API
- ✅ **Test Data Management**: Constantes centralizadas

**Boas práticas implementadas:**
- ✅ **Page Object Model adaptado para APIs** - Cliente centralizado para todas as chamadas HTTP
- ✅ **Separação de responsabilidades** - Client, Model, Test e Utils bem definidos
- ✅ **Reutilização de código** - DogApiClient usado em todos os testes
- ✅ **Nomenclatura clara e descritiva** - Classes e métodos autoexplicativos
- ✅ **Documentação inline com Javadoc** - Código bem documentado
- ✅ **Uso de constantes para valores fixos** - TestConstants centraliza configurações
- ✅ **Validações abrangentes** - Testes funcionais, não-funcionais e de integração
- ✅ **Relatórios detalhados** - Allure e TestNG para análise completa
- ✅ **HTTP Request/Response Capture** - Captura automática via AllureRestAssuredFilter
- ✅ **CI/CD Pipeline moderno** - GitHub Actions com deploy automático
- ✅ **Execução Multi-Plataforma** - Docker + Linux/macOS/Windows nativos

## 🛠 **Funcionalidades Avançadas**

### **📊 HTTP Request/Response Capture**
- **AllureRestAssuredFilter personalizado** - Captura automática de todas as requisições HTTP
- **Anexos detalhados nos relatórios** - Headers, body, status code visíveis no Allure
- **Steps organizados** - Requests e responses como steps separados
- **JSON formatado** - Responses JSON anexados como arquivos navegáveis

### **🐳 Execução Docker (Workflow Principal)**
- **Imagem:** `openjdk:21-jdk-slim`
- **Sem privilégios sudo** - Gerenciamento de permissões embarcado
- **Volumes configurados** - Extração automática de relatórios
- **Script automatizado** - `./scripts/docker-test.sh`
- **Deploy automático** - GitHub Pages via workflow `ci-cd.yml`

### **🖥️ Execução Nativa Multi-Plataforma**
- **Linux:** Execução nativa Ubuntu (workflow `linux.yml`)
- **macOS:** Setup via Homebrew (workflow `macos.yml`)
- **Windows:** PowerShell nativo (workflow `windows.yml`)
- **Análise de sistema** - Informações detalhadas por plataforma

### **📈 Relatórios Multi-Formato**
- **TestNG HTML** - Resultados tradicionais com logs detalhados
- **Allure interativo** - Gráficos, trends, e navegação avançada
- **GitHub Pages** - Acesso público aos relatórios online
- **Step Summaries** - Resumos visuais no GitHub Actions

## �📁 Estrutura do Projeto

```
teste-tecnico-api/
├── src/
│   ├── main/java/dogapi/
│   │   ├── client/
│   │   │   └── DogApiClient.java          # Cliente HTTP para Dog API
│   │   ├── model/
│   │   │   ├── BaseResponse.java          # Classe base para respostas
│   │   │   ├── BreedsListResponse.java    # Modelo para /breeds/list/all
│   │   │   ├── BreedImagesResponse.java   # Modelo para /breed/{breed}/images
│   │   │   ├── RandomImageResponse.java   # Modelo para /breeds/image/random
│   │   │   └── ErrorResponse.java         # Modelo para respostas de erro
│   │   └── util/
│   │       ├── TestConstants.java         # Constantes e configurações
│   │       └── AllureRestAssuredFilter.java  # Filtro HTTP para Allure
│   └── test/java/dogapi/tests/
│       ├── BreedsListTest.java            # Testes para listagem de raças
│       ├── BreedImagesTest.java           # Testes para imagens por raça
│       ├── RandomImageTest.java           # Testes para imagem aleatória
│       └── IntegrationTest.java           # Testes de integração
├── scripts/
│   ├── docker-test.sh                     # Script Docker automatizado
│   └── run-tests.sh                       # Script execução local
├── .github/
│   └── workflows/
│       ├── ci-cd.yml                      # Pipeline principal Docker (Linux)
│       ├── linux.yml                     # Execução nativa Linux
│       ├── macos.yml                     # Execução nativa macOS
│       └── windows.yml                   # Execução nativa Windows
├── target/
│   ├── allure-results/                    # Resultados Allure (JSON)
│   ├── site/allure-maven-plugin/          # Relatórios HTML Allure
│   └── surefire-reports/                  # Relatórios TestNG
├── Dockerfile                             # Containerização segura (Java 21)
├── Makefile                               # Comandos automatizados de desenvolvimento
├── pom.xml                                # Configuração Maven (Java 21 + deps atualizadas)
└── README.md                              # Documentação do projeto
```

## 🛠️ Tecnologias Utilizadas

### **Stack Principal (Modernizado para Java 21 LTS)**
- **Java 21 LTS** - Linguagem de programação (versão mais recente LTS)
- **Maven 3.9.5** - Gerenciamento de dependências e build
- **TestNG 7.11.0** - Framework de testes com DataProviders
- **RestAssured 5.5.6** - Biblioteca para testes de API REST
- **Jackson 2.18.2** - Serialização/deserialização JSON
- **Allure 2.30.0** - Geração de relatórios avançados com HTTP capture
- **SLF4J 2.0.16** - Logging estruturado

### **DevOps e CI/CD**
- **Docker** - Containerização segura dos testes (openjdk:21-jdk-slim)
- **GitHub Actions** - Pipeline CI/CD unificado com deploy automático
- **GitHub Pages** - Hospedagem automática dos relatórios Allure
- **Makefile** - Comandos automatizados para desenvolvimento

## 🚀 Pré-requisitos

### **📋 Opções de Execução Disponíveis:**

#### **🐳 Opção 1: Docker (Recomendado para CI/CD)**
- Docker instalado
- Docker Compose (opcional)
- **Usado em:** Workflow principal (`ci-cd.yml`)

#### **🐧 Opção 2: Linux Nativo**
- Ubuntu 20.04+ ou distribuição compatível
- Java 21 LTS + Maven (instalação automática via workflow)
- **Usado em:** Workflow `linux.yml`

#### **🍎 Opção 3: macOS Nativo**
- macOS 12+ (Monterey ou superior)
- Homebrew (para instalação automática do Java 21 + Maven)
- **Usado em:** Workflow `macos.yml`

#### **🪟 Opção 4: Windows Nativo**
- Windows Server 2022 ou Windows 11
- PowerShell 5.1+ (instalação automática do Java 21)
- **Usado em:** Workflow `windows.yml`

#### **🔧 Opção 5: Instalação Local Manual**
- Java 21 LTS (qualquer distribuição)
- Maven 3.9+ 
- Conexão com internet (para acessar a Dog API)

## 📦 Configuração e Instalação

### Usando Docker

1. **Clone o repositório:**
```bash
git clone <repository-url>
cd teste-tecnico-api
```

2. **Execute com Docker (Comando único):**
```bash
# Execução automatizada: build + testes + relatórios
make test-docker
```

**O que acontece automaticamente:**
- 🏗️ Build da imagem Docker
- 🧪 Execução de todos os testes  
- 📊 Geração de relatórios Allure e TestNG
- 📁 Extração dos relatórios para `./target/`

3. **Comandos manuais (opcional):**
```bash
# Se preferir executar manualmente
docker build -t dog-api-tests .
docker run --rm -v $(pwd)/target:/app/target dog-api-tests
```

### Instalação Local

1. **Verifique os pré-requisitos:**
```bash
java -version  # Deve mostrar Java 21+
mvn -version   # Deve mostrar Maven 3.9+
```

2. **Clone e configure:**
```bash
git clone <repository-url>
cd teste-tecnico-api
```

3. **Instale as dependências:**
```bash
# Opção 1: Baixar dependências (recomendado)
mvn dependency:resolve

# Opção 2: Compilar código + baixar dependências
mvn clean compile

# Opção 3: Setup completo do projeto
mvn clean install
```

## 🧪 Executando os Testes

### 🎯 Usando Makefile (Recomendado)

```bash
# Executar testes localmente
make test

# Executar testes no Docker (automatizado: build + test + reports)
make test-docker

# Gerar relatório Allure interativo (abre no navegador)
make report

# Gerar relatório Allure estático (arquivos HTML)
make report-build
```

### 🔧 Usando Maven Diretamente

```bash
# Executar todos os testes
mvn clean test

# Executar testes específicos
mvn test -Dtest=BreedsListTest
mvn test -Dtest=BreedImagesTest
mvn test -Dtest=RandomImageTest
```

### Executar com perfil específico
```bash
mvn clean test -Ptest
```

## 📊 Relatórios

### 🤖 **Automáticos (gerados no `mvn test`):**

#### **TestNG Reports** ✅
- **Localização:** `target/surefire-reports/`
- **Arquivo principal:** `target/surefire-reports/index.html`
- **Conteúdo:** Resultados detalhados, logs, estatísticas

#### **Allure Raw Data** ✅  
- **Localização:** `target/allure-results/`
- **Formato:** JSON (dados brutos para processamento)

### 🔧 **Manuais (requerem comando adicional):**

#### **Allure HTML Reports** 
```bash
# Opção 1: Gerar e visualizar (recomendado)
mvn allure:serve          # Abre automaticamente no navegador

# Opção 2: Apenas gerar HTML
mvn allure:report         # Gera em target/site/allure-maven-plugin/
```

#### **Usando Makefile:**
```bash
make report              # Equivale a mvn allure:serve (interativo)
make report-build        # Equivale a mvn allure:report (estático)
```

### 📋 **Resumo:**
- **TestNG**: 100% automático após `mvn test`
- **Allure JSON**: 100% automático após `mvn test`  
- **Allure HTML**: Requer `mvn allure:report` ou `mvn allure:serve`

### 🌐 **Relatórios Online (GitHub Pages):**
- **URL:** https://jonataspassos96.github.io/tests-api-dog/reports/
- **Deploy automático:** A cada push na branch `main`
- **Captura HTTP:** Requests e responses completos nos relatórios





## 📊 Métricas e Cobertura

### **🧪 Cobertura de Testes**
- **Total de testes:** 20
- **Endpoints cobertos:** 3/3 (100%)
- **Taxa de sucesso:** 100% ✅
- **Cenários positivos:** ✅
- **Cenários negativos:** ✅  
- **Testes de performance:** ✅
- **Testes de integração:** ✅

### **🔧 Qualidade Técnica**
- **Java 21 LTS:** ✅ Runtime moderno
- **Dependencies atualizadas:** ✅ Sem CVEs conhecidas
- **Docker build:** ✅ 20/20 testes passando
- **CI/CD Pipeline:** ✅ Deploy automático funcional
- **GitHub Pages:** ✅ Relatórios públicos online

### **⚡ Performance**
- **Execução local:** ~30-45 segundos
- **Execução Docker:** ~60-90 segundos (cold start)
- **CI Pipeline:** ~3-5 minutos (com cache)
- **Tempo resposta API:** < 10s por endpoint

---







## 🧪 Cenários de Teste e Validações

### 📝 **Cenários Implementados**

#### **BreedsListTest** - Endpoint `/breeds/list/all`
- ✅ Validação de resposta bem-sucedida (200)
- ✅ Validação da estrutura JSON
- ✅ Verificação de raças conhecidas
- ✅ Validação de sub-raças
- ✅ Teste de performance (< 10s)
- ✅ Validação de headers HTTP

#### **BreedImagesTest** - Endpoint `/breed/{breed}/images`
- ✅ Busca de imagens para raça válida (200)
- ✅ Validação do formato das URLs de imagem
- ✅ Tratamento de raça inválida (404)
- ✅ Teste com múltiplas raças
- ✅ Teste de case sensitivity
- ✅ Validação de performance (< 10s)

#### **RandomImageTest** - Endpoint `/breeds/image/random`
- ✅ Busca de imagem aleatória (200)
- ✅ Validação do formato da URL
- ✅ Teste de aleatoriedade
- ✅ Consistência da estrutura JSON
- ✅ Teste de múltiplas chamadas
- ✅ Validação de performance (< 10s)

#### **IntegrationTest** - Fluxos Completos
- ✅ Integração entre endpoints
- ✅ Consistência de dados
- ✅ Tratamento de casos extremos

### ✅ **Checklist de Validações**

#### **Validações Funcionais**
- [x] Status code 200 para requisições válidas
- [x] Status code 404 para recursos inexistentes  
- [x] Estrutura JSON conforme especificação da API
- [x] Dados obrigatórios presentes nas respostas
- [x] URLs de imagens válidas e acessíveis
- [x] Aleatoriedade das imagens retornadas

#### **Validações Não-Funcionais**
- [x] Tempo de resposta < 10 segundos
- [x] Headers HTTP corretos (Content-Type, etc.)
- [x] Content-Type: application/json
- [x] Encoding UTF-8

#### **Validações de Integração**
- [x] Consistência entre endpoints
- [x] Fluxo completo de uso da API
- [x] Tratamento de casos extremos e erros

## 🔧 Configuração de CI/CD

### 🚀 **Arquitetura Multi-Plataforma GitHub Actions**

O projeto implementa uma **arquitetura CI/CD robusta e multi-plataforma** com 4 workflows especializados:

#### **🎯 Workflows Disponíveis:**

### **1. 🐳 Pipeline Principal (`ci-cd.yml`)**
- **Ambiente:** Docker + Ubuntu runners
- **Finalidade:** Pipeline principal com deploy para GitHub Pages
- **Triggers:** Push na `main`, Pull Requests, Manual
- **Características:**
  - ✅ Execução containerizada (openjdk:21-jdk-slim)
  - ✅ Deploy automático para GitHub Pages
  - ✅ Otimizado para produção e integração contínua
  - ✅ URL pública: `https://jonataspassos96.github.io/tests-api-dog/reports/`

### **2. 🐧 Linux Nativo (`linux.yml`)**
- **Ambiente:** ubuntu-latest (nativo)
- **Finalidade:** Testes em ambiente Linux puro
- **Triggers:** Manual (`workflow_dispatch`)
- **Características:**
  - ✅ Execução nativa sem Docker
  - ✅ Setup JDK 21 Temurin nativo
  - ✅ Análise detalhada do sistema Linux
  - ✅ Artifacts específicos: `linux-test-results`

### **3. 🍎 macOS Nativo (`macos.yml`)**
- **Ambiente:** macos-latest (nativo)
- **Finalidade:** Testes em ambiente macOS
- **Triggers:** Manual (`workflow_dispatch`)
- **Características:**
  - ✅ Integração com Homebrew
  - ✅ Análise do sistema macOS (sw_vers)
  - ✅ Setup JDK 21 + Maven via Homebrew
  - ✅ Artifacts específicos: `macos-test-results`

### **4. 🪟 Windows Nativo (`windows.yml`)**
- **Ambiente:** windows-latest (nativo)
- **Finalidade:** Testes em ambiente Windows
- **Triggers:** Manual (`workflow_dispatch`)
- **Características:**
  - ✅ Scripts PowerShell nativos
  - ✅ Análise completa do sistema Windows
  - ✅ Setup JDK 21 Temurin para Windows
  - ✅ Artifacts específicos: `windows-test-results`

#### **🛠️ Execução dos Workflows:**

```bash
# Pipeline Principal (automático)
git push origin main  # Trigger automático

# Execução Manual via GitHub CLI
gh workflow run ci-cd.yml     # Pipeline principal
gh workflow run linux.yml     # Linux nativo
gh workflow run macos.yml     # macOS nativo  
gh workflow run windows.yml   # Windows nativo

# Via GitHub Web Interface
# Actions → Selecionar workflow → "Run workflow"
```

#### **📊 Recursos Avançados Compartilhados:**
- **HTTP Request/Response Capture** - AllureRestAssuredFilter personalizado em todos os workflows
- **Step Summaries** - Resumos visuais detalhados por plataforma
- **Artifact Management** - Artifacts específicos por plataforma (30 dias de retenção)
- **System Analysis** - Análise detalhada do ambiente de execução por plataforma
- **Conditional Deployment** - Deploy apenas no workflow principal (`ci-cd.yml`)

#### **📦 Artifacts Multi-Plataforma:**

Cada workflow gera artifacts específicos com análises detalhadas:

| Workflow      | Artifact Name          | Conteúdo                                     |
| ------------- | ---------------------- | -------------------------------------------- |
| `ci-cd.yml`   | `test-reports`         | Relatórios Allure + Deploy para GitHub Pages |
| `linux.yml`   | `linux-test-results`   | Relatórios + Análise sistema Linux           |
| `macos.yml`   | `macos-test-results`   | Relatórios + Análise sistema macOS           |
| `windows.yml` | `windows-test-results` | Relatórios + Análise sistema Windows         |

**📥 Download de Artifacts:**
- Acesse: `GitHub Actions → Workflow específico → Run → Artifacts`
- Retenção: 30 dias por execução
- Formato: ZIP com relatórios HTML + análises de sistema

## 🔗 **Links Úteis**

### **📊 Relatórios e Monitoramento**
- **Relatórios Allure Online:** https://jonataspassos96.github.io/tests-api-dog/reports/
- **GitHub Actions (Multi-Platform):** https://github.com/jonataspassos96/tests-api-dog/actions
- **Artifacts por Plataforma:** 
  - **Docker/Linux:** https://github.com/jonataspassos96/tests-api-dog/actions/workflows/ci-cd.yml
  - **Linux Nativo:** https://github.com/jonataspassos96/tests-api-dog/actions/workflows/linux.yml
  - **macOS Nativo:** https://github.com/jonataspassos96/tests-api-dog/actions/workflows/macos.yml
  - **Windows Nativo:** https://github.com/jonataspassos96/tests-api-dog/actions/workflows/windows.yml

### **🚀 Execução Rápida**
```bash
# Setup completo em um comando
git clone https://github.com/jonataspassos96/tests-api-dog.git
cd tests-api-dog && make test-docker

# Visualizar relatório local
make report
```

### **🛠️ Para Desenvolvedores**
```bash
# Execução local (requer Java 21)
make test

# Apenas relatórios estáticos
make report-build

# Testes multi-plataforma via GitHub Actions
gh workflow run linux.yml    # Linux nativo
gh workflow run macos.yml    # macOS nativo
gh workflow run windows.yml  # Windows nativo

# Limpeza
make clean
```

### **📋 Comandos CI/CD**
- **Trigger manual:** GitHub Actions → "CI Pipeline" → "Run workflow"
- **Auto-deploy:** Push para `main` → Deploy automático GitHub Pages
- **Pull Request:** Validação automática sem deploy

---