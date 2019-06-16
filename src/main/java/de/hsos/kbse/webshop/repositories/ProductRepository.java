/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.webshop.repositories;

import de.hsos.kbse.webshop.entities.Product;
import de.hsos.kbse.webshop.util.RepoForEntityWithLongId;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author niklas_debbrecht
 */
@Stateless
public class ProductRepository extends RepoForEntityWithLongId<Product> {
    public ProductRepository(){
        this.type = Product.class;
    }
    
    public Collection<Product> findAll() {
        Query query = em.createQuery("SELECT e FROM Product e");
        return (Collection<Product>) query.getResultList();
    }
}

