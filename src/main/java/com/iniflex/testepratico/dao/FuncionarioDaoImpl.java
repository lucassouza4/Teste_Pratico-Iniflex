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

    public static FuncionarioDaoImpl build(EntityManagerFactory entityManagerFactory) {
        return new FuncionarioDaoImpl(entityManagerFactory);
    }

    @Override
    public void salvar(Funcionario funcionario) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(funcionario);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erro ao salvar funcionário! ", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void salvar(List<Funcionario> funcionarios) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            for (Funcionario funcionario : funcionarios) {
                em.persist(funcionario);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erro ao salvar lista de funcionários! ", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void atualizar(Funcionario funcionario) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(funcionario);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erro ao atualizar funcionário! ", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void atualizar(List<Funcionario> funcionarios) {
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
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erro ao atualizar lista de funcionários! ", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void deletar(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            Funcionario funcionario = em.find(Funcionario.class, id);
            if (funcionario != null) {
                em.remove(funcionario);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erro ao excluir funcionário! ", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Funcionario> buscarTodos() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Funcionario> query = em.createQuery("SELECT f FROM Funcionario f ORDER BY f.nome", Funcionario.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar funcionários", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Funcionario> buscarPorMeses(List<Integer> meses) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Funcionario> query = em.createQuery(
                    "SELECT f FROM Funcionario f WHERE MONTH(f.dataNascimento) IN :meses ORDER BY MONTH(f.dataNascimento)", Funcionario.class);
            query.setParameter("meses", meses);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar funcionários por mês de aniversário", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Funcionario buscarFuncionarioMaisVelho() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Funcionario> query = em.createQuery(
                    "SELECT f FROM Funcionario f ORDER BY f.dataNascimento ASC", Funcionario.class);
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar funcionário", e);
        } finally {
            em.close();
        }
    }
}
