/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "tbl_restaurante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Restaurante.findAll", query = "SELECT r FROM Restaurante r"),
    @NamedQuery(name = "Restaurante.findByResNit", query = "SELECT r FROM Restaurante r WHERE r.resNit = :resNit"),
    @NamedQuery(name = "Restaurante.findByResNombre", query = "SELECT r FROM Restaurante r WHERE r.resNombre = :resNombre"),
    @NamedQuery(name = "Restaurante.findByResDireccion", query = "SELECT r FROM Restaurante r WHERE r.resDireccion = :resDireccion"),
    @NamedQuery(name = "Restaurante.findByResTelefono", query = "SELECT r FROM Restaurante r WHERE r.resTelefono = :resTelefono"),
    @NamedQuery(name = "Restaurante.findByResLogo", query = "SELECT r FROM Restaurante r WHERE r.resLogo = :resLogo"),
    @NamedQuery(name = "Restaurante.findByResEstado", query = "SELECT r FROM Restaurante r WHERE r.resEstado = :resEstado"),
    @NamedQuery(name = "Restaurante.findByResLatitud", query = "SELECT r FROM Restaurante r WHERE r.resLatitud = :resLatitud"),
    @NamedQuery(name = "Restaurante.findByTesLongitud", query = "SELECT r FROM Restaurante r WHERE r.tesLongitud = :tesLongitud"),
    @NamedQuery(name = "Restaurante.findByUserName", query = "SELECT r FROM Restaurante r WHERE r.tblUsuarioDueId.dueUsuario = :userName"),
    @NamedQuery(name = "Restaurante.findByResId", query = "SELECT r FROM Restaurante r WHERE r.resId = :resId")})
public class Restaurante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 250)
    @Column(name = "res_Nit")
    private String resNit;
    @Size(max = 1000)
    @Column(name = "res_Nombre")
    private String resNombre;
    @Size(max = 1000)
    @Column(name = "res_Direccion")
    private String resDireccion;
    @Size(max = 250)
    @Column(name = "res_Telefono")
    private String resTelefono;
    @Size(max = 1000)
    @Column(name = "res_Logo")
    private String resLogo;
    @Size(max = 45)
    @Column(name = "res_Estado")
    private String resEstado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "res_Latitud")
    private Float resLatitud;
    @Column(name = "tes_Longitud")
    private Float tesLongitud;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "res_id")
    private Integer resId;
    @JoinColumn(name = "tbl_usuario_due_id", referencedColumnName = "due_id")
    @ManyToOne
    private Usuario tblUsuarioDueId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblRestauranteResId")
    private List<PlatoRestaurante> platoRestauranteList;

    public Restaurante() {
    }

    public Restaurante(Integer resId) {
        this.resId = resId;
    }

    public String getResNit() {
        return resNit;
    }

    public void setResNit(String resNit) {
        this.resNit = resNit;
    }

    public String getResNombre() {
        return resNombre;
    }

    public void setResNombre(String resNombre) {
        this.resNombre = resNombre;
    }

    public String getResDireccion() {
        return resDireccion;
    }

    public void setResDireccion(String resDireccion) {
        this.resDireccion = resDireccion;
    }

    public String getResTelefono() {
        return resTelefono;
    }

    public void setResTelefono(String resTelefono) {
        this.resTelefono = resTelefono;
    }

    public String getResLogo() {
        return resLogo;
    }

    public void setResLogo(String resLogo) {
        this.resLogo = resLogo;
    }

    public String getResEstado() {
        return resEstado;
    }

    public void setResEstado(String resEstado) {
        this.resEstado = resEstado;
    }

    public Float getResLatitud() {
        return resLatitud;
    }

    public void setResLatitud(Float resLatitud) {
        this.resLatitud = resLatitud;
    }

    public Float getTesLongitud() {
        return tesLongitud;
    }

    public void setTesLongitud(Float tesLongitud) {
        this.tesLongitud = tesLongitud;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Usuario getTblUsuarioDueId() {
        return tblUsuarioDueId;
    }

    public void setTblUsuarioDueId(Usuario tblUsuarioDueId) {
        this.tblUsuarioDueId = tblUsuarioDueId;
    }

    @XmlTransient
    public List<PlatoRestaurante> getPlatoRestauranteList() {
        return platoRestauranteList;
    }

    public void setPlatoRestauranteList(List<PlatoRestaurante> platoRestauranteList) {
        this.platoRestauranteList = platoRestauranteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resId != null ? resId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Restaurante)) {
            return false;
        }
        Restaurante other = (Restaurante) object;
        if ((this.resId == null && other.resId != null) || (this.resId != null && !this.resId.equals(other.resId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Restaurante[ resId=" + resId + " ]";
    }
    
}
