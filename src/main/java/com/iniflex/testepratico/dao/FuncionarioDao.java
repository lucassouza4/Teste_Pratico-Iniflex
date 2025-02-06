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
    void save(Funcionario funcionario);
    void update(Funcionario funcionario);
    void update(List<Funcionario> funcionarios);
    void delete(Long id);
    Funcionario findById(Long id);
    List<Funcionario> findByAniversarioMeses(List<Integer> meses);
    List<Funcionario> findAll();
}
