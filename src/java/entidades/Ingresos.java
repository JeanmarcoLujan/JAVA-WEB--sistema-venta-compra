/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "ingresos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingresos.findAll", query = "SELECT i FROM Ingresos i")
    , @NamedQuery(name = "Ingresos.findByIdingreso", query = "SELECT i FROM Ingresos i WHERE i.idingreso = :idingreso")
    , @NamedQuery(name = "Ingresos.findByTipoComprobante", query = "SELECT i FROM Ingresos i WHERE i.tipoComprobante = :tipoComprobante")
    , @NamedQuery(name = "Ingresos.findBySerieComprobante", query = "SELECT i FROM Ingresos i WHERE i.serieComprobante = :serieComprobante")
    , @NamedQuery(name = "Ingresos.findByNumComprobante", query = "SELECT i FROM Ingresos i WHERE i.numComprobante = :numComprobante")
    , @NamedQuery(name = "Ingresos.findByFechaHora", query = "SELECT i FROM Ingresos i WHERE i.fechaHora = :fechaHora")
    , @NamedQuery(name = "Ingresos.findByEstado", query = "SELECT i FROM Ingresos i WHERE i.estado = :estado")})
public class Ingresos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idingreso")
    private Integer idingreso;
    @Basic(optional = false)
    @Column(name = "tipo_comprobante")
    private String tipoComprobante;
    @Column(name = "serie_comprobante")
    private String serieComprobante;
    @Basic(optional = false)
    @Column(name = "num_comprobante")
    private String numComprobante;
    @Basic(optional = false)
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "idproveedor", referencedColumnName = "idpersona")
    @ManyToOne(optional = false)
    private Personas idproveedor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idingreso")
    private List<DetalleIngresos> detalleIngresosList;

    public Ingresos() {
    }

    public Ingresos(Integer idingreso) {
        this.idingreso = idingreso;
    }

    public Ingresos(Integer idingreso, String tipoComprobante, String numComprobante, Date fechaHora, String estado) {
        this.idingreso = idingreso;
        this.tipoComprobante = tipoComprobante;
        this.numComprobante = numComprobante;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }
    
    

    public Integer getIdingreso() {
        return idingreso;
    }

    public void setIdingreso(Integer idingreso) {
        this.idingreso = idingreso;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getSerieComprobante() {
        return serieComprobante;
    }

    public void setSerieComprobante(String serieComprobante) {
        this.serieComprobante = serieComprobante;
    }

    public String getNumComprobante() {
        return numComprobante;
    }

    public void setNumComprobante(String numComprobante) {
        this.numComprobante = numComprobante;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Personas getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(Personas idproveedor) {
        this.idproveedor = idproveedor;
    }

    @XmlTransient
    public List<DetalleIngresos> getDetalleIngresosList() {
        return detalleIngresosList;
    }

    public void setDetalleIngresosList(List<DetalleIngresos> detalleIngresosList) {
        this.detalleIngresosList = detalleIngresosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idingreso != null ? idingreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingresos)) {
            return false;
        }
        Ingresos other = (Ingresos) object;
        if ((this.idingreso == null && other.idingreso != null) || (this.idingreso != null && !this.idingreso.equals(other.idingreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Ingresos[ idingreso=" + idingreso + " ]";
    }
    
}
