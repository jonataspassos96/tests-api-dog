# Dockerfile para executar os testes da Dog API
FROM openjdk:21-jdk-slim

# Instalar Maven e curl
RUN apt-get update && \
    apt-get install -y maven curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Criar usuário não-root para evitar problemas de permissão
RUN groupadd -r appuser && useradd -r -g appuser -m appuser

# Definir diretório de trabalho
WORKDIR /app

# Dar permissão para o usuário appuser
RUN chown -R appuser:appuser /app

# Criar e dar permissão para o diretório .m2 do Maven
RUN mkdir -p /home/appuser/.m2/repository && chown -R appuser:appuser /home/appuser/.m2

# Copiar arquivos do projeto
COPY --chown=appuser:appuser pom.xml .
COPY --chown=appuser:appuser src ./src

# Trocar para usuário não-root
USER appuser

# Definir diretório do repositório Maven
ENV MAVEN_CONFIG=/home/appuser/.m2

# Comando padrão para executar os testes E gerar relatório (sem flags JVM especiais no Java 21)
CMD ["sh", "-c", "mvn -Dmaven.repo.local=/home/appuser/.m2/repository test allure:report"]

# Labels para metadados
LABEL maintainer="QA Team"
LABEL description="Dog API Test Suite"
LABEL version="1.0.0"

# Volume recomendado para extrair relatórios
VOLUME ["/app/target"]