package DAO;

import Modelo.Calificacion;
import DAO.util.JsfUtil;
import DAO.util.PaginationHelper;
import Bean.CalificacionFacade;
import Modelo.PlatoRestaurante;
import Modelo.Restaurante;
import Modelo.Usuario;
import Modelo.ResultadoCalificacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.PieChartModel;

@Named("calificacionController")
@SessionScoped
public class CalificacionController implements Serializable {

    private Calificacion current;
    private DataModel items = null;
    @EJB
    private Bean.CalificacionFacade ejbFacade;
    @EJB
    private Bean.UsuarioFacade usuarioEjb;
    @EJB
    private Bean.RestauranteFacade ejbRestauranteFacade;
    
    private PaginationHelper pagination;
    private int selectedItemIndex;
    List<Calificacion> calificaciones=new ArrayList<Calificacion>();
    List<ResultadoCalificacion> r_calificaciones=new ArrayList<ResultadoCalificacion>();
    List<PieChartModel> tortas=new ArrayList<PieChartModel>();
    private PieChartModel pieModel1 = new PieChartModel();

    public CalificacionController() {
    }

    public Calificacion getSelected() {
        if (current == null) {
            current = new Calificacion();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CalificacionFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    //return getFacade().count();
                    return getItems2().getRowCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Calificacion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Calificacion();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CalificacionCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Calificacion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CalificacionUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Calificacion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CalificacionDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() 
    {
        if (items == null) 
        {
            items = getPagination().createPageDataModel();
        }
        else
        {
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
            
            if (req.getUserPrincipal() != null) 
            {
                String username = req.getUserPrincipal().getName();
                Usuario usuario = usuarioEjb.findebyUserName(username).get(0);//obtengo el usuario completo
                List<Restaurante> listRestaurante = ejbRestauranteFacade.findByUserName(usuario);
                Restaurante restaurante = listRestaurante.get(0);
                items.setWrappedData(ejbFacade.findByRestauranteId(restaurante.getResId()));//obtengo la calificacion del plato del restaurante del usuario loguiniao y lo meto en items
                calificaciones=ejbFacade.findByRestauranteId(restaurante.getResId());
                r_calificaciones=prepararCalificaciones(calificaciones);
            }
        }
        return items;
    }
    public DataModel getItems2() 
    {
        if (items == null) 
        {
            items = getPagination().createPageDataModel();
        }
        return items;
    }
    public List<ResultadoCalificacion> prepararCalificaciones(List<Calificacion> lc)
    {
        List<ResultadoCalificacion> resultado=new ArrayList<>();
        int contadorPlato=0;
        float promedioCalificacion=0, sumatoriaCalificacion=0;
        ResultadoCalificacion c=new ResultadoCalificacion();

        for(int i=0; i<lc.size(); i++)
        {
            if(contadorPlato==0)
            {
                if(promedioCalificacion!=0)
                {
                    i--;
                }
                c.setCalificacion(lc.get(i));
                sumatoriaCalificacion+=c.getCalificacion().getCalPuntuacion();
                contadorPlato++;
            }
            else
            {
                if(c.getCalificacion().getTblplatorestauranteplatId().getTblplatoplaId().getPlaNombre().equals(lc.get(i).getTblplatorestauranteplatId().getTblplatoplaId().getPlaNombre()))//si es calificacion del mismo plato
                {
                    sumatoriaCalificacion+=lc.get(i).getCalPuntuacion();
                    contadorPlato++;
                    if(i+1==lc.size())
                    {
                        promedioCalificacion=sumatoriaCalificacion/contadorPlato;
                        c=new ResultadoCalificacion();
                        c.setCalificacion(lc.get(i));
                        c.setPromedioCalificacion(promedioCalificacion);
                        c.setTotalCalificaciones(contadorPlato);
                        resultado.add(c);
                    }
                }
                else
                {
                    promedioCalificacion=sumatoriaCalificacion/contadorPlato;
                    ResultadoCalificacion c2=new ResultadoCalificacion();
                    c2.setCalificacion(lc.get(i-1));
                    c2.setPromedioCalificacion(promedioCalificacion);
                    c2.setTotalCalificaciones(contadorPlato);
                    resultado.add(c2);
                    contadorPlato=0;
                    sumatoriaCalificacion=0;
                }
            }
        }
        return resultado;
    }
    public List<Calificacion> getCalificaciones()
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
            
            if (req.getUserPrincipal() != null) 
            {
                String username = req.getUserPrincipal().getName();
                Usuario usuario = usuarioEjb.findebyUserName(username).get(0);//obtengo el usuario completo
                calificaciones=ejbFacade.findByRestauranteId(usuario.getRestauranteList().get(0).getResId());
            }
        //calificaciones=prepararCalificaciones(calificaciones);
        return calificaciones;
    }

    public List<ResultadoCalificacion> getR_calificaciones() 
    {
        return r_calificaciones;
    }
    
    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Calificacion getCalificacion(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    private void createPieModel1(Calificacion c) 
    {
        int i=0, cont_1=0, cont_2=0, cont_3=0, cont_4=0, cont_5=0;
        pieModel1=new PieChartModel();
        for(i=0; i<calificaciones.size(); i++)
        {
            if(c.getTblplatorestauranteplatId().getTblplatoplaId().getPlaNombre().equals(calificaciones.get(i).getTblplatorestauranteplatId().getTblplatoplaId().getPlaNombre()))//si los nombres de los platos son iguales
            {
                switch(calificaciones.get(i).getCalPuntuacion())
                {
                    case 1:
                        cont_1++;
                        break;

                    case 2:
                        cont_2++;
                        break;

                    case 3:
                        cont_3++;
                        break;

                    case 4:
                        cont_4++;
                        break;

                    case 5:
                        cont_5++;
                        break;
                }
            }
            
        }
        if(cont_1!=0)
        {
            pieModel1.set("1 estrella", cont_1);
        }
        if(cont_2!=0)
        {
            pieModel1.set("2 estrellas", cont_2);
        }
        if(cont_3!=0)
        {
            pieModel1.set("3 estrellas", cont_3);
        }
        if(cont_4!=0)
        {
            pieModel1.set("4 estrellas", cont_4);
        }
        if(cont_5!=0)
        {
            pieModel1.set("5 estrellas", cont_5);
        }
         
        pieModel1.setTitle(c.getTblplatorestauranteplatId().getTblplatoplaId().getPlaNombre());
        pieModel1.setLegendPosition("w");
        pieModel1.setShowDataLabels(true);
    }

    public PieChartModel getPieModel1(Calificacion c) 
    {
        createPieModel1(c);
        return pieModel1;
    }

    public List<PieChartModel> getTortas() 
    {
        tortas=new ArrayList<PieChartModel>();
        for(int i=0; i<r_calificaciones.size(); i++)
        {
            Calificacion c=new Calificacion();
            c=r_calificaciones.get(i).getCalificacion();
            tortas.add(getPieModel1(c));
        }
        
        return tortas;
    }
    @FacesConverter(forClass = Calificacion.class)
    public static class CalificacionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CalificacionController controller = (CalificacionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "calificacionController");
            return controller.getCalificacion(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Calificacion) {
                Calificacion o = (Calificacion) object;
                return getStringKey(o.getCalId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Calificacion.class.getName());
            }
        }

    }
     public String obtenerNomPlatCal(PlatoRestaurante platoRestaurante)
    {
        return platoRestaurante.getTblplatoplaId().getPlaNombre();
    }
}
