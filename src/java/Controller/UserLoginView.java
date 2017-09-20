/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modelo.Usuario;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
 
import org.primefaces.context.RequestContext;
/**
 *
 * @author Apollo
 */

@ManagedBean
public class UserLoginView {
     
    private String username;
     
    private String password;
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
   
    public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;
        
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("GlotonAplicationPU");
        
        em = emf.createEntityManager();
        List<Usuario> lst = null;
        Query q = em.createQuery("SELECT a FROM Usuario a");
        lst = q.getResultList();
        
        for (Usuario usu : lst)
        {
            if(username != null && username.equals(usu.getDueUsuario()) && password != null && password.equals(usu.getDuePass())) {
                loggedIn = true;
            } else {
                loggedIn = false;
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
            }
        }
        
        if (loggedIn)
        {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
        }
        else
        {
            
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        }
         
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
    }   
}
