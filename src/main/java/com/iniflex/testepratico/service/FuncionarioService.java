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
    void saveFuncionario(BufferedReader reader);
    void updateFuncionarios(BufferedReader reader);
    void deleteFuncionario(BufferedReader reader);
    Funcionario findFuncionarioById(Long id);
    void findAllFuncionarios();
    void agruparFuncionarios();
    void totalSalario();
    void salarioMinimo();
    void imprimirAniversariantes(BufferedReader reader);
    void findFuncionarioMaisVelho();
}
