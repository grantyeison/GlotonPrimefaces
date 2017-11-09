/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import Bean.CategoriaFacade;
import Bean.PlatoFacade;
import Bean.PlatoRestauranteFacade;
import Bean.RestauranteFacade;
import Bean.CalificacionFacade;
import Modelo.Calificacion;
import Modelo.Categoria;
import Modelo.Plato;
import Modelo.PlatoRestaurante;
import Modelo.Restaurante;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    @EJB
    private PlatoFacade ejPlatoFacade;
    @EJB
    private CategoriaFacade ejCategFacade;
    @EJB
    private PlatoRestauranteFacade ejPlatoRestFacade;
    @EJB
    private CalificacionFacade ejCalificacionFacade;
    

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
        JSONArray jSONArray;
        List<Restaurante> listaRestaurante= ejRestauranteFacade.findAll();
        List<Plato> listaPlato = ejPlatoFacade.findAll();
        List<Categoria> listaCateg = ejCategFacade.findAll();
        List<PlatoRestaurante> listaPlatoRest = ejPlatoRestFacade.findAll();
        List<Calificacion> listaCalificaciones = ejCalificacionFacade.findAll();
        
        try {
            //llenar lista JSON con restaurantes
            jSONArray = new JSONArray();
            for(Restaurante r : listaRestaurante)
            {
                JSONObject j = new JSONObject();
                if (r.getResEstado().equals("Activo"))
                {
                    j.put("Nit",r.getResNit());
                    j.put("nombre",r.getResNombre());
                    j.put("direccion",r.getResDireccion());
                    j.put("telefono",r.getResTelefono());
                    j.put("logo",r.getResLogo());
                    j.put("latitud",r.getResLatitud());
                    j.put("longitud",r.getTesLongitud());
                    jSONArray.put(j);
                }
            }
            jSONObject.put("restaurante", jSONArray);
            
            
            //llenar lista JSON con Platos
            jSONArray = new JSONArray();
            for(Plato r : listaPlato)
            {
                JSONObject j = new JSONObject();
                if (r.getPlaEstado().equals("Activo"))
                {
                    j.put("nombre",r.getPlaNombre());
                    j.put("imagen",r.getPlaImagen());
                    j.put("categoria",r.getTblcategoriacatId().getCatNombre());
                    jSONArray.put(j);
                }
            }
            jSONObject.put("plato", jSONArray);
            
            
            //llenar lista JSON con Categorías
            jSONArray = new JSONArray();
            for(Categoria r : listaCateg)
            {
                JSONObject j = new JSONObject();
                if (r.getCatEstado().equals("Activo"))
                {
                    j.put("nombre",r.getCatNombre());
                    j.put("imagen",r.getCatImagen());
                    jSONArray.put(j);
                }
            }
            jSONObject.put("categoria", jSONArray);
            
            
            //llenar lista JSON con PlatoRestaurante
            jSONArray = new JSONArray();
            for(PlatoRestaurante r : listaPlatoRest)
            {
                JSONObject j = new JSONObject();
                if (r.getPlatEstado().equals("Activo"))
                {
                    j.put("id",r.getPlatId());
                    j.put("plato",r.getTblplatoplaId().getPlaNombre());
                    j.put("ingredientes",r.getPlatIngredientes());
                    j.put("descripcion",r.getPlatDescripcion());
                    j.put("restaurante",r.getTblRestauranteResId().getResNit());//se maneja el nit dado que dos restaurantes pueden tener el mismo nombre
                    j.put("precio",r.getPlatPrecio());
                    jSONArray.put(j);
                }
            }
            jSONObject.put("caracteristicasPlato", jSONArray);
            
            //llenar lista JSON con calificaciones
            jSONArray = new JSONArray();
            //hacer una por cada plato y devolver los mejor calificados solamente...
            List<Calificacion> calificacionesEnviar = new ArrayList<>();
 /*           
            for (PlatoRestaurante plaRes : listaPlatoRest)
            {
                int suma = 0;
                int total = 0;
                for (Calificacion cali: listaCalificaciones)
                {
                    if (cali.getTblplatorestauranteplatId().getPlatId() == plaRes.getPlatId())
                    {
                        suma += cali.getCalPuntuacion();
                        total ++;
                    }
                }
                if (total > 0)
                {
                    int promedio = suma / total;
                    JSONObject j = new JSONObject();
                    j.put("caracteristica",plaRes.getPlatId());
                    j.put("puntuacion",promedio);
                    jSONArray.put(j);
                }
            }
 */           
            
            for (PlatoRestaurante plar: listaPlatoRest)
            {
                int suma = 0;
                int conteo = 0;
                for(Calificacion r : listaCalificaciones)
                {
                    if (plar.getPlatId() == r.getTblplatorestauranteplatId().getPlatId())
                    {
                        suma = suma+r.getCalPuntuacion();
                        conteo++;
                    }
                }
                if (conteo >0)
                {
                    int promedio = suma/conteo;
                    if (promedio >3)
                    {
                        JSONObject j = new JSONObject();
                        j.put("puntuacion",suma/conteo);
                        j.put("caracteristica",plar.getPlatId());
                        jSONArray.put(j);
                    }
                }
            }
            
            jSONObject.put("recomendados", jSONArray);
            
         } catch (JSONException ex) {
                Logger.getLogger(webservice.class.getName()).log(Level.SEVERE, null, ex);
           }
        
        return jSONObject.toString();
    }

    /**
     * PUT method for updating or creating an instance of webservice
     * @param content representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postJson(String content) throws JSONException {
        JSONObject jsonObject = new JSONObject(content);
        JSONArray calificaciones = jsonObject.getJSONArray("calificaciones");
        for (int i = 0 ; i < calificaciones.length(); i++)
        {
            JSONObject calificacion = calificaciones.getJSONObject(i);
            Calificacion cal = new Calificacion();
            PlatoRestaurante plato = ejPlatoRestFacade.buscar(Integer.parseInt(calificacion.get("plato").toString()));
            cal.setTblplatorestauranteplatId(plato);
            System.out.println("plato"+plato.getPlatDescripcion());
            cal.setCalPuntuacion(Integer.parseInt(calificacion.get("calificacion").toString()));
            cal.setCalUsuario(calificacion.get("usuario").toString());
            List<Calificacion> cals = ejCalificacionFacade.findAll();
            int bandera = 0;
            for (Calificacion cali : cals)
            {
                if (cali.getCalUsuario().equals(cal.getCalUsuario()))
                {
                    if (cali.getTblplatorestauranteplatId().getPlatId() == cal.getTblplatorestauranteplatId().getPlatId())
                        bandera = 1;
                }
            }
            if (bandera == 0)
                ejCalificacionFacade.create(cal);
            else
                ejCalificacionFacade.edit(cal);
        }
    }
}
