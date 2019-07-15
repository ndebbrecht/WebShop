/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.webshop.util;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author niklas_debbrecht
 */
public abstract class RepoForEntityWithLongId<T extends Serializable> implements Serializable {
    @PersistenceContext(unitName="WebShopPU")
    protected EntityManager em;
    protected Class<?> type;
    
    public void persist(T entity){
        em.persist(entity);
        em.flush();
    }
    
    public T findById(Long id){
        return (T)em.find(this.type, id);
    }
    
    public void remove(T entity){
        em.remove(entity);
    }
    
    public T merge(T entity){
        return em.merge(entity);
    }
}
