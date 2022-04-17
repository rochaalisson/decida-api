# Desafio Reskilling Java

Implemente uma solução utilizando JAVA 8+ e [SpringBoot](https://spring.io/) conforme descrição abaixo.</br>
**ATENÇÂO**, faça a **LEITURA COMPLETA** deste documento.

## Tarefa Principal
Em uma cooperativa, cada associado possui um voto e as decisões são tomadas através de assembleias, por votação. A partir disso, você precisa criar o backend para gerenciar essas sessões de votação. A solução deve atender os seguintes requisitos através de uma API REST:
- (RF1) Cadastrar pauta.
- (RF2) Abrir uma sessão de votação para uma pauta.
  - Cada pauta deve comportar apenas uma sessão de votação. 
  - A sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por padrão.
- (RF3) Receber votos dos associados em pautas abertas. 
  - Os votos são apenas 'Sim'/'Não'.
  - Cada associado é identificado por um id único e pode votar apenas uma vez por pauta. 
  - Registre a data/hora do voto.
- (RF4) Contabilizar os votos e dar o resultado da votação na pauta.
  - Exibir vencedor por maioria simples.
  - Exibir quantidade de votos para cada um dos grupos 'Sim'/'Não'.
  - Exibir percentual para cada um dos grupos 'Sim'/'Não'.

Para fins de exercício, a segurança pode ser abstraída e qualquer chamada para a API pode ser considerada autorizada.

Para fins de exercício, você pode cadastrar previamente um conjunto de asociados.

Implemente testes unitários com ao menos 50% de cobertura de código.

Utilize [Swagger](https://swagger.io/) para documentar sua API.

Disponibilize uma coleção do [Postman](https://www.postman.com/) com todos endpoints.

Se utilizar um banco de dados diferente do H2, um [dockerfile](https://www.docker.com/) deve ser dispobinilizado para o banco de dados.

## Tarefas bônus
As tarefas bônus falam muito sobre seu comprometimento e vontade de aprender =D. 

### Tarefa Bônus 1 - Integração com sistemas externos
Integrar com um sistema externo que verifica, a partir do CPF do associado, se ele pode votar ou não. Observe que um CPF poderá ser usado apenas 1 vez por votação em determinada pauta. Caso o serviço externo esteja indisponível, permita o voto.
OBS: O serviço só verifica se foram passados 11 digitos ou não, veja exemplos de requisição e retornos abaixo.

GET: https://cpf-api-almfelipe.herokuapp.com/cpf/12345678901
```json
{
"cpf": "12345678901",
"isValid": true
}
```

GET: https://cpf-api-almfelipe.herokuapp.com/cpf/1234567890a
```json
{
"cpf": "1234567890a",
"isValid": false
}
```

### Tarefa Bônus 2 - Contabilização automática
A contabilização de votos de pautas encerradas (RF4), deve ser feita de forma automática pelo sistema. A rotina de contabilização deve ser executada a cada minuto. Os dados devem ser persistidos.

### Tarefa Bônus 3 - Mensageria e filas
Quando a sessão de votação fechada, poste uma mensagem em uma mensageria ([Kafka](https://kafka.apache.org/), [RebbitMQ](https://www.rabbitmq.com/) ou qualquer outra) com o resultado da votação. Fornecaça dockerfile ou configurações necessárias para o serviço de mensageiria utilizado.

### Tarefa Bônus 4 - Hospede sua API na nuven
Hospede sua API em um núvem qualquer. Cuidado para não gerar corbranças, explore serviços gratuítos como da [Free Tier AWS](https://aws.amazon.com/pt/free/), [Heroku](https://www.heroku.com/pricing) ou outro da sua escolha. Lembre-se de tratar Cross-Origin Resource Sharing (CORS). 

### Tarefa Bônus 5 - Análise de qualidade do código
Utilize o [Sonarqube](https://www.sonarqube.org/) para realizar uma análise de qualidade do seu código, aplique as correções necessárias e disponibilize o relatório.

### Tarefa Bônus 6 - Versionamento da API
Como você versionaria a sua API? Que estratégia usar?

## O que será analisado
- Simplicidade no design da solução (evitar over engineering)
- Organização do código
- Arquitetura do projeto
- Boas práticas de programação (manutenibilidade, legibilidade, baixo acoplamento, facildiade em reuso e etc)
- Possíveis falhas e erros
- Tratamento de erros e exceções
- Uso de testes automatizados
- Limpeza do código
- Documentação do código e da API
- Logs da aplicação
- Mensagens e organização dos commits
- Apresentação oral da solução

## Observações importantes
- Tire **TODAS** dúvidas antes de iniciar a implementação
- Ao Final você **irá apresentar** a solução e possivelmente será questionado a respeito de decisões/implementações.
- **ATENÇÂO**: Irei executar a aplicação para testá-la, faça um **README** completo e detalhado com **TODOS** passos necessários para executar a aplicação. Se ao seguir os passos, sua aplicação não for executada ela não será analisada. Preferencialmente solicite que um colega execute o seu passo a passo para verificar se ele está completo.
- Aidcione ao seu README o link para o Swagger da API, listagem de quais tarefas bônus realizadas e link caso tenha hospedado a API na nuvem.