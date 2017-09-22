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
@Table(name = "tbl_plato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plato.findAll", query = "SELECT p FROM Plato p"),
    @NamedQuery(name = "Plato.findByPlaNombre", query = "SELECT p FROM Plato p WHERE p.plaNombre = :plaNombre"),
    @NamedQuery(name = "Plato.findByPlaImagen", query = "SELECT p FROM Plato p WHERE p.plaImagen = :plaImagen"),
    @NamedQuery(name = "Plato.findByPlaEstado", query = "SELECT p FROM Plato p WHERE p.plaEstado = :plaEstado"),
    @NamedQuery(name = "Plato.findByPlaId", query = "SELECT p FROM Plato p WHERE p.plaId = :plaId")})
public class Plato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 250)
    @Column(name = "pla_Nombre")
    private String plaNombre;
    @Size(max = 250)
    @Column(name = "pla_Imagen")
    private String plaImagen;
    @Size(max = 45)
    @Column(name = "pla_Estado")
    private String plaEstado;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pla_Id")
    private Integer plaId;
    @JoinColumn(name = "tbl_categoria_cat_Id", referencedColumnName = "cat_Id")
    @ManyToOne(optional = false)
    private Categoria tblcategoriacatId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblplatoplaId")
    private List<PlatoRestaurante> platoRestauranteList;

    public Plato() {
    }

    public Plato(Integer plaId) {
        this.plaId = plaId;
    }

    public String getPlaNombre() {
        return plaNombre;
    }

    public void setPlaNombre(String plaNombre) {
        this.plaNombre = plaNombre;
    }

    public String getPlaImagen() {
        return plaImagen;
    }

    public void setPlaImagen(String plaImagen) {
        this.plaImagen = plaImagen;
    }

    public String getPlaEstado() {
        return plaEstado;
    }

    public void setPlaEstado(String plaEstado) {
        this.plaEstado = plaEstado;
    }

    public Integer getPlaId() {
        return plaId;
    }

    public void setPlaId(Integer plaId) {
        this.plaId = plaId;
    }

    public Categoria getTblcategoriacatId() {
        return tblcategoriacatId;
    }

    public void setTblcategoriacatId(Categoria tblcategoriacatId) {
        this.tblcategoriacatId = tblcategoriacatId;
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
        hash += (plaId != null ? plaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plato)) {
            return false;
        }
        Plato other = (Plato) object;
        if ((this.plaId == null && other.plaId != null) || (this.plaId != null && !this.plaId.equals(other.plaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Plato[ plaId=" + plaId + " ]";
    }
    
}
