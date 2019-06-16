/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.webshop.repositories;

import de.hsos.kbse.webshop.entities.Customer;
import de.hsos.kbse.webshop.util.RepoForEntityWithLongId;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author niklas_debbrecht
 */
@Stateless
public class CustomerRepository extends RepoForEntityWithLongId<Customer> {
    public CustomerRepository(){
        this.type = Customer.class;
    }
    
    public Collection<Customer> findAll() {
        Query query = em.createQuery("SELECT e FROM Customer e");
        return (Collection<Customer>) query.getResultList();
    }
}
