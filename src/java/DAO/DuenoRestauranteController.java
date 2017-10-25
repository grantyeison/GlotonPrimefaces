package DAO;

import Modelo.Restaurante;
import DAO.util.JsfUtil;
import DAO.util.PaginationHelper;
import Bean.RestauranteFacade;
import Modelo.Usuario;

import java.io.Serializable;
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

@Named("duenoRestauranteController")
@SessionScoped
public class DuenoRestauranteController implements Serializable 
{

    private Restaurante current;
    Usuario usuario=new Usuario();
    private DataModel items = null;
    
    @EJB
    private Bean.RestauranteFacade ejbFacade;
    @EJB
    private Bean.UsuarioFacade usuarioEjb;
    
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public DuenoRestauranteController() 
    {
    }

    public Restaurante getSelected() 
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        if (req.getUserPrincipal() != null) 
        {        
                String username = req.getUserPrincipal().getName();
                usuario = usuarioEjb.findebyUserName(username).get(0);
                current=usuario.getRestauranteList().get(0);
        }
        return current;
    }

    private RestauranteFacade getFacade() 
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
                    return getFacade().count();
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

    public String prepareList() 
    {
        recreateModel();
        return "List";
    }

    public String prepareView() 
    {
        current = (Restaurante) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() 
    {
        current = new Restaurante();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() 
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
                current.setTblUsuarioDueId(usuario);
            }
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RestauranteCreated"));
            return prepareCreate();
        } 
        catch (Exception e) 
        {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() 
    {
        current = (Restaurante) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String prepararEdicion()
    { 
        return "restaurante/EditarRestLogeado";
    }
    
    public String actualizar() 
    {
        try 
        {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RestauranteUpdated"));
            return "paginaUsuario";
        } 
        catch (Exception e) 
        {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
        
    public String update() 
    {
        String txtProperty="";
        try 
        {
            HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            txtProperty = request.getParameter("myForm:resNit");
            current.setResNit(txtProperty);
            txtProperty = request.getParameter("myForm:resNombre");
            current.setResNombre(txtProperty);
            txtProperty = request.getParameter("myForm:resDireccion");
            current.setResDireccion(txtProperty);
            txtProperty = request.getParameter("myForm:resTelefono");
            current.setResTelefono(txtProperty);
            txtProperty = request.getParameter("myForm:resLogo");
            current.setResLogo(txtProperty);
            txtProperty = request.getParameter("myForm:resEstado");
            current.setResEstado(txtProperty);
            txtProperty = request.getParameter("myForm:resLatitud");
            current.setResLatitud(Float.valueOf(txtProperty));
            txtProperty = request.getParameter("myForm:tesLongitud");
            current.setTesLongitud(Float.valueOf(txtProperty));
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RestauranteUpdated"));
            return "EditarRestLogeado";
        } 
        catch (Exception e) 
        {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() 
    {
        current = (Restaurante) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
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

    private void performDestroy() 
    {
        try 
        {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RestauranteDeleted"));
        } 
        catch (Exception e) 
        {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() 
    {
        int count = getFacade().count();
        if (selectedItemIndex >= count) 
        {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) 
        {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() 
    {
        if (items == null) 
        {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() 
    {
        items = null;
    }

    private void recreatePagination() 
    {
        pagination = null;
    }

    public String next() 
    {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() 
    {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() 
    {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() 
    {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Restaurante getRestaurante(java.lang.Integer id) 
    {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Restaurante.class)
    public static class RestauranteControllerConverter implements Converter 
    {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) 
            {
                return null;
            }
            RestauranteController controller = (RestauranteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "restauranteController");
            return controller.getRestaurante(getKey(value));
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
            if (object instanceof Restaurante) 
            {
                Restaurante o = (Restaurante) object;
                return getStringKey(o.getResId());
            } 
            else 
            {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Restaurante.class.getName());
            }
        }
    }
}