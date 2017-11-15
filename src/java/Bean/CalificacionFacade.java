/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Modelo.Calificacion;
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
public class CalificacionFacade extends AbstractFacade<Calificacion> {

    @PersistenceContext(unitName = "GlotonAplicationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CalificacionFacade() {
        super(Calificacion.class);
    }
    public List<Calificacion> findByRestauranteId(int resId)//esto invoca la namedQuery y devuelve los restaurantes que tienen el id parámetro
    {
        Query query = getEntityManager().createNamedQuery("Calificacion.findByRestaurante");
        query.setParameter("resId", resId);
        return query.getResultList();
    }
}
