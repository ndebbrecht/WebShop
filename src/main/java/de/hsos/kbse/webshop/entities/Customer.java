/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.webshop.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author niklas_debbrecht
 */
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @JsonbProperty("firstname")
    private String firstname;
    @JsonbProperty("lastname")
    private String lastname;
    @JsonbProperty("email")
    private String email;
    @JsonbProperty("password")
    private String password;
    @JsonbProperty("street")
    private String street;
    @JsonbProperty("postalCode")
    private String postalCode;
    @JsonbProperty("city")
    private String city;
    @JsonbProperty("country")
    private String country;
    @JsonbProperty("isAdmin")
    private boolean isAdmin;
    @OneToMany
    @JsonbTransient
    private Collection<Cart> carts;

    public Customer(){
    }
    
    public Customer(
            String firstname, 
            String lastname, 
            String email, 
            String password, 
            String street, 
            String postalCode, 
            String city, 
            String country, 
            boolean isAdmin
    ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.isAdmin = isAdmin;
    }
    
    public Collection<Cart> getCarts(){
        return this.carts;
    }
    
    public Collection<Cart> addCart(Cart cart){
        this.carts.add(cart);
        return this.carts;
    }
    
    public Collection<Cart> removeCart(Cart cart){
        this.carts.remove(cart);
        return this.carts;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hsos.kbse.webshop.entities.Customer[ id=" + id + " ]";
    }
    
}
