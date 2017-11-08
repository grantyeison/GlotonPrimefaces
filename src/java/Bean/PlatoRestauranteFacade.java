/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Modelo.PlatoRestaurante;
import Modelo.Restaurante;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author aranda
 */
@Stateless
public class PlatoRestauranteFacade extends AbstractFacade<PlatoRestaurante> {

    @PersistenceContext(unitName = "GlotonAplicationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlatoRestauranteFacade() {
        super(PlatoRestaurante.class);
    }
    public List<PlatoRestaurante> findByRestauranteId(int resId)//esto invoca la namedQuery y devuelve los restaurantes que tienen el id parámetro
    {
        Query query = getEntityManager().createNamedQuery("PlatoRestaurante.findByRestaurante");
        query.setParameter("resId", resId);
        return query.getResultList();
    }
    
    public PlatoRestaurante buscar(int id)
    {
        Query query = getEntityManager().createNamedQuery("PlatoRestaurante.findByPlatId");
        query.setParameter("platId", id);
        return (PlatoRestaurante) query.getResultList().get(0);
        
    }
}
