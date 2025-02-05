/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iniflex.testepratico.service;

import com.iniflex.testepratico.dao.FuncionarioDao;
import com.iniflex.testepratico.model.Funcionario;
import java.util.List;

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
    public void saveFuncionario(Funcionario funcionario) {
        funcionarioDao.save(funcionario);
    }

    @Override
    public void updateFuncionario(Funcionario funcionario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteFuncionario(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Funcionario findFuncionarioById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Funcionario> findAllFuncionarios() {
        return this.funcionarioDao.findAll();
    }
    
}
