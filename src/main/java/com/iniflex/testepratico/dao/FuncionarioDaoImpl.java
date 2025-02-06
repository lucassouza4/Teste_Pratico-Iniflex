/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iniflex.testepratico.dao;

import com.iniflex.testepratico.model.Funcionario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author lucas
 */
public class FuncionarioDaoImpl implements FuncionarioDao {

    private final EntityManagerFactory entityManagerFactory;

    private FuncionarioDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    
    public static FuncionarioDaoImpl build(EntityManagerFactory entityManagerFactory){
        return new FuncionarioDaoImpl(entityManagerFactory);
    }

    @Override
    public void save(Funcionario funcionario) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(funcionario);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw new RuntimeException("Erro ao salvar funcionário! ",e);
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Funcionario funcionario) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(funcionario);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw new RuntimeException("Erro ao atualizar funcionário! ",e);
        } finally {
            em.close();
        }
    }
    
    @Override
    public void update(List<Funcionario> funcionarios) {
    EntityManager em = entityManagerFactory.createEntityManager();
    EntityTransaction tx = null;
    try {
        tx = em.getTransaction();
        tx.begin();
        
        for (Funcionario funcionario : funcionarios) {
            em.merge(funcionario);
        }
        
        tx.commit();
    } catch (Exception e) {
        if (tx != null && tx.isActive()) tx.rollback();
        throw new RuntimeException("Erro ao atualizar lista de funcionários! ", e);
    } finally {
        em.close();
    }
}

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Funcionario findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Funcionario> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Funcionario> query = em.createQuery("SELECT f FROM Funcionario f", Funcionario.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar funcionários", e);
        } finally {
            em.close();
        }
    }
}
