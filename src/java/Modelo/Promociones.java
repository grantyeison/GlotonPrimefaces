/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "tbl_promociones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promociones.findAll", query = "SELECT p FROM Promociones p"),
    @NamedQuery(name = "Promociones.findByPromoId", query = "SELECT p FROM Promociones p WHERE p.promoId = :promoId"),
    @NamedQuery(name = "Promociones.findByPromoPrecio", query = "SELECT p FROM Promociones p WHERE p.promoPrecio = :promoPrecio"),
    @NamedQuery(name = "Promociones.findByPromoDescripcion", query = "SELECT p FROM Promociones p WHERE p.promoDescripcion = :promoDescripcion"),
    @NamedQuery(name = "Promociones.findByPromoFecha", query = "SELECT p FROM Promociones p WHERE p.promoFecha = :promoFecha"),
    @NamedQuery(name = "Promociones.findByPromoDuracion", query = "SELECT p FROM Promociones p WHERE p.promoDuracion = :promoDuracion")})
public class Promociones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "promo_id")
    private Integer promoId;
    @Column(name = "promo_precio")
    private Integer promoPrecio;
    @Size(max = 10000)
    @Column(name = "promo_Descripcion")
    private String promoDescripcion;
    @Column(name = "promo_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date promoFecha;
    @Column(name = "promo_duracion")
    private Integer promoDuracion;
    @OneToMany(mappedBy = "promocionesCombospromoid")
    private List<PromocioneslPlatoRestaurante> promocioneslPlatoRestauranteList;

    public Promociones() {
    }

    public Promociones(Integer promoId) {
        this.promoId = promoId;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public Integer getPromoPrecio() {
        return promoPrecio;
    }

    public void setPromoPrecio(Integer promoPrecio) {
        this.promoPrecio = promoPrecio;
    }

    public String getPromoDescripcion() {
        return promoDescripcion;
    }

    public void setPromoDescripcion(String promoDescripcion) {
        this.promoDescripcion = promoDescripcion;
    }

    public Date getPromoFecha() {
        return promoFecha;
    }

    public void setPromoFecha(Date promoFecha) {
        this.promoFecha = promoFecha;
    }

    public Integer getPromoDuracion() {
        return promoDuracion;
    }

    public void setPromoDuracion(Integer promoDuracion) {
        this.promoDuracion = promoDuracion;
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
        hash += (promoId != null ? promoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promociones)) {
            return false;
        }
        Promociones other = (Promociones) object;
        if ((this.promoId == null && other.promoId != null) || (this.promoId != null && !this.promoId.equals(other.promoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Promociones[ promoId=" + promoId + " ]";
    }
    
}
