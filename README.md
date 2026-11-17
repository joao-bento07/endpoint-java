# Autenticação básica com usuário e senha (Spring Boot + BCrypt)

Endpoint protegido que recebe usuário e senha no corpo da requisição, busca o
usuário no banco (H2 em memória) e valida a senha. Sem OAuth2 e sem JWT.

## Como executar
./mvnw spring-boot:run
Usuário de teste criado na inicialização: admin / Senha@123

## Endpoints
- POST /api/usuarios   cadastra usuário (grava só o hash BCrypt)
- POST /api/protegido  200 se as credenciais forem válidas
    - 404 "Usuário não encontrado."
    - 401 "Senha incorreta."
    - 400 usuário ou senha ausentes

## Decisões
- Senhas armazenadas com BCrypt (hash com salt), nunca em texto puro.
  A coluna senha_hash guarda os 60 caracteres do hash.
- A comparação usa PasswordEncoder.matches(), que recalcula o hash com o salt
  armazenado. Não se compara String com equals().
- Uso apenas spring-security-crypto (BCrypt). O starter completo do Spring
  Security não foi usado porque o exercício pede validação direta, sem filtros.

## Limitações conhecidas
- Mensagens distintas para "usuário não encontrado" e "senha incorreta" permitem
  descobrir quais usuários existem (enumeração). Exigido pelo exercício; em
  produção seria uma mensagem única.
- Credenciais no corpo exigem HTTPS em produção.
- Sem limite de tentativas (força bruta).