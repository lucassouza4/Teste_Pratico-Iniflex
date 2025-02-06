/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.iniflex.testepratico.service;

import com.iniflex.testepratico.model.Funcionario;
import java.io.BufferedReader;
import java.util.List;

/**
 *
 * @author lucas
 */
public interface FuncionarioService {
    void salvarFuncionarios(List<Funcionario> funcionarios);
    void salvarFuncionario(BufferedReader reader);
    void atualizarFuncionarios(BufferedReader reader);
    void deletarFuncionario(BufferedReader reader);
    void buscarTodosFuncionarios();
    void agruparFuncionarios();
    void calcularTotalSalario();
    void calcularQuantidadeSalarioMinimo();
    void imprimirAniversariantes(BufferedReader reader);
    void buscarFuncionarioMaisVelho();
}
