/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.webshop.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.Jsonb;

/**
 *
 * @author niklas_debbrecht
 */
@ApplicationScoped
public class JsonbFactory {
    @Produces
    public Jsonb createJsonb(){
        return JsonbBuilder.create();
    }
}
