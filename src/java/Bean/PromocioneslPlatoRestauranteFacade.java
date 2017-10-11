/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Modelo.PromocioneslPlatoRestaurante;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aranda
 */
@Stateless
public class PromocioneslPlatoRestauranteFacade extends AbstractFacade<PromocioneslPlatoRestaurante> {

    @PersistenceContext(unitName = "GlotonAplicationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PromocioneslPlatoRestauranteFacade() {
        super(PromocioneslPlatoRestaurante.class);
    }
    
}
