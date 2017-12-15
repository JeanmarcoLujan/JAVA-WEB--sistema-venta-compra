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
@Table(name = "ventas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ventas.findAll", query = "SELECT v FROM Ventas v")
    , @NamedQuery(name = "Ventas.findByIdventa", query = "SELECT v FROM Ventas v WHERE v.idventa = :idventa")
    , @NamedQuery(name = "Ventas.findByTipoComprobante", query = "SELECT v FROM Ventas v WHERE v.tipoComprobante = :tipoComprobante")
    , @NamedQuery(name = "Ventas.findBySerieComprobante", query = "SELECT v FROM Ventas v WHERE v.serieComprobante = :serieComprobante")
    , @NamedQuery(name = "Ventas.findByNumComprobante", query = "SELECT v FROM Ventas v WHERE v.numComprobante = :numComprobante")
    , @NamedQuery(name = "Ventas.findByFechaHora", query = "SELECT v FROM Ventas v WHERE v.fechaHora = :fechaHora")
    , @NamedQuery(name = "Ventas.findByEstado", query = "SELECT v FROM Ventas v WHERE v.estado = :estado")})
public class Ventas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idventa")
    private Integer idventa;
    @Basic(optional = false)
    @Column(name = "tipo_comprobante")
    private String tipoComprobante;
    @Basic(optional = false)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idventa")
    private List<DetalleVentas> detalleVentasList;
    @JoinColumn(name = "idcliente", referencedColumnName = "idpersona")
    @ManyToOne(optional = false)
    private Personas idcliente;

    public Ventas() {
    }

    public Ventas(Integer idventa) {
        this.idventa = idventa;
    }

    public Ventas(Integer idventa, String tipoComprobante, String serieComprobante, String numComprobante, Date fechaHora, String estado) {
        this.idventa = idventa;
        this.tipoComprobante = tipoComprobante;
        this.serieComprobante = serieComprobante;
        this.numComprobante = numComprobante;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public Integer getIdventa() {
        return idventa;
    }

    public void setIdventa(Integer idventa) {
        this.idventa = idventa;
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

    @XmlTransient
    public List<DetalleVentas> getDetalleVentasList() {
        return detalleVentasList;
    }

    public void setDetalleVentasList(List<DetalleVentas> detalleVentasList) {
        this.detalleVentasList = detalleVentasList;
    }

    public Personas getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Personas idcliente) {
        this.idcliente = idcliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idventa != null ? idventa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ventas)) {
            return false;
        }
        Ventas other = (Ventas) object;
        if ((this.idventa == null && other.idventa != null) || (this.idventa != null && !this.idventa.equals(other.idventa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Ventas[ idventa=" + idventa + " ]";
    }
    
}
