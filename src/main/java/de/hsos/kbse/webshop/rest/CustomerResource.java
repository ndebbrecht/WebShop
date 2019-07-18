/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.webshop.rest;

import de.hsos.kbse.webshop.entities.Customer;
import de.hsos.kbse.webshop.repositories.CustomerRepository;
import de.hsos.kbse.webshop.util.UserManager;
import java.io.Serializable;
import java.net.URI;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
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
    private UserManager userManager;
    @Inject
    private Jsonb jsonb;
    @Context
    UriInfo uriinfo;
    
    @GET
    public Response getCustomers(@QueryParam("email")String email, @QueryParam("password")String password){
        if(userManager.isAdmin(email, password)){
            return Response.ok(jsonb.toJson(customerRepo.findAll())).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
    
    @GET
    @Path("my")
    public Response getCustomer(@QueryParam("email")String email, @QueryParam("password")String password){
        if(!userManager.isUser(email, password)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        try {
            Customer c = userManager.getValidCustomer(email, password);
            if(c == null){
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            
            // Create cart:
            URI uriToNewCart = uriinfo.getBaseUriBuilder().path("/cart/new").build();
            Link linkToNewCart = Link.fromUri(uriToNewCart).rel("collection").type("application/json").param("method", "GET").build();
            
            return Response.ok(jsonb.toJson(c)).links(linkToNewCart).build();
        } catch (NullPointerException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @POST
    @Path("new")
    public Response newCustomer(
            @QueryParam("firstname")String firstname,
            @QueryParam("lastname")String lastname,
            @QueryParam("email")String email,
            @QueryParam("password")String password,
            @QueryParam("street")String street,
            @QueryParam("postalCode")String postalCode,
            @QueryParam("city")String city,
            @QueryParam("country")String country
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
        
        // View the customer:
        URI uriToCustomer = uriinfo.getBaseUriBuilder().path("/customer/my").build();
        Link linkToCustomer = Link.fromUri(uriToCustomer).rel("collection").type("application/json").param("method", "GET").build();
        
        return Response.ok(jsonb.toJson(c)).links(linkToCustomer).build();
    }
    
    @PUT
    @Path("promote")
    public Response promote(@QueryParam("id")Long id, @QueryParam("email")String email, @QueryParam("password")String password){
        if(userManager.isAdmin(email, password)){
            try {
                Customer c = customerRepo.findById(id);
                c.setIsAdmin(true);
                customerRepo.merge(c);
            } catch(NullPointerException e){
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            
            // View the product:
            URI uriToCustomers = uriinfo.getBaseUriBuilder().path(this.getClass()).build();
            Link linkToCustomers = Link.fromUri(uriToCustomers).rel("collection").type("application/json").param("method", "GET").build(); 
           
            return Response.ok(jsonb.toJson(c)).links(linkToCustomers).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
    
    @PUT
    @Path("demote")
    public Response demote(@QueryParam("id")Long id, @QueryParam("email")String email, @QueryParam("password")String password){
        if(userManager.isAdmin(email, password)){
            try {
                Customer c = customerRepo.findById(id);
                c.setIsAdmin(false);
                customerRepo.merge(c);
            } catch(NullPointerException e){
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            
            // View the product:
            URI uriToCustomers = uriinfo.getBaseUriBuilder().path(this.getClass()).build();
            Link linkToCustomers = Link.fromUri(uriToCustomers).rel("collection").type("application/json").param("method", "GET").build();
            
            return Response.ok(jsonb.toJson(c)).links(linkToCustomers).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
}
