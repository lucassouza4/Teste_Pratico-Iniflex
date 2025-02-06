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
import java.nio.charset.StandardCharsets;
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
        
        System.out.println("\n");
        while (op != 0) {
           System.out.println("1 - Adicionar funcionário. ");
           System.out.println("2 - Listar funcionários. ");
           System.out.println("3 - Listar funcionários por função. ");
           System.out.println("4 - Atualizar funcionário. ");
           System.out.println("5 - Excluir funcionário. ");
           System.out.println("6 - Total dos salários. ");
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
               case 1 -> funcionarioService.saveFuncionario(reader);
               case 2 -> funcionarioService.findAllFuncionarios();
               case 3 -> funcionarioService.agruparFuncionarios();
               case 4 -> funcionarioService.updateFuncionarios(reader);
               case 5 -> funcionarioService.deleteFuncionario(reader);
               case 6 -> funcionarioService.totalSalario(reader);
               case 0 -> System.out.println("Saindo...");
               default -> System.out.println("Opção inválida.");
           }
           System.out.println("\n");
        }
         reader.close();
    }
}
