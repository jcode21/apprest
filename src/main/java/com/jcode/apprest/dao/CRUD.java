/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcode.apprest.dao;

import com.jcode.apprest.utilities.BeanCrud;
import com.jcode.apprest.utilities.BeanPagination;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author JCode
 * @param <T>
 */
public interface CRUD<T> {

    BeanPagination getPagination(HashMap<String, Object> parameters, Connection conn) throws SQLException;

    BeanCrud getPagination(HashMap<String, Object> parameters) throws SQLException;

    BeanCrud add(T t, HashMap<String, Object> parameters) throws SQLException;

    BeanCrud update(T t, HashMap<String, Object> parameters) throws SQLException;

    BeanCrud delete(Integer id, HashMap<String, Object> parameters) throws SQLException;

    T getForId(Long id);

}
