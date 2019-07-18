/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.webshop.rest;

import de.hsos.kbse.webshop.entities.Category;
import de.hsos.kbse.webshop.entities.Product;
import de.hsos.kbse.webshop.repositories.CategoryRepository;
import de.hsos.kbse.webshop.repositories.ProductRepository;
import de.hsos.kbse.webshop.util.UserManager;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author niklas_debbrecht
 */
@RequestScoped
@Path("products")
@Produces({MediaType.APPLICATION_JSON})
//@Consumes({MediaType.APPLICATION_JSON})
public class ProductResource implements Serializable {
    @Inject
    private ProductRepository productRepo;
    @Inject
    private CategoryRepository categoryRepo;
    @Inject
    private Jsonb jsonb;
    @Inject
    private UserManager userManager;
    @Context
    UriInfo uriInfo;
    
    @GET
    public Response getAllProducts(){
        return Response.ok(jsonb.toJson(productRepo.findAll())).build();
    }
    
    @GET
    @Path("{id}")
    public Response getProduct(@PathParam("id")Long id){
        try {
            Product p = productRepo.findById(id);
        
            return Response.ok(jsonb.toJson(p)).build();
        } catch (NullPointerException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("category")
    public Response getCategoryProducts(@QueryParam("id")Long id){
        if(id == null){
            return Response.ok(jsonb.toJson(categoryRepo.findAll())).build();
        }
        try {
            return Response.ok(jsonb.toJson(categoryRepo.findById(id).getProducts())).build();
        } catch (NullPointerException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("find")
    public Response findProducts(@QueryParam("term")String term){
        Collection<Product> pResult = new ArrayList();
        String[] words = term.split(" ");
        productRepo.findAll().forEach(p -> {
            for(String word: words){
                if(p.getName().contains(word)){
                    pResult.add(p);
                } else if(p.getDescription().contains(word)){
                    pResult.add(p);
                } else if(p.getCategory().getName().contains(word)){
                    pResult.add(p);
                }
            }
        });
        
        // View the first result product:
        URI uriToProduct = uriInfo.getBaseUriBuilder().path("/products/"+pResult.iterator().next().getId()).build();
        Link linkToProduct = Link.fromUri(uriToProduct).rel("collection").type("application/json").param("method", "GET").build();
        
        return Response.ok(jsonb.toJson(pResult)).links(linkToProduct).build();
    }
    
    @POST
    @Path("new")
    public Response addProduct(
            @QueryParam("name")String name,
            @QueryParam("price")double price,
            @QueryParam("description")String description,
            @QueryParam("categoryId")Long categoryId,
            @QueryParam("email")String email,
            @QueryParam("password")String password
            ){
        if(!userManager.isAdmin(email, password)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Product p = new Product();
        p.setName(name);
        p.setPrice(price);
        p.setDescription(description);
        System.out.println(jsonb.toJson(p));
        Category c = categoryRepo.findById(categoryId);
        p.setCategory(c);
        c.addProduct(p);
        productRepo.persist(p);
        categoryRepo.merge(c);
        
        // View the product:
        URI uriToProduct = uriInfo.getBaseUriBuilder().path("/products/"+p.getId()).build();
        Link linkToProduct = Link.fromUri(uriToProduct).rel("collection").type("application/json").param("method", "GET").build();
        
        return Response.ok(jsonb.toJson(p)).links(linkToProduct).build();
    }
    
    @POST
    @Path("category/new")
    public Response addCategory(@QueryParam("name")String name, @QueryParam("email")String email, @QueryParam("password")String password){
        if(!userManager.isAdmin(email, password)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Category c = new Category();
        c.setName(name);
        categoryRepo.persist(c);
        
        // View the category:
        URI uriToCategory = uriInfo.getBaseUriBuilder().path("/products/category/"+c.getId()).build();
        Link linkToCategory = Link.fromUri(uriToCategory).rel("collection").type("application/json").param("method", "GET").build();
        
        return Response.ok(jsonb.toJson(c)).links(linkToCategory).build();
    }
}
