/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iniflex.testepratico.model;

import com.iniflex.testepratico.util.DataFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author lucas
 */

@Entity
@Table(name = "funcionarios")
public class Funcionario extends Pessoa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private BigDecimal salario;
    
    private String funcao;
    
    protected Funcionario() {
        super(null, null);
    }
    
    private Funcionario(BigDecimal salario, String funcao, String nome, LocalDate dataNascimento){
        super(nome,dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }
    
    public static Funcionario build(BigDecimal salario, String funcao, String nome, LocalDate dataNascimento){
        return new Funcionario(salario,funcao,nome,dataNascimento);
    }
    
    public Long getId() {
        return id;
    }
    
    public BigDecimal getSalario(){
        return salario;
    }
    
    public BigDecimal setSalario(BigDecimal salario){
        return this.salario = salario;
    }
    
    public String getFuncao(){
        return funcao;
    }
    
    public String setFuncao(String funcao){
        return this.funcao = funcao;
    }
    
     @Override
    public String toString() {
        return "nome ='" + super.getNome() + "'" + 
                ", nascimento = '"+ super.getDataNascimento().format(DataFormat.formatter) + "'" + 
                ", salario = '"+ salario + "'" +
                ", funcao = '"+ funcao + "'"; 
    }
}
