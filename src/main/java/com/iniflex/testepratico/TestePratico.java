/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.iniflex.testepratico;

import com.iniflex.testepratico.model.Funcionario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author lucas
 */
public class TestePratico {

    public static void main(String[] args) {
         BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Nome: ");
            String nome = reader.readLine();

            System.out.println("Data de nascimento (formato dd/MM/yyyy): ");
            String dataNascimentoStr = reader.readLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
            //System.out.println(dataNascimento);

            System.out.println("Salario: ");
            BigDecimal salario = new BigDecimal(reader.readLine());
            
            System.out.println("Funcao: ");
            String funcao = reader.readLine();

            Funcionario f1 = Funcionario.build(salario, funcao, nome, dataNascimento);
            System.out.println(f1.toString());
            
        } catch (IOException e) {
            System.out.println("Erro ao ler valores de entrada!");
        }
    }
}
