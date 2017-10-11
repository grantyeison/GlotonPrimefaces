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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "tbl_grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g"),
    @NamedQuery(name = "Grupo.findByGrupId", query = "SELECT g FROM Grupo g WHERE g.grupId = :grupId"),
    @NamedQuery(name = "Grupo.findByGrupdescripciouserLoginTaskCallbackn", query = "SELECT g FROM Grupo g WHERE g.grupdescripciouserLoginTaskCallbackn = :grupdescripciouserLoginTaskCallbackn")})
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "grup_id")
    private String grupId;
    @Size(max = 100)
    @Column(name = "grup_descripciouserLoginTaskCallbackn")
    private String grupdescripciouserLoginTaskCallbackn;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usugrupGrupId")
    private List<UsuarioGrupo> usuarioGrupoList;

    public Grupo() {
    }

    public Grupo(String grupId) {
        this.grupId = grupId;
    }

    public String getGrupId() {
        return grupId;
    }

    public void setGrupId(String grupId) {
        this.grupId = grupId;
    }

    public String getGrupdescripciouserLoginTaskCallbackn() {
        return grupdescripciouserLoginTaskCallbackn;
    }

    public void setGrupdescripciouserLoginTaskCallbackn(String grupdescripciouserLoginTaskCallbackn) {
        this.grupdescripciouserLoginTaskCallbackn = grupdescripciouserLoginTaskCallbackn;
    }

    @XmlTransient
    public List<UsuarioGrupo> getUsuarioGrupoList() {
        return usuarioGrupoList;
    }

    public void setUsuarioGrupoList(List<UsuarioGrupo> usuarioGrupoList) {
        this.usuarioGrupoList = usuarioGrupoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupId != null ? grupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.grupId == null && other.grupId != null) || (this.grupId != null && !this.grupId.equals(other.grupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Grupo[ grupId=" + grupId + " ]";
    }
    
}
