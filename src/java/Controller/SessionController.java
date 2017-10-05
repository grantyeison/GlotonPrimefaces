/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Bean.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author aranda
 */

@Named("sessionController")
@SessionScoped
public class SessionController implements Serializable
{
    private String nombreUsuario;
    private String contrasena;
    @EJB
    private UsuarioFacade usuarioEjb;
    
    
    
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String constrasena) {
        this.contrasena = constrasena;
    }
    
    public void login()throws IOException, ServletException 
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();        
        if (req.getUserPrincipal() == null) {
            try 
            {
                req.login(this.nombreUsuario, this.contrasena);
                req.getServletContext().log("Autenticacion exitosa");
                //haySesion = true;
                String username = req.getUserPrincipal().getName();
                
                if(usuarioEjb.findebyUserName(username).get(0).getUsuarioGrupoList().get(0).getUsugrupGrupId().getGrupId().equals("admin"))
                {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("admin/principaladmin.xhtml");

                }
                else
                {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("usuario/paginaUsuario.xhtml");
                }
                
                
            } 
            catch (ServletException e) 
            {
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre de usuario o contraseña incorrectos", "Nombre de usuario o contraseña incorrectos"));
                requestContext.update("formLogin");                
            }
        } 
        else 
        {
            req.getServletContext().log("El usuario ya estaba logueado:  ");
            FacesContext.getCurrentInstance().getExternalContext().redirect("/GlotonPrimefaces/admin/principaladmin.xhtml");
        }
    }
    
    public void logout() throws IOException, ServletException 
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        try {
            req.logout();            
            req.getSession().invalidate();
            fc.getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/GlotonPrimefaces");

        } catch (ServletException e) {            
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILED", "Logout failed on backend"));            
        }
        
    }
}
