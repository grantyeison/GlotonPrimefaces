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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "tbl_calificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calificacion.findAll", query = "SELECT c FROM Calificacion c"),
    @NamedQuery(name = "Calificacion.findByCalId", query = "SELECT c FROM Calificacion c WHERE c.calId = :calId"),
    @NamedQuery(name = "Calificacion.findByCalPuntuacion", query = "SELECT c FROM Calificacion c WHERE c.calPuntuacion = :calPuntuacion"),
    @NamedQuery(name = "Calificacion.findByCalUsuario", query = "SELECT c FROM Calificacion c WHERE c.calUsuario = :calUsuario")})
public class Calificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cal_id")
    private Integer calId;
    @Column(name = "cal_puntuacion")
    private Integer calPuntuacion;
    @Size(max = 250)
    @Column(name = "cal_usuario")
    private String calUsuario;
    @JoinColumn(name = "tbl_plato_restaurante_plat_Id", referencedColumnName = "plat_Id")
    @ManyToOne(optional = false)
    private PlatoRestaurante tblplatorestauranteplatId;

    public Calificacion() {
    }

    public Calificacion(Integer calId) {
        this.calId = calId;
    }

    public Integer getCalId() {
        return calId;
    }

    public void setCalId(Integer calId) {
        this.calId = calId;
    }

    public Integer getCalPuntuacion() {
        return calPuntuacion;
    }

    public void setCalPuntuacion(Integer calPuntuacion) {
        this.calPuntuacion = calPuntuacion;
    }

    public String getCalUsuario() {
        return calUsuario;
    }

    public void setCalUsuario(String calUsuario) {
        this.calUsuario = calUsuario;
    }

    public PlatoRestaurante getTblplatorestauranteplatId() {
        return tblplatorestauranteplatId;
    }

    public void setTblplatorestauranteplatId(PlatoRestaurante tblplatorestauranteplatId) {
        this.tblplatorestauranteplatId = tblplatorestauranteplatId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calId != null ? calId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calificacion)) {
            return false;
        }
        Calificacion other = (Calificacion) object;
        if ((this.calId == null && other.calId != null) || (this.calId != null && !this.calId.equals(other.calId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Calificacion[ calId=" + calId + " ]";
    }
    
}
