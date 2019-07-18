/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.webshop.test;

import de.hsos.kbse.webshop.rest.CustomerResource;
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
public class CustomerResourceTest extends JerseyTest {
    
    @Override
    protected Application configure() {
        return new ResourceConfig(CustomerResource.class);
    }

    /*@Test
    public void createCustomerTest() {
        Response response = target("customers/new").request().post(Entity.json("{'firstname':'test1','lastname':'test2','email':'test@test.de','password':'test123','street':'teststr.1','postalCode':'12345','city':'teststadt','country':'DE'}"));
        System.out.print(response.toString());
        Customer c = response.readEntity(Customer.class);
        Customer c2 = new Customer("test1","test2","test@test.de","test123","teststr.1","12345","teststadt","DE",false);
        Assert.assertTrue(c.getFirstname().equals(c2.getFirstname()));
        Assert.assertTrue(c.getLastname().equals(c2.getLastname()));
        Assert.assertTrue(c.getEmail().equals(c2.getEmail()));
        Assert.assertTrue(c.getPassword().equals(c2.getPassword()));
        Assert.assertTrue(c.getStreet().equals(c2.getStreet()));
        Assert.assertTrue(c.getPostalCode().equals(c2.getPostalCode()));
        Assert.assertTrue(c.getCity().equals(c2.getCity()));
        Assert.assertTrue(c.getCountry().equals(c2.getCountry()));
        Assert.assertTrue(c.isIsAdmin() == c2.isIsAdmin());
    }*/
    
    /*@Test
    public void getCustomerTest(){
        Response result = target("customers/my")
                .queryParam("email", "admin@root.de")
                .queryParam("password", "password123")
                .request()
                .get();
        //Assert.assertTrue(c.isIsAdmin());
        Assert.assertEquals("Status 200", 200, result.getStatus());
    }*/
}
