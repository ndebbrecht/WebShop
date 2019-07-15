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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author niklas_debbrecht
 */
@RequestScoped
@Path("products")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductResource implements Serializable {
    @Inject
    private ProductRepository productRepo;
    @Inject
    private CategoryRepository categoryRepo;
    @Inject
    private Jsonb jsonb;// = JsonbBuilder.create();
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
    @Path("category/{id}")
    public Response getCategoryProducts(@PathParam("id")Long id){
        try {
            return Response.ok(jsonb.toJson(categoryRepo.findById(id).getProducts())).build();
        } catch (NullPointerException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("find/{term}")
    public Response findProducts(@PathParam("term")String term){
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
        return Response.ok(jsonb.toJson(pResult)).build();
    }
    
    @POST
    @Path("new/{name}/{price}/{description}/{categoryId}")
    public Response addProduct(
            @PathParam("name")String name,
            @PathParam("price")double price,
            @PathParam("description")String description,
            @PathParam("categoryId")Long categoryId
            ){
        System.out.println(name+price+description+categoryId);
        Product p = new Product();
        p.setName(name);
        p.setPrice(price);
        p.setDescription(description);
        System.out.println(jsonb.toJson(p));
        p.setCategory(categoryRepo.findById(categoryId));
        productRepo.persist(p);
        return Response.ok(jsonb.toJson(p)).build();
    }
    
    @POST
    @Path("newCategory/{name}")
    public Response addCategory(@PathParam("name")String name){
        Category c = new Category();
        c.setName(name);
        categoryRepo.persist(c);
        return Response.ok(jsonb.toJson(c)).build();
    }
}
