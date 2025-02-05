/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.iniflex.testepratico;

import com.iniflex.testepratico.dao.FuncionarioDao;
import com.iniflex.testepratico.dao.FuncionarioDaoImpl;
import com.iniflex.testepratico.model.Funcionario;
import com.iniflex.testepratico.service.FuncionarioService;
import com.iniflex.testepratico.service.FuncionarioServiceImpl;
import com.iniflex.testepratico.util.JPA;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lucas
 */
public class TestePratico {

    public static void main(String[] args) throws IOException {
        EntityManagerFactory entityManager = JPA.getEntityManagerFactory();
        FuncionarioDao funcionarioDao = FuncionarioDaoImpl.build(entityManager);
        FuncionarioService funcionarioService = FuncionarioServiceImpl.build(funcionarioDao);
        
        System.setOut(new PrintStream(System.out,true,StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        
        int op = -1;
        
         while (op != 0) {
            System.out.println("1 - Adicionar funcionario. ");
            System.out.println("2 - Listar funcionarios. ");
            System.out.println("0 - Sair. ");

            String input = reader.readLine();

            try {
                op = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, insira um número válido.");
                continue;
            }
            System.out.println("\n");
            switch (op) {
                case 1 -> adicionarFuncionario(funcionarioService, reader);
                case 2 -> buscarFuncionarios(funcionarioService);
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
            System.out.println("\n");
        }
    }
    
    public static void adicionarFuncionario(FuncionarioService funcionarioService, BufferedReader reader){
        try {
            System.out.println("Nome: ");
            String nome = reader.readLine();

            System.out.println("Data de nascimento (formato dd/MM/yyyy): ");
            String dataNascimentoStr = reader.readLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);

            System.out.println("Salario: ");
            BigDecimal salario = new BigDecimal(reader.readLine());
            
            System.out.println("Funcao: ");
            String funcao = reader.readLine();

            Funcionario f1 = Funcionario.build(salario, funcao, nome, dataNascimento);
            funcionarioService.saveFuncionario(f1);
            
        } catch (IOException e) {
            System.out.println("Erro ao ler valores de entrada!");
        }
    }
    
    public static void buscarFuncionarios(FuncionarioService funcionarioService){
        List<Funcionario> funcionarios = funcionarioService.findAllFuncionarios();
        funcionarios.forEach(System.out::println);
    }
}
