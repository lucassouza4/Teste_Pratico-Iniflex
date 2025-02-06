/*
 * Click nbfs://nbhost/SystentityManagerileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystentityManagerileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iniflex.testepratico.util;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import java.sql.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author lucas
 */
public class JPA {
    private static final EntityManagerFactory entityManager = initEntityManagerFactory();

    private static EntityManagerFactory initEntityManagerFactory() {
        
        try {
            Configuration configuration = new Configuration().configure();

            String jdbcUrl = configuration.getProperty("hibernate.connection.url");
            String username = configuration.getProperty("hibernate.connection.username");
            String password = configuration.getProperty("hibernate.connection.password");

            createDatabaseIfNeeded(jdbcUrl, username, password);

            return Persistence.createEntityManagerFactory("testePraticoPU");
        } catch (HibernateException ex) {
            System.err.println("Falha ao criar EntityManagerFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static void createDatabaseIfNeeded(String jdbcUrl, String username, String password) {
        try {
            String cleanUrl = jdbcUrl.split("\\?")[0];
            String dbName = cleanUrl.substring(cleanUrl.lastIndexOf("/") + 1);

            dbName = dbName.toLowerCase();
            
            String defaultUrl = jdbcUrl.replace(dbName, "postgres");
            
            try (Connection conn = DriverManager.getConnection(defaultUrl, username, password);
                 Statement stmt = conn.createStatement()) {
                
                ResultSet rs = stmt.executeQuery(
                    "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'"
                );
                
                if (!rs.next()) {
                    stmt.executeUpdate("CREATE DATABASE " + dbName);
                    System.out.println("Banco de dados " + dbName + " criado com sucesso!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao criar banco de dados", e);
        }
    }
    
    
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManager;
    }

    public static void shutdown() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
