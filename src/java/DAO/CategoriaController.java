package DAO;

import Modelo.Categoria;
import DAO.util.JsfUtil;
import DAO.util.PaginationHelper;
import Bean.CategoriaFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named("categoriaController")
@SessionScoped
public class CategoriaController implements Serializable {

    private Categoria current;
    private DataModel items = null;
    @EJB
    private Bean.CategoriaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private UploadedFile foto;
    private String rutaFoto;
    private String rutaFotoAbsoluta;

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public String getRutaFotoAbsoluta() {
        return rutaFotoAbsoluta;
    }

    public void setRutaFotoAbsoluta(String rutaFotoAbsoluta) {
        this.rutaFotoAbsoluta = rutaFotoAbsoluta;
    }

    public UploadedFile getFoto() {
        return foto;
    }

    public void setFoto(UploadedFile foto) {
        this.foto = foto;
    }
    private List<String> estado = new ArrayList<String>();

    
    public CategoriaController() {
        estado.add("Activo");
        estado.add("Inactivo");
        
        String OS = System.getProperty("os.name").toLowerCase();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = (String) servletContext.getRealPath("/"); 
        if(OS.contains("nux"))
        {
           this.rutaFotoAbsoluta=realPath.replace("build/", "")+"resources/imagenes/categoria/"; 
        }
        else
        {
            this.rutaFotoAbsoluta=realPath.replace("build\\", "")+"resources\\imagenes\\categoria\\";
        }
        
        
    }

    public Categoria getSelected() {
        if (current == null) {
            current = new Categoria();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CategoriaFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
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
        current = (Categoria) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Categoria();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            
            if(foto!=null)
            {
                getFacade().create(current);
                int i = this.foto.getFileName().lastIndexOf('.');            
                String extension = this.foto.getFileName().substring(i+1);
                System.out.println("id del creado: "+current.getCatId());
                String nombre = current.getCatId()+"."+extension;
               
                current.setCatImagen(nombre);
                getFacade().edit(current);
                this.GuardarFoto(nombre, this.foto.getInputstream());
                
            
            Thread.sleep(2000);
            this.foto=null;  
            }
            
            else
            {
                List<String> lista = new ArrayList<>();
                lista.add("cargar foto");
                JsfUtil.addErrorMessages(lista);
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CategoriaCreated"));
            
            
            
            
          return prepareCreate();  
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

     private void GuardarFoto(String filename, InputStream in)
    {
       try 
       { 
            OutputStream out = new FileOutputStream(new File(this.rutaFotoAbsoluta + filename));              
            int read = 0;
            byte[] bytes = new byte[1024];              
            while ((read = in.read(bytes)) != -1) 
            {
                out.write(bytes, 0, read);
            }              
            in.close();
            out.flush();
            out.close();
       } catch (IOException e)
       {
            System.out.println(e.getMessage());
       } 
    }
    
    
    public String prepareEdit() {
        current = (Categoria) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    
    public List<String> getEstado() {
        return estado;
    }
    
    public String update() {
        try {
            
            HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String txtProperty = request.getParameter("formEditCateg:catEstado");
            current.setCatEstado(txtProperty);
            
            if(foto!=null)
            {
                if(current.getCatImagen().equals("") || current.getCatImagen().equals("Imagen Registrada") )
                {
                    int i = this.foto.getFileName().lastIndexOf('.');            
                    String extension = this.foto.getFileName().substring(i+1);
                    
                    //String nombre = current.getPlaId()+"."+extension;
                    String nombre = current.getCatId()+"."+extension;
                    System.out.println("el nombre del nuevo archivo es: "+nombre);
                    this.GuardarFoto(nombre, this.foto.getInputstream());
                    Thread.sleep(2000);
                    this.foto=null;

                    //current.setPlaImagen(nombre);
                    current.setCatImagen(nombre);
                }
                else
                {
                    String oldname = current.getCatImagen();
                    File file = new File(rutaFotoAbsoluta + oldname);
                    if(file.exists())
                    {
                        if(file.delete())
                        {
                            int i = this.foto.getFileName().lastIndexOf('.');            
                            String extension = this.foto.getFileName().substring(i+1);

                        //String nombre = current.getPlaId()+"."+extension;
                            String nombre = current.getCatId()+"."+extension;
                            System.out.println("el nombre del nuevo archivo es: "+nombre);
                            this.GuardarFoto(nombre, this.foto.getInputstream());
                            Thread.sleep(2000);
                            this.foto=null;
                            current.setCatImagen(nombre);
                        }
                        

                    //current.setPlaImagen(nombre);
                        
                    }
                    else
                    {
                        int i = this.foto.getFileName().lastIndexOf('.');            
                        String extension = this.foto.getFileName().substring(i+1);

                        //String nombre = current.getPlaId()+"."+extension;
                        String nombre = current.getCatId()+"."+extension;
                        System.out.println("el nombre del nuevo archivo es: "+nombre);
                        this.GuardarFoto(nombre, this.foto.getInputstream());
                        Thread.sleep(2000);
                        this.foto=null;

                        //current.setPlaImagen(nombre);
                        current.setCatImagen(nombre);
                    }
                }
            }
            else
            {
                
                List<String> lista = new ArrayList<>();
                lista.add("cargar foto");
                JsfUtil.addErrorMessages(lista);
            }
            
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CategoriaUpdated"));
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Categoria) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CategoriaDeleted"));
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

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
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

    public Categoria getCategoria(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    public List<Categoria> getListCategorias()
    {
        List<Categoria> list;
        list = ejbFacade.findAll();
        return list;
    }
    

    @FacesConverter(forClass = Categoria.class)
    public static class CategoriaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CategoriaController controller = (CategoriaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "categoriaController");
            return controller.getCategoria(getKey(value));
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
            if (object instanceof Categoria) {
                Categoria o = (Categoria) object;
                return getStringKey(o.getCatId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Categoria.class.getName());
            }
        }

    }
    public void cargarFoto(FileUploadEvent event)
    {
        RequestContext requestContext = RequestContext.getCurrentInstance(); 
        this.foto=event.getFile();
        requestContext.update("formularioCategoria");        
        
    }


}
