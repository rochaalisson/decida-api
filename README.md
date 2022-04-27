# Decida
API REST de pautas de votação feita com Spring Boot

## Pré-requisitos

Estas são as instalações e configurações necessárias para executar o projeto.

Para executar este projeto é necessário instalar:

- Apache Maven 3.8.5
- Java 11

1. Após a instalação é necessário configurar as variaveis de ambiente:

   - JAVA_HOME - Apontando para o local de instalação do Java
   - MAVEN_HOME - Apontando para o local de instalação do Maven
   - PATH - Adicionar "JAVA_HOME\bin" e "MAVEN_HOME\bin"

2. Após intatalações e configurações verifique se o ambiente está pronto:

- Execute o seguinte comando no terminal 

        java -version

- Resultado esperado

        openjdk 11.0.14.1 2022-02-08 LTS
        OpenJDK Runtime Environment Corretto-11.0.14.10.1 (build 11.0.14.1+10-LTS)
        OpenJDK Server VM Corretto-11.0.14.10.1 (build 11.0.14.1+10-LTS, mixed mode, emulated-client)

3. Execute o seguinte comando no terminal:

         mvn -version

- Resultado esperado

        Apache Maven 3.8.5 (3599d3414f046de2324203b78ddcf9b5e4388aa0)
        Maven home: ...\apache-maven-3.8.5
        Java version: 11.0.14.1
   
## Execução

1. Clonar repositório git:

         git clone https://github.com/rochaalisson/decida-api.git

2. Vá ate a pasta do projeto

         cd decida-api

3. Execute o projeto

         mvn spring-boot:run
