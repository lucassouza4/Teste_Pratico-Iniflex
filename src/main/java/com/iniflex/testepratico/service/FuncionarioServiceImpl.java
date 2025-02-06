/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iniflex.testepratico.service;

import com.iniflex.testepratico.dao.FuncionarioDao;
import com.iniflex.testepratico.model.Funcionario;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lucas
 */
public class FuncionarioServiceImpl implements FuncionarioService {

    private final FuncionarioDao funcionarioDao;

    private FuncionarioServiceImpl(FuncionarioDao funcionarioDao) {
        this.funcionarioDao = funcionarioDao;
    }
    
    public static FuncionarioServiceImpl build(FuncionarioDao funcionarioDao){
        return new FuncionarioServiceImpl(funcionarioDao);
    }

    @Override
    public void saveFuncionario(BufferedReader reader) {
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

            Funcionario funcionario = Funcionario.build(salario, funcao, nome, dataNascimento);
            funcionarioDao.save(funcionario);
        } catch (IOException e) {
            System.out.println("Erro ao ler valores de entrada!");
        }
        
    }

    @Override
    public void updateFuncionarios(BufferedReader reader) {
        List<Funcionario> funcionarios = funcionarioDao.findAll();
        try {
            System.out.println("Deseja alterar quantos funcionários? ");
            System.out.println("1 - Todos.");
            System.out.println("2 - Somente um.");
            int opcao = lerOpcao(reader);

            System.out.println();

            switch (opcao) {
                case 1 -> atualizarTodosFuncionarios(reader, funcionarios);
                case 2 -> atualizarUmFuncionario(reader, funcionarios);
                default -> System.out.println("Opção inválida.");
            }

            System.out.println();
        } catch (IOException e) {
            System.out.println("Erro de I/O: " + e.getMessage());
        }
    }
    
    private int lerOpcao(BufferedReader reader) throws IOException {
        String input = reader.readLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Por favor, insira um valor numérico!");
            return -1;
        }
    }

    private void atualizarTodosFuncionarios(BufferedReader reader, List<Funcionario> funcionarios) throws IOException {
        System.out.println("Qual o aumento de salário (%): ");
        String input = reader.readLine();
        double percentual;
        try {
            percentual = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Por favor, insira um valor numérico!");
            return;
        }

        final double fatorAumento = 1 + percentual / 100;
        funcionarios.forEach(funcionario -> {
            BigDecimal novoSalario = funcionario.getSalario().multiply(BigDecimal.valueOf(fatorAumento));
            funcionario.setSalario(novoSalario);
        });
        funcionarioDao.update(funcionarios);
    }
    
    private void atualizarUmFuncionario(BufferedReader reader, List<Funcionario> funcionarios) throws IOException {
        System.out.println("Qual funcionário deseja alterar? ");
        for (int i = 0; i < funcionarios.size(); i++) {
            Funcionario f = funcionarios.get(i);
            System.out.println(i + " - " + f.getNome() + ": " + f.getFuncao());
        }
        int indiceFuncionario = lerOpcao(reader);
        if (indiceFuncionario < 0 || indiceFuncionario >= funcionarios.size()) {
            System.out.println("Índice de funcionário inválido.");
            return;
        }
        Funcionario funcionarioSelecionado = funcionarios.get(indiceFuncionario);

        System.out.println("O que deseja alterar?");
        System.out.println("1 - Nome.");
        System.out.println("2 - Data de nascimento.");
        System.out.println("3 - Função.");
        System.out.println("4 - Salário.");
        int opcaoAtualizacao = lerOpcao(reader);

        switch (opcaoAtualizacao) {
            case 1 -> atualizarNome(reader, funcionarioSelecionado);
            case 2 -> atualizarDataNascimento(reader, funcionarioSelecionado);
            case 3 -> atualizarFuncao(reader, funcionarioSelecionado);
            case 4 -> atualizarSalario(reader, funcionarioSelecionado);
            default -> System.out.println("Opção inválida para alteração.");
        }
        funcionarioDao.update(funcionarioSelecionado);
    }

    private void atualizarNome(BufferedReader reader, Funcionario funcionario) throws IOException {
        System.out.println("Entre com o novo nome: ");
        String novoNome = reader.readLine();
        funcionario.setNome(novoNome);
    }

    private void atualizarDataNascimento(BufferedReader reader, Funcionario funcionario) throws IOException {
        System.out.println("Entre com a nova data de nascimento (dd/MM/yyyy): ");
        String input = reader.readLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate novaData = LocalDate.parse(input, formatter);
            funcionario.setDataNascimento(novaData);
        } catch (Exception e) {
            System.out.println("Data inválida. Por favor, siga o formato dd/MM/yyyy.");
        }
    }

    private void atualizarFuncao(BufferedReader reader, Funcionario funcionario) throws IOException {
        System.out.println("Entre com a nova função: ");
        String novaFuncao = reader.readLine();
        funcionario.setFuncao(novaFuncao);
    }

    private void atualizarSalario(BufferedReader reader, Funcionario funcionario) throws IOException {
        System.out.println("Entre com o novo salário: ");
        String input = reader.readLine();
        try {
            BigDecimal novoSalario = new BigDecimal(input);
            funcionario.setSalario(novoSalario);
        } catch (NumberFormatException e) {
            System.out.println("Salário inválido. Certifique-se de usar um valor numérico.");
        }
    }
    
    @Override
    public void deleteFuncionario(BufferedReader reader) {
        try {
            List<Funcionario> funcionarios = funcionarioDao.findAll();
            System.out.println("Qual funcionário deseja excluir? ");
            for (int i = 0; i < funcionarios.size(); i++) {
                Funcionario f = funcionarios.get(i);
                System.out.println(i + " - " + f.getNome() + ": " + f.getFuncao());
            }
            int indiceFuncionario = lerOpcao(reader);
            if (indiceFuncionario < 0 || indiceFuncionario >= funcionarios.size()) {
                System.out.println("Índice de funcionário inválido.");
                return;
            }
            Funcionario funcionarioSelecionado = funcionarios.get(indiceFuncionario);
            funcionarioDao.delete(funcionarioSelecionado.getId());
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Funcionario findFuncionarioById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void findAllFuncionarios() {
        List<Funcionario> funcionarios =  this.funcionarioDao.findAll();
        funcionarios.forEach(System.out::println);
    }
    
}
