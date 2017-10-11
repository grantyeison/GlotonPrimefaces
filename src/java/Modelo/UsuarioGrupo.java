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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "tbl_usuario_grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioGrupo.findAll", query = "SELECT u FROM UsuarioGrupo u"),
    @NamedQuery(name = "UsuarioGrupo.findByUsugrupId", query = "SELECT u FROM UsuarioGrupo u WHERE u.usugrupId = :usugrupId"),
    @NamedQuery(name = "UsuarioGrupo.findByUsugrupDueUsuario", query = "SELECT u FROM UsuarioGrupo u WHERE u.usugrupDueUsuario = :usugrupDueUsuario")})
public class UsuarioGrupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usugrup_id")
    private Integer usugrupId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "usugrup_due_usuario")
    private String usugrupDueUsuario;
    @JoinColumn(name = "usugrup_grup_id", referencedColumnName = "grup_id")
    @ManyToOne(optional = false)
    private Grupo usugrupGrupId;
    @JoinColumn(name = "usugrup_due_id", referencedColumnName = "due_id")
    @ManyToOne(optional = false)
    private Usuario usugrupDueId;

    public UsuarioGrupo() {
    }

    public UsuarioGrupo(Integer usugrupId) {
        this.usugrupId = usugrupId;
    }

    public UsuarioGrupo(Integer usugrupId, String usugrupDueUsuario) {
        this.usugrupId = usugrupId;
        this.usugrupDueUsuario = usugrupDueUsuario;
    }

    public Integer getUsugrupId() {
        return usugrupId;
    }

    public void setUsugrupId(Integer usugrupId) {
        this.usugrupId = usugrupId;
    }

    public String getUsugrupDueUsuario() {
        return usugrupDueUsuario;
    }

    public void setUsugrupDueUsuario(String usugrupDueUsuario) {
        this.usugrupDueUsuario = usugrupDueUsuario;
    }

    public Grupo getUsugrupGrupId() {
        return usugrupGrupId;
    }

    public void setUsugrupGrupId(Grupo usugrupGrupId) {
        this.usugrupGrupId = usugrupGrupId;
    }

    public Usuario getUsugrupDueId() {
        return usugrupDueId;
    }

    public void setUsugrupDueId(Usuario usugrupDueId) {
        this.usugrupDueId = usugrupDueId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usugrupId != null ? usugrupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioGrupo)) {
            return false;
        }
        UsuarioGrupo other = (UsuarioGrupo) object;
        if ((this.usugrupId == null && other.usugrupId != null) || (this.usugrupId != null && !this.usugrupId.equals(other.usugrupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.UsuarioGrupo[ usugrupId=" + usugrupId + " ]";
    }
    
}
