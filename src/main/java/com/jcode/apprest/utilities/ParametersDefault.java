/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcode.apprest.utilities;

import java.util.HashMap;

/**
 *
 * @author JCode
 */
public class ParametersDefault {

    public static HashMap<String, Object> getParametersCategoria() {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("FILTER", "");
        parameters.put("SQL_ORDER_BY", " ORDER BY NOMBRE ASC");
        parameters.put("SQL_PAGINATION", "LIMIT 10 OFFSET 0");
        return parameters;
    }

}
