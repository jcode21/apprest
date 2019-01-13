/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcode.apprest.conf;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author JCode
 */
@ApplicationPath("/api")
public class AppConfiguration extends ResourceConfig{

    public AppConfiguration() {
        packages("com.jcode.apprest");
    }
    
}
