/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcode.apprest.api;

import com.jcode.apprest.dao.CategoriaDAO;
import com.jcode.apprest.dao.impl.CategoriaDAOImpl;
import com.jcode.apprest.model.Categoria;
import com.jcode.apprest.utilities.BeanCrud;
import com.jcode.apprest.utilities.ParametersDefault;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author JCode
 */
@Singleton
@Path("/categorias")
public class CategoriaAPI {

    private static final Logger LOG = Logger.getLogger(CategoriaAPI.class.getName());
    private final String lookup = "java:/comp/env/";
    private DataSource pool;
    private CategoriaDAO categoriaDAO;

    public CategoriaAPI() {
        try {
            InitialContext cxt = new InitialContext();
            this.pool = (DataSource) cxt.lookup(lookup + "jdbc/dbmyapp");
            if (this.pool != null) {
                LOG.info("Data Source Inicializado exitosamente!");
            } else {
                LOG.info("Error al Inicializar DataSource");
            }
            this.categoriaDAO = new CategoriaDAOImpl(this.pool);
        } catch (NamingException ex) {
            Logger.getLogger(CategoriaAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Path("/paginate")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response paginate(
            @QueryParam("nombre") String nombre,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size) throws SQLException {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("FILTER", nombre);
        parameters.put("SQL_ORDER_BY", " ORDER BY NOMBRE ASC");
        parameters.put("SQL_PAGINATION", " LIMIT " + size + " OFFSET "
                + (page - 1) * size);
        LOG.info(parameters.toString());
        return Response.status(Response.Status.OK).entity(this.categoriaDAO.getPagination(parameters)).build();
    }

    @Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Categoria categoria) throws SQLException {
        LOG.info(categoria.toString());
        return Response.status(Response.Status.OK).entity(this.categoriaDAO.add(categoria, ParametersDefault.getParametersCategoria())).build();
    }

    @Path("/update")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response udpate(Categoria categoria) throws SQLException {
        LOG.info(categoria.toString());
        return Response.status(Response.Status.OK).entity(this.categoriaDAO.update(categoria, ParametersDefault.getParametersCategoria())).build();
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) throws SQLException {
        LOG.info(id.toString());
        return Response.status(Response.Status.OK).entity(this.categoriaDAO.delete(id, ParametersDefault.getParametersCategoria())).build();
    }

}
