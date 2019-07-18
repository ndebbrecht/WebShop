/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.webshop.test;

import de.hsos.kbse.webshop.rest.ProductResource;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author niklas_debbrecht
 */
public class ProductResourceTest extends JerseyTest {
    
    @Override
    protected Application configure() {
        return new ResourceConfig(ProductResource.class);
    }
    
    /*@Test
    public void allProducts(){
        Response response = target("products").request().get();
        Assert.assertEquals("Status 200", 200, response.getStatus());
    }*/
}
