/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/AnnotationType.java to edit this template
 */
package com.iniflex.testepratico.util;

import java.time.format.DateTimeFormatter;

/**
 *
 * @author lucas
 */
public @interface DataFormat {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
}
