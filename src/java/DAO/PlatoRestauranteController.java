package DAO;

import Modelo.PlatoRestaurante;
import DAO.util.JsfUtil;
import DAO.util.PaginationHelper;
import Bean.PlatoRestauranteFacade;
import Modelo.Plato;
import Modelo.Restaurante;
import Modelo.Usuario;

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

@Named("platoRestauranteController")
@SessionScoped
public class PlatoRestauranteController implements Serializable 
{

    private PlatoRestaurante current;//el PlatoRestaurante actual
    private DataModel items = null;//el listado de registros de PlatoRestaurante
    
    //Zona de Enterprise Java Bean que corresponden a entidades propias o asociadas
    @EJB
    private Bean.PlatoRestauranteFacade ejbFacade;
    @EJB
    private Bean.RestauranteFacade ejbRestauranteFacade;
    @EJB
    private Bean.UsuarioFacade usuarioEjb;
    // fin de la zona de EJB
    private List<String> estado = new ArrayList<String>();

    private PaginationHelper pagination;
    private int selectedItemIndex;
    ArrayList datos=new ArrayList();

    public PlatoRestauranteController() //método constructor del controlador de PlatoRestaurante
    {
        estado.add("Activo");
        estado.add("Inactivo");
    }

    public PlatoRestaurante getSelected() 
    {
        if (current == null) 
        {
            current = new PlatoRestaurante();
            selectedItemIndex = -1;
        }
        return current;
    }

    public List<String> getEstado() {
        return estado;
    }
    
    private PlatoRestauranteFacade getFacade() //retorna el session bean
    {
        return ejbFacade;
    }

    public PaginationHelper getPagination() 
    {
        if (pagination == null) 
        {
            pagination = new PaginationHelper(10) 
            {

                @Override
                public int getItemsCount() 
                {
                    return getItems2().getRowCount();
                }

                @Override
                public DataModel createPageDataModel() 
                {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() //preparación para listar todos los PlatosRestaurante, invoca List.xhtml
    {
        recreateModel();
        return "List";
    }

    public String prepareView() //preparación para ver los datos de un PlatoRestaurante, invoca View.xhtml
    {
        current = (PlatoRestaurante) getItems2().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems2().getRowIndex();
        return "View";
    }

    public String prepareCreate() //preparación para crear un nuevo PlatoRestaurante, invoca Create.xhtml
    {
        current = new PlatoRestaurante();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() //este método se usa para crear un nuevo PlatoRestaurante en Create.xhtml
    {
        try 
        {
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();   
            if (req.getUserPrincipal() != null) 
            {
                
            
                String username = req.getUserPrincipal().getName();
                Usuario usuario = usuarioEjb.findebyUserName(username).get(0);
                Restaurante restaurante = ejbRestauranteFacade.findByUserName(usuario).get(0);
                current.setTblRestauranteResId(restaurante);
                
            }
            
            
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PlatoRestauranteCreated"));
            return prepareCreate();
        } catch (Exception e) 
        {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() //preparación para editar un item, invoca a Edit.xhtml
    {
        current = (PlatoRestaurante) getItems2().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems2().getRowIndex();
        return "Edit";
    }

    public String update() //método para actualizar un registro de PlatoRestaurante, se usa en Edit.xhtml
    {
        try 
        {   
            HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String txtProperty = request.getParameter("formEditPlaRes:platEstado");
            current.setPlatEstado(txtProperty);
            
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PlatoRestauranteUpdated"));
            return "List";
        } catch (Exception e) 
        {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() //método para eliminar un registro de PlatoRestaurante, se usa en List.xhtml
    {
        current = (PlatoRestaurante) getItems2().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems2().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() 
    {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) 
        {
            return "View";
        } 
        else 
        {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() //este método es el que ejecuta la lógica para eliminar un registro de PlatoRestaurante
    {
        try 
        {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PlatoRestauranteDeleted"));//mensaje de éxito en la eliminación
        } catch (Exception e) 
        {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));//mensaje de error en la eliminación
        }
    }

    private void updateCurrentItem() //método que realiza propiamente la edición de un registro de PlatoRestaurante
    {
        int count = getFacade().count();//se obtiene la cantidad de registros de la tabla PlatoRestaurante
        if (selectedItemIndex >= count) //si el índice seleccionado es mayor a la cantidad de registros de PlatoRestaurante
        {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) //si hay por lo menos un registro
            {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) 
        {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems()//versión modificada para la consulta
    {
        if (items == null) //si no lo está, simplemente lo retorna y ya
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
                items.setWrappedData(ejbFacade.findByRestauranteId(usuario.getRestauranteList().get(0).getResId()));//obtengo el restaurante del usuario loguiniao y lo meto en items
            }
        }
        return items;
    }
public DataModel getItems2()//el método como se genera por defecto
    {
        if (items == null) //si no lo está, simplemente lo retorna y ya
        {
            items = getPagination().createPageDataModel();
        }
        return items;
    }
    private void recreateModel() //reinicia los datos
    {
        items = null;
    }

    private void recreatePagination() //recrea la paginación
    {
        pagination = null;
    }

    public String next() //avanza en la siguiente página de items
    {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() //regresa a la anterior página de items
    {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() //obtiene todos los items
    {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() //obtiene un item
    {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public PlatoRestaurante getPlatoRestaurante(java.lang.Integer id) //obtener un PlatoRestaurante por id
    {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = PlatoRestaurante.class)
    public static class PlatoRestauranteControllerConverter implements Converter 
    {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) 
        {
            if (value == null || value.length() == 0) 
            {
                return null;
            }
            PlatoRestauranteController controller = (PlatoRestauranteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "platoRestauranteController");
            return controller.getPlatoRestaurante(getKey(value));
        }

        java.lang.Integer getKey(String value) 
        {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) 
        {
            if (object == null) 
            {
                return null;
            }
            if (object instanceof PlatoRestaurante) 
            {
                PlatoRestaurante o = (PlatoRestaurante) object;
                return getStringKey(o.getPlatId());
            } 
            else 
            {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + PlatoRestaurante.class.getName());
            }
        }

    }
    
    public String obtenerNomPlatoRes(Plato plato)
    {
        return plato.getPlaNombre();
    }

}
