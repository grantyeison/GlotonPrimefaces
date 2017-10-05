/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Modelo.Usuario;
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
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "GlotonAplicationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public List<Usuario> findebyUserName(String username)
    {
         Query query = getEntityManager().createNamedQuery("Usuario.findByDueUsuario");
        query.setParameter("dueUsuario", username);
        return query.getResultList();
    }
    ///// forma con consulta base de datos
    public List<Usuario> findbyRolCliente(String rol)
    {
        Query query = getEntityManager().createNamedQuery("Usuario.findByDueRolCliente");
        query.setParameter("usugrupDueId", rol);
        return query.getResultList();
    }
    
}
