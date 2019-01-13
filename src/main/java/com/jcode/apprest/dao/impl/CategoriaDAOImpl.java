/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcode.apprest.dao.impl;

import com.jcode.apprest.dao.CategoriaDAO;
import com.jcode.apprest.dao.SQLCloseable;
import com.jcode.apprest.model.Categoria;
import com.jcode.apprest.utilities.BeanCrud;
import com.jcode.apprest.utilities.BeanPagination;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author JCode
 */
public class CategoriaDAOImpl implements CategoriaDAO {

    private static final Logger LOG = Logger.getLogger(CategoriaDAOImpl.class.getName());
    private final DataSource pool;

    public CategoriaDAOImpl(DataSource pool) {
        this.pool = pool;
    }

    @Override
    public BeanPagination getPagination(HashMap<String, Object> parameters, Connection conn) throws SQLException {
        BeanPagination beanPagination = new BeanPagination();
        List<Categoria> list = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;
        try {
            pst = conn.prepareStatement("SELECT COUNT(IDCATEGORIA) AS COUNT FROM CATEGORIA WHERE "
                    + "NOMBRE LIKE CONCAT('%',?,'%')");
            pst.setString(1, String.valueOf(parameters.get("FILTER")));
            LOG.info(pst.toString());
            rs = pst.executeQuery();
            while (rs.next()) {
                beanPagination.setCount_filter(rs.getInt("COUNT"));
                if (rs.getInt("COUNT") > 0) {
                    pst = conn.prepareStatement("SELECT * FROM CATEGORIA WHERE NOMBRE LIKE CONCAT('%',?,'%') "
                            + String.valueOf(parameters.get("SQL_ORDER_BY")) + " " + String.valueOf(parameters.get("SQL_PAGINATION")));
                    pst.setString(1, String.valueOf(parameters.get("FILTER")));
                    LOG.info(pst.toString());
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        Categoria categoria = new Categoria();
                        categoria.setIdcategoria(rs.getInt("IDCATEGORIA"));
                        categoria.setNombre(rs.getString("NOMBRE"));
                        list.add(categoria);
                    }
                }
            }
            beanPagination.setList(list);
            rs.close();
            pst.close();
        } catch (SQLException e) {
            throw e;
        }
        return beanPagination;
    }

    @Override
    public BeanCrud getPagination(HashMap<String, Object> parameters) throws SQLException {
        BeanCrud beanCrud = new BeanCrud();
        try (Connection conn = this.pool.getConnection()) {
            beanCrud.setBeanPagination(getPagination(parameters, conn));
        } catch (SQLException e) {
            throw e;
        }
        return beanCrud;
    }

    @Override
    public BeanCrud add(Categoria t, HashMap<String, Object> parameters) throws SQLException {
        BeanCrud beanCrud = new BeanCrud();
        PreparedStatement pst;
        ResultSet rs;
        try (Connection conn = this.pool.getConnection();
                SQLCloseable finish = conn::rollback;) {
            conn.setAutoCommit(false);
            pst = conn.prepareStatement("SELECT COUNT(IDCATEGORIA) AS COUNT FROM CATEGORIA WHERE NOMBRE = ?");
            pst.setString(1, t.getNombre());
            LOG.info(pst.toString());
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getInt("COUNT") == 0) {
                    //REALIZAMOS LA TRANSACCIÓN
                    pst = conn.prepareStatement("INSERT INTO CATEGORIA(NOMBRE) VALUES(?)");
                    pst.setString(1, t.getNombre());
                    LOG.info(pst.toString());
                    pst.executeUpdate();
                    conn.commit();
                    beanCrud.setMessage_server("ok");
                    beanCrud.setBeanPagination(getPagination(parameters, conn));
                } else {
                    //RECHAZAMOS EL REGISTRO
                    beanCrud.setMessage_server("No se registró, ya existe una Categoría con el nombre ingresado");
                }
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            throw e;
        }
        return beanCrud;
    }

    @Override
    public BeanCrud update(Categoria t, HashMap<String, Object> parameters) throws SQLException {
        BeanCrud beanCrud = new BeanCrud();
        PreparedStatement pst;
        ResultSet rs;
        try (Connection conn = this.pool.getConnection();
                SQLCloseable finish = conn::rollback;) {
            conn.setAutoCommit(false);
            pst = conn.prepareStatement("SELECT COUNT(IDCATEGORIA) AS COUNT FROM CATEGORIA WHERE NOMBRE = ? AND IDCATEGORIA != ?");
            pst.setString(1, t.getNombre());
            pst.setInt(2, t.getIdcategoria());
            LOG.info(pst.toString());
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getInt("COUNT") == 0) {
                    //REALIZAMOS LA TRANSACCIÓN
                    pst = conn.prepareStatement("UPDATE CATEGORIA SET NOMBRE = ? WHERE IDCATEGORIA = ?");
                    pst.setString(1, t.getNombre());
                    pst.setInt(2, t.getIdcategoria());
                    LOG.info(pst.toString());
                    pst.executeUpdate();
                    conn.commit();
                    beanCrud.setMessage_server("ok");
                    beanCrud.setBeanPagination(getPagination(parameters, conn));
                } else {
                    //RECHAZAMOS EL REGISTRO
                    beanCrud.setMessage_server("No se modificó, ya existe una Categoría con el nombre ingresado");
                }
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            throw e;
        }
        return beanCrud;
    }

    @Override
    public BeanCrud delete(Integer id, HashMap<String, Object> parameters) throws SQLException {
        BeanCrud beanCrud = new BeanCrud();
        try (Connection conn = this.pool.getConnection();
                SQLCloseable finish = conn::rollback;) {
            conn.setAutoCommit(false);
            try (PreparedStatement pst = conn.prepareStatement("DELETE FROM CATEGORIA WHERE IDCATEGORIA = ?")) {
                pst.setInt(1, id);
                LOG.info(pst.toString());
                pst.executeUpdate();
                conn.commit();
                beanCrud.setMessage_server("ok");
                beanCrud.setBeanPagination(getPagination(parameters, conn));
            }
        } catch (SQLException e) {
            throw e;
        }
        return beanCrud;
    }

    @Override
    public Categoria getForId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
