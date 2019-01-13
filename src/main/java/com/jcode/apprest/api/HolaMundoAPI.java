/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcode.apprest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author JCode
 */
@Path("/saludo")
public class HolaMundoAPI {

    @Path("/saludar")
    @GET
    public String getSaludo() {
        return "Hola amiguitos xD!";
    }
}
