/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.iniflex.testepratico.dao;

import com.iniflex.testepratico.model.Funcionario;
import java.util.List;

/**
 *
 * @author lucas
 */
public interface FuncionarioDao {
    void salvar(Funcionario funcionario);
    void salvar(List<Funcionario> funcionario);
    void atualizar(Funcionario funcionario);
    void atualizar(List<Funcionario> funcionarios);
    void deletar(Long id);
    List<Funcionario> buscarPorMeses(List<Integer> meses);
    List<Funcionario> buscarTodos();
    Funcionario buscarFuncionarioMaisVelho();
}
