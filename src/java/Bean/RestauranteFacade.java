/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Modelo.Restaurante;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Apollo
 */
@Stateless
public class RestauranteFacade extends AbstractFacade<Restaurante> {

    @PersistenceContext(unitName = "GlotonAplicationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RestauranteFacade() {
        super(Restaurante.class);
    }
    
}
