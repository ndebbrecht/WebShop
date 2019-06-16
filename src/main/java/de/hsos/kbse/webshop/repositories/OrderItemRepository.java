/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.webshop.repositories;

import de.hsos.kbse.webshop.entities.OrderItem;
import de.hsos.kbse.webshop.util.RepoForEntityWithLongId;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;

/**
 *
 * @author niklas_debbrecht
 */
@Stateless
public class OrderItemRepository extends RepoForEntityWithLongId<OrderItem> {
    public OrderItemRepository(){
        this.type = OrderItem.class;
    }
    
    public Collection<OrderItem> findAll() {
        Query query = em.createQuery("SELECT e FROM OrderItem e");
        return (Collection<OrderItem>) query.getResultList();
    }
}
