/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.webshop.rest;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
/**
 *
 * @author niklas_debbrecht
 */
@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {
    private Set<Class<?>> classes = new HashSet<>();
    
    public JAXRSConfiguration(){
        this.classes.add(de.hsos.kbse.webshop.rest.CustomerResource.class);
        this.classes.add(de.hsos.kbse.webshop.rest.CartResource.class);
        this.classes.add(de.hsos.kbse.webshop.rest.ProductResource.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindAsContract(UserManager.class);
            }
        });
    }
    }
    
    @Override
    public Set<Class<?>> getClasses(){
        return this.classes;
    }
}
