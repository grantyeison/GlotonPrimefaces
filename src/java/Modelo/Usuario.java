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
 * @author Apollo
 */
@Entity
@Table(name = "tbl_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByDueId", query = "SELECT u FROM Usuario u WHERE u.dueId = :dueId")
    , @NamedQuery(name = "Usuario.findByDueNombre", query = "SELECT u FROM Usuario u WHERE u.dueNombre = :dueNombre")
    , @NamedQuery(name = "Usuario.findByDueEmail", query = "SELECT u FROM Usuario u WHERE u.dueEmail = :dueEmail")
    , @NamedQuery(name = "Usuario.findByDueRol", query = "SELECT u FROM Usuario u WHERE u.dueRol = :dueRol")
    , @NamedQuery(name = "Usuario.findByDueUsuario", query = "SELECT u FROM Usuario u WHERE u.dueUsuario = :dueUsuario")
    , @NamedQuery(name = "Usuario.findByDuePass", query = "SELECT u FROM Usuario u WHERE u.duePass = :duePass")
    , @NamedQuery(name = "Usuario.findByDueEstado", query = "SELECT u FROM Usuario u WHERE u.dueEstado = :dueEstado")
    , @NamedQuery(name = "Usuario.findByDueFechaRegistro", query = "SELECT u FROM Usuario u WHERE u.dueFechaRegistro = :dueFechaRegistro")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "due_id")
    private Integer dueId;
    @Size(max = 1000)
    @Column(name = "due_Nombre")
    private String dueNombre;
    @Size(max = 250)
    @Column(name = "due_email")
    private String dueEmail;
    @Size(max = 45)
    @Column(name = "due_rol")
    private String dueRol;
    @Size(max = 250)
    @Column(name = "due_usuario")
    private String dueUsuario;
    @Size(max = 1000)
    @Column(name = "due_pass")
    private String duePass;
    @Size(max = 45)
    @Column(name = "due_estado")
    private String dueEstado;
    @Column(name = "due_Fecha_Registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueFechaRegistro;
    @OneToMany(mappedBy = "tblUsuariodueid")
    private List<Restaurante> restauranteList;

    public Usuario() {
    }

    public Usuario(Integer dueId) {
        this.dueId = dueId;
    }

    public Integer getDueId() {
        return dueId;
    }

    public void setDueId(Integer dueId) {
        this.dueId = dueId;
    }

    public String getDueNombre() {
        return dueNombre;
    }

    public void setDueNombre(String dueNombre) {
        this.dueNombre = dueNombre;
    }

    public String getDueEmail() {
        return dueEmail;
    }

    public void setDueEmail(String dueEmail) {
        this.dueEmail = dueEmail;
    }

    public String getDueRol() {
        return dueRol;
    }

    public void setDueRol(String dueRol) {
        this.dueRol = dueRol;
    }

    public String getDueUsuario() {
        return dueUsuario;
    }

    public void setDueUsuario(String dueUsuario) {
        this.dueUsuario = dueUsuario;
    }

    public String getDuePass() {
        return duePass;
    }

    public void setDuePass(String duePass) {
        this.duePass = duePass;
    }

    public String getDueEstado() {
        return dueEstado;
    }

    public void setDueEstado(String dueEstado) {
        this.dueEstado = dueEstado;
    }

    public Date getDueFechaRegistro() {
        return dueFechaRegistro;
    }

    public void setDueFechaRegistro(Date dueFechaRegistro) {
        this.dueFechaRegistro = dueFechaRegistro;
    }

    @XmlTransient
    public List<Restaurante> getRestauranteList() {
        return restauranteList;
    }

    public void setRestauranteList(List<Restaurante> restauranteList) {
        this.restauranteList = restauranteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dueId != null ? dueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.dueId == null && other.dueId != null) || (this.dueId != null && !this.dueId.equals(other.dueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Usuario[ dueId=" + dueId + " ]";
    }
    
}
