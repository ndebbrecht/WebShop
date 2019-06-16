/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.webshop.repositories;

import de.hsos.kbse.webshop.entities.Category;
import de.hsos.kbse.webshop.util.RepoForEntityWithLongId;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author niklas_debbrecht
 */
@Stateless
public class CategoryRepository extends RepoForEntityWithLongId<Category> {
    public CategoryRepository(){
        this.type = Category.class;
    }
    
    public Collection<Category> findAll() {
        Query query = em.createQuery("SELECT e FROM Category e");
        return (Collection<Category>) query.getResultList();
    }
}

