/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Apollo
 */
@Entity
@Table(name = "tbl_promocionesl_plato_restaurante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PromocioneslPlatoRestaurante.findAll", query = "SELECT p FROM PromocioneslPlatoRestaurante p")
    , @NamedQuery(name = "PromocioneslPlatoRestaurante.findById", query = "SELECT p FROM PromocioneslPlatoRestaurante p WHERE p.id = :id")})
public class PromocioneslPlatoRestaurante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "Promociones_Combos_promo_id", referencedColumnName = "promo_id")
    @ManyToOne
    private Promociones promocionesCombospromoid;
    @JoinColumn(name = "tbl_Plato_Restaurante_plat_Id", referencedColumnName = "plat_Id")
    @ManyToOne
    private PlatoRestaurante tblPlatoRestauranteplatId;

    public PromocioneslPlatoRestaurante() {
    }

    public PromocioneslPlatoRestaurante(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Promociones getPromocionesCombospromoid() {
        return promocionesCombospromoid;
    }

    public void setPromocionesCombospromoid(Promociones promocionesCombospromoid) {
        this.promocionesCombospromoid = promocionesCombospromoid;
    }

    public PlatoRestaurante getTblPlatoRestauranteplatId() {
        return tblPlatoRestauranteplatId;
    }

    public void setTblPlatoRestauranteplatId(PlatoRestaurante tblPlatoRestauranteplatId) {
        this.tblPlatoRestauranteplatId = tblPlatoRestauranteplatId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PromocioneslPlatoRestaurante)) {
            return false;
        }
        PromocioneslPlatoRestaurante other = (PromocioneslPlatoRestaurante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.PromocioneslPlatoRestaurante[ id=" + id + " ]";
    }
    
}
