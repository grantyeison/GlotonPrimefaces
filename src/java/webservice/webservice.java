/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import Bean.RestauranteFacade;
import Modelo.Restaurante;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Daniel
 */
@Path("webservice")
public class webservice {

    @Context
    private UriInfo context;
    @EJB
    private RestauranteFacade ejRestauranteFacade;

    /**
     * Creates a new instance of webservice
     */
    public webservice() {
    }

    /**
     * Retrieves representation of an instance of webservice.webservice
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        List<Restaurante> listaRestaurante= ejRestauranteFacade.findAll();
        try {
        for(Restaurante r : listaRestaurante)
        {
            JSONObject j = new JSONObject();
            
                j.put("nombre",r.getResNombre());
                jSONArray.put(j);
           
            
        }
        jSONObject.put("restaurante", jSONArray);
         } catch (JSONException ex) {
                Logger.getLogger(webservice.class.getName()).log(Level.SEVERE, null, ex);
           }
        
        return jSONObject.toString();
    }

    /**
     * PUT method for updating or creating an instance of webservice
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
