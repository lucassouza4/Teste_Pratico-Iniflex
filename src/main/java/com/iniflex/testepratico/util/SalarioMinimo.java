/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.iniflex.testepratico.util;

import java.math.BigDecimal;

/**
 *
 * @author lucas
 */
public enum SalarioMinimo {
    VALOR(new BigDecimal("1212.00"));

    public final BigDecimal valor;

    SalarioMinimo(BigDecimal valor) {
        this.valor = valor;
    }
}
