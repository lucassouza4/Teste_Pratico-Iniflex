/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.iniflex.testepratico.service;

import com.iniflex.testepratico.model.Funcionario;
import java.util.List;

/**
 *
 * @author lucas
 */
public interface FuncionarioService {
    void saveFuncionario(Funcionario funcionario);
    void updateFuncionario(Funcionario funcionario);
    void deleteFuncionario(Long id);
    Funcionario findFuncionarioById(Long id);
    List<Funcionario> findAllFuncionarios();
}
