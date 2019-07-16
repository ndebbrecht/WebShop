/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.webshop.rest;

import de.hsos.kbse.webshop.entities.Cart;
import de.hsos.kbse.webshop.entities.Customer;
import de.hsos.kbse.webshop.entities.OrderItem;
import de.hsos.kbse.webshop.entities.Product;
import de.hsos.kbse.webshop.repositories.CartRepository;
import de.hsos.kbse.webshop.repositories.CustomerRepository;
import de.hsos.kbse.webshop.repositories.OrderItemRepository;
import de.hsos.kbse.webshop.repositories.ProductRepository;
import de.hsos.kbse.webshop.util.UserManager;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;

/**
 *
 * @author niklas_debbrecht
 */
@RequestScoped
@Path("carts")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class CartResource implements Serializable {
    @Inject
    private CartRepository cartRepo;
    @Inject
    private ProductRepository productRepo;
    @Inject
    private OrderItemRepository orderItemRepo;
    @Inject
    private CustomerRepository customerRepo;
    @Inject
    private UserManager userManager;
    @Inject
    private Jsonb jsonb;
    @Context
    UriInfo uriInfo;
    
    @GET
    @Path("{id}")
    public Response getCart(@PathParam("id")Long id, @QueryParam("email")String email, @QueryParam("password")String password){
        if(!userManager.isYourCart(email, password, id)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        try {
            Cart cart = cartRepo.findById(id);
        
            return Response.ok(jsonb.toJson(cart)).build();
        } catch (NullPointerException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @GET
    public Response getAllCarts(@QueryParam("email")String email, @QueryParam("password")String password){
        if(userManager.isAdmin(email, password)){
            return Response.ok(jsonb.toJson(cartRepo.findAll())).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
    
    @POST
    @Path("{id}/add")
    public Response addProduct(@PathParam("id")Long cartId, @QueryParam("productId")Long productId, @QueryParam("amount")int amount, @FormParam("email")String email, @FormParam("password")String password){
        if(!userManager.isYourCart(email, password, cartId)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        try {
            Product p = productRepo.findById(productId);
            OrderItem oi = new OrderItem();
            oi.setProduct(p);
            oi.setAmount(amount);
            orderItemRepo.persist(oi);
            Cart c = cartRepo.findById(cartId);
            c.addOrderItem(oi);
            cartRepo.merge(c);
            return Response.ok(jsonb.toJson(c)).build();
        } catch (NullPointerException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @POST
    @Path("new")
    public Response newCart(@QueryParam("email")String email, @QueryParam("password")String password){
        try {
            Customer c = userManager.getValidCustomer(email, password);
            Cart cart = new Cart();
            cart.setCustomer(c);
            cart.setStatus(1);
            cartRepo.persist(cart);
        
            return Response.ok(jsonb.toJson(cart)).build();
        } catch (NullPointerException e){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
    
    @PUT
    @Path("{id}/editAmount")
    public Response editAmount(@PathParam("id")Long cartId, @QueryParam("orderItemId")Long orderItemId, @QueryParam("newAmount")int newAmount, @FormParam("email")String email, @FormParam("password")String password){
        if(!userManager.isYourCart(email, password, cartId)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        try {
            OrderItem oi = orderItemRepo.findById(orderItemId);
            oi.setAmount(newAmount);
            orderItemRepo.merge(oi);
            return Response.ok(jsonb.toJson(oi)).build();
        } catch (NullPointerException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @DELETE
    @Path("{id}/removeFromCart")
    public Response removeOrderItem(@PathParam("id")Long cartId, @QueryParam("orderItemId")Long orderItemId, @QueryParam("email")String email, @QueryParam("password")String password){
        if(!userManager.isYourCart(email, password, cartId)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        try {
            Cart cart = cartRepo.findById(cartId);
            OrderItem oi = orderItemRepo.findById(orderItemId);
            if(oi == null || !cart.getOrderItems().contains(oi)){
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            cart.getOrderItems().remove(oi);
            cartRepo.merge(cart);
            return Response.ok(jsonb.toJson(cartRepo.findById(cartId))).build();
        } catch (NullPointerException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
