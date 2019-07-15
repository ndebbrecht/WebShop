/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.webshop.rest;

import de.hsos.kbse.webshop.entities.Customer;
import de.hsos.kbse.webshop.repositories.CustomerRepository;
import de.hsos.kbse.webshop.util.JsonbFactory;
import java.io.Serializable;
import java.util.Collection;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author niklas_debbrecht
 */
@RequestScoped
@Path("customers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class CustomerResource implements Serializable {
    @Inject
    private CustomerRepository customerRepo;
    @Inject
    private Jsonb jsonb;
    @Context
    UriInfo uriinfo;
    
    @GET
    public Response getCustomers(){
        return Response.ok(jsonb.toJson(customerRepo.findAll())).build();
    }
    
    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id")Long id){
        try {
            Customer c = customerRepo.findById(id);
            return Response.ok(jsonb.toJson(c)).build();
        } catch (NullPointerException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @POST
    @Path("new/{firstname}/{lastname}/{email}/{password}/{street}/{postalCode}/{city}/{country}")
    public Response newCustomer(
            @PathParam("firstname")String firstname,
            @PathParam("lastname")String lastname,
            @PathParam("email")String email,
            @PathParam("password")String password,
            @PathParam("street")String street,
            @PathParam("postalCode")String postalCode,
            @PathParam("city")String city,
            @PathParam("country")String country
    ){
        Customer c = new Customer();
        c.setFirstname(firstname);
        c.setLastname(lastname);
        c.setEmail(email);
        c.setPassword(password);
        c.setStreet(street);
        c.setPostalCode(postalCode);
        c.setCity(city);
        c.setCountry(country);
        c.setIsAdmin(false);
        
        customerRepo.persist(c);
        
        return Response.ok(jsonb.toJson(c)).build();
    }
}