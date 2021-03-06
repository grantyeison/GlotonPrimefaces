/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Modelo.UsuarioGrupo;
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
public class UsuarioGrupoFacade extends AbstractFacade<UsuarioGrupo> {

    @PersistenceContext(unitName = "GlotonAplicationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioGrupoFacade() {
        super(UsuarioGrupo.class);
    }
    
    public List<UsuarioGrupo> findByIdUsuario(Usuario dueid)
    {
        Query query = getEntityManager().createNamedQuery("UsuarioGrupo.findByIdUsuario");
        query.setParameter("due_id", dueid);
        return query.getResultList();
    }
    
    public List<UsuarioGrupo> findByUsuario(String usugrupDueUsuario)
    {
        Query query = getEntityManager().createNamedQuery("UsuarioGrupo.findRolByUserName");
        query.setParameter("usugrupDueUsuario", usugrupDueUsuario);
        return query.getResultList();
    }
    
    public String getRolUsuario(String usugrupDueUsuario)
    {
        List<UsuarioGrupo> listaUsuarioGrupo;
        Query query = getEntityManager().createNamedQuery("UsuarioGrupo.findRolByUserName");
        query.setParameter("usugrupDueUsuario", usugrupDueUsuario);
        listaUsuarioGrupo=query.getResultList();
        if(listaUsuarioGrupo != null && listaUsuarioGrupo.size()>0)
        {
            return listaUsuarioGrupo.get(0).getUsugrupGrupId().getGrupId();
        }
        else
        {
            return "indefinido";
        }
    }
        
    
}
