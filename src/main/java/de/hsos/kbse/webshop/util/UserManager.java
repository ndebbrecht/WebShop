/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.webshop.util;

import de.hsos.kbse.webshop.entities.Cart;
import de.hsos.kbse.webshop.entities.Customer;
import de.hsos.kbse.webshop.repositories.CustomerRepository;
import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author niklas_debbrecht
 */
@ApplicationScoped
public class UserManager {
    @Inject
    private CustomerRepository customerRepo;
    
    public boolean isAdmin(String email, String password){
        try{
            Customer c = this.getValidCustomer(email, password);
            return c.isIsAdmin();
        } catch(NullPointerException e){
            return false;
        }
    }
    
    public Customer getValidCustomer(String email, String password){
        if(isUser(email, password)){
            return findCustomer(email);
        } else {
            return null;
        }
    }
    
    public boolean isUser(String email, String password){
        try {
            // returns true if entered email and password are correct otherwise false
            return (findCustomer(email).getPassword().equals(password));
        } catch (NullPointerException e){
            // catches if user doesnt exists or input is null
            return false;
        }
    }
    
    private Customer findCustomer(String email){
        if(email == null){
            return null;
        }
        Collection<Customer> customers = customerRepo.findAll();
        for(Customer c: customers){
            if(c.getEmail().equals(email)){
                return c;
            }
        }
        return null;
    }
    
    /*
    Checks if the chosen cart belongs to the entered credentials
    */
    public boolean isYourCart(String email, String password, Long cartId){
        Customer c = this.getValidCustomer(email, password);
        if(c != null){
            Collection<Cart> carts = c.getCarts();
            for(Cart cart: carts){
                if(cart.getId() == cartId){
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
