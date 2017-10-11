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
@Table(name = "tbl_plato_restaurante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatoRestaurante.findAll", query = "SELECT p FROM PlatoRestaurante p"),
    @NamedQuery(name = "PlatoRestaurante.findByPlatIngredientes", query = "SELECT p FROM PlatoRestaurante p WHERE p.platIngredientes = :platIngredientes"),
    @NamedQuery(name = "PlatoRestaurante.findByPlatDescripcion", query = "SELECT p FROM PlatoRestaurante p WHERE p.platDescripcion = :platDescripcion"),
    @NamedQuery(name = "PlatoRestaurante.findByPlatPrecio", query = "SELECT p FROM PlatoRestaurante p WHERE p.platPrecio = :platPrecio"),
    @NamedQuery(name = "PlatoRestaurante.findByPlatEstado", query = "SELECT p FROM PlatoRestaurante p WHERE p.platEstado = :platEstado"),
    @NamedQuery(name = "PlatoRestaurante.findByPlatId", query = "SELECT p FROM PlatoRestaurante p WHERE p.platId = :platId")})
public class PlatoRestaurante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 10000)
    @Column(name = "plat_Ingredientes")
    private String platIngredientes;
    @Size(max = 10000)
    @Column(name = "plat_Descripcion")
    private String platDescripcion;
    @Column(name = "plat_Precio")
    private Integer platPrecio;
    @Size(max = 45)
    @Column(name = "plat_Estado")
    private String platEstado;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "plat_Id")
    private Integer platId;
    @JoinColumn(name = "tbl_plato_pla_Id", referencedColumnName = "pla_Id")
    @ManyToOne(optional = false)
    private Plato tblplatoplaId;
    @JoinColumn(name = "tbl_restaurante_res_id", referencedColumnName = "res_id")
    @ManyToOne(optional = false)
    private Restaurante tblRestauranteResId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblplatorestauranteplatId")
    private List<Calificacion> calificacionList;
    @OneToMany(mappedBy = "tblplatorestauranteplatId")
    private List<PromocioneslPlatoRestaurante> promocioneslPlatoRestauranteList;

    public PlatoRestaurante() {
    }

    public PlatoRestaurante(Integer platId) {
        this.platId = platId;
    }

    public String getPlatIngredientes() {
        return platIngredientes;
    }

    public void setPlatIngredientes(String platIngredientes) {
        this.platIngredientes = platIngredientes;
    }

    public String getPlatDescripcion() {
        return platDescripcion;
    }

    public void setPlatDescripcion(String platDescripcion) {
        this.platDescripcion = platDescripcion;
    }

    public Integer getPlatPrecio() {
        return platPrecio;
    }

    public void setPlatPrecio(Integer platPrecio) {
        this.platPrecio = platPrecio;
    }

    public String getPlatEstado() {
        return platEstado;
    }

    public void setPlatEstado(String platEstado) {
        this.platEstado = platEstado;
    }

    public Integer getPlatId() {
        return platId;
    }

    public void setPlatId(Integer platId) {
        this.platId = platId;
    }

    public Plato getTblplatoplaId() {
        return tblplatoplaId;
    }

    public void setTblplatoplaId(Plato tblplatoplaId) {
        this.tblplatoplaId = tblplatoplaId;
    }

    public Restaurante getTblRestauranteResId() {
        return tblRestauranteResId;
    }

    public void setTblRestauranteResId(Restaurante tblRestauranteResId) {
        this.tblRestauranteResId = tblRestauranteResId;
    }

    @XmlTransient
    public List<Calificacion> getCalificacionList() {
        return calificacionList;
    }

    public void setCalificacionList(List<Calificacion> calificacionList) {
        this.calificacionList = calificacionList;
    }

    @XmlTransient
    public List<PromocioneslPlatoRestaurante> getPromocioneslPlatoRestauranteList() {
        return promocioneslPlatoRestauranteList;
    }

    public void setPromocioneslPlatoRestauranteList(List<PromocioneslPlatoRestaurante> promocioneslPlatoRestauranteList) {
        this.promocioneslPlatoRestauranteList = promocioneslPlatoRestauranteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (platId != null ? platId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlatoRestaurante)) {
            return false;
        }
        PlatoRestaurante other = (PlatoRestaurante) object;
        if ((this.platId == null && other.platId != null) || (this.platId != null && !this.platId.equals(other.platId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.PlatoRestaurante[ platId=" + platId + " ]";
    }
    
}
