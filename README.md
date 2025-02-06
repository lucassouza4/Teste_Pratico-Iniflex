# Teste Prático - Iniflex

Este projeto gerencia funcionários, realizando operações CRUD, cálculos de salário, agrupamentos e relatórios conforme os requisitos especificados.

---

## 🛠️ Funcionalidades Implementadas

- [x] **3.1** – Inserir todos os funcionários, na mesma ordem e informações da tabela acima.
- [x] **3.2** – Remover o funcionário “João” da lista.
- [x] **3.3** – Imprimir todos os funcionários com todas suas informações, sendo que:
  - Informação de data deve ser exibida no formato `dd/mm/aaaa`.
  - Informação de valor numérico deve ser exibida formatada com separador de milhar como ponto e decimal como vírgula.
- [x] **3.4** – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
- [x] **3.5** – Agrupar os funcionários por função em um `Map<String, List<Funcionario>>`
- [x] **3.6** – Imprimir os funcionários, agrupados por função.
- [x] **3.8** – Imprimir os funcionários que fazem aniversário nos meses `10` e `12`.
- [x] **3.9** – Imprimir o funcionário com a maior idade, exibindo os atributos: **nome** e **idade**.
- [x] **3.10** – Imprimir a lista de funcionários por ordem alfabética.
- [x] **3.11** – Imprimir o total dos salários dos funcionários.
- [x] **3.12** – Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é **R$ 1212,00**.

---

## 🧪 Testes Unitários

### **Estrutura**
- **Classe**: `FuncionarioServiceImplUnitTest` (JUnit 5).  
- **Cobertura**: Valida CRUD, formatação, cálculos e tratamento de erros.

### **Casos de Teste**
| Cenário | Descrição |  
|---------|-----------|  
| `salvarFuncionario` | Valida inserção com dados corretos e tratamento de datas/salários inválidos. |  
| `atualizarTodosFuncionarios` | Verifica aumento de 10% nos salários. |  
| `deletarFuncionario` | Testa remoção por ID e trata índices inválidos. |  
| `buscarFuncionarioMaisVelho` | Retorna o funcionário com a data de nascimento mais antiga. |  
| `imprimirAniversariantes` | Filtra funcionários pelos meses 10 e 12. |  

---

## 🗃️ Banco de Dados

### **Configuração Automática**
- **Classe**: `JPA` (utiliza Hibernate + PostgreSQL).  
- **Lógica**:  
  1. Conecta ao banco `postgres` (default do PostgreSQL).  
  2. Verifica se o banco especificado em `jdbc:postgresql://localhost:5432/nome_banco` existe.  
  3. Cria o banco automaticamente se não existir via `CREATE DATABASE`.  

### **Modelo de Dados**
- **Entidade**: `Funcionario` com campos:  
  ```java
  Long id;  
  String nome;  
  LocalDate dataNascimento;  
  BigDecimal salario;  
  String funcao;
  ```

---

## ▶️ Execução

### **Pré-requisitos**
1. PostgreSQL instalado.  
2. Configurar `persistence.xml` com usuário/senha do banco.