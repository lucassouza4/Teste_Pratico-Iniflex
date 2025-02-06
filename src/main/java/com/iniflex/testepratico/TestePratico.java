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
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lucas
 */
public class TestePratico {

    public static void main(String[] args) throws IOException {
        // Injeção de dependências
        EntityManagerFactory entityManager = JPA.getEntityManagerFactory();
        FuncionarioDao funcionarioDao = FuncionarioDaoImpl.build(entityManager);
        FuncionarioService funcionarioService = FuncionarioServiceImpl.build(funcionarioDao);

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        int op = -1;

        System.out.println("\n");
        while (op != 0) {
            System.out.println("1 - Adicionar funcionários a partir de arquivo.");
            System.out.println("2 - Adicionar funcionário. ");
            System.out.println("3 - Listar funcionários. ");
            System.out.println("4 - Listar funcionários por função. ");
            System.out.println("5 - Atualizar funcionário. ");
            System.out.println("6 - Excluir funcionário. ");
            System.out.println("7 - Total dos salários. ");
            System.out.println("8 - Quantidade de salários mínimo por funcionário. ");
            System.out.println("9 - Aniversariantes. ");
            System.out.println("10 - Funcionário de maior idade. ");
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
                case 1 -> {
                    try {
                        List<Funcionario> funcionarios = lerCsv();
                        funcionarioService.salvarFuncionarios(funcionarios);
                    } catch (CsvValidationException ex) {
                        System.out.println("Erro ao ler o arquivo");
                    }
                }
                case 2 ->
                    funcionarioService.salvarFuncionario(reader);
                case 3 ->
                    funcionarioService.buscarTodosFuncionarios();
                case 4 ->
                    funcionarioService.agruparFuncionarios();
                case 5 ->
                    funcionarioService.atualizarFuncionarios(reader);
                case 6 ->
                    funcionarioService.deletarFuncionario(reader);
                case 7 ->
                    funcionarioService.calcularTotalSalario();
                case 8 ->
                    funcionarioService.calcularQuantidadeSalarioMinimo();
                case 9 ->
                    funcionarioService.imprimirAniversariantes(reader);
                case 10 ->
                    funcionarioService.buscarFuncionarioMaisVelho();
                case 0 ->
                    System.out.println("Saindo...");
                default ->
                    System.out.println("Opção inválida.");
            }
            System.out.println("\n");
        }
        reader.close();
    }

    private static List<Funcionario> lerCsv() throws CsvValidationException {
        String caminho = "funcionarios.csv";
        List<Funcionario> funcionarios = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(caminho))) {
            String[] linha;
            boolean primeiraLinha = true;
            while ((linha = reader.readNext()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                String nome = linha[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataNascimento = LocalDate.parse(linha[1], formatter);
                BigDecimal salario = BigDecimal.valueOf(Double.parseDouble(linha[2]));
                String funcao = linha[3];

                Funcionario funcionario = Funcionario.build(salario, funcao, nome,dataNascimento);
                funcionarios.add(funcionario);
            }
        } catch (IOException e) {
            System.out.println("Erro ao encontrar o arquivo");
        }
        return funcionarios;
    }
}
