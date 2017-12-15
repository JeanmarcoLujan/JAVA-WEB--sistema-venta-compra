/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

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
 * @author User
 */
@Entity
@Table(name = "detalle_ingresos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleIngresos.findAll", query = "SELECT d FROM DetalleIngresos d")
    , @NamedQuery(name = "DetalleIngresos.findByIddetalleIngreso", query = "SELECT d FROM DetalleIngresos d WHERE d.iddetalleIngreso = :iddetalleIngreso")
    , @NamedQuery(name = "DetalleIngresos.findByCantidad", query = "SELECT d FROM DetalleIngresos d WHERE d.cantidad = :cantidad")
    , @NamedQuery(name = "DetalleIngresos.findByPrecioCompra", query = "SELECT d FROM DetalleIngresos d WHERE d.precioCompra = :precioCompra")
    , @NamedQuery(name = "DetalleIngresos.findByPrecioVenta", query = "SELECT d FROM DetalleIngresos d WHERE d.precioVenta = :precioVenta")})
public class DetalleIngresos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddetalle_ingreso")
    private Integer iddetalleIngreso;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "precio_compra")
    private long precioCompra;
    @Basic(optional = false)
    @Column(name = "precio_venta")
    private long precioVenta;
    @JoinColumn(name = "idingreso", referencedColumnName = "idingreso")
    @ManyToOne(optional = false)
    private Ingresos idingreso;
    @JoinColumn(name = "idarticulo", referencedColumnName = "idarticulo")
    @ManyToOne(optional = false)
    private Articulos idarticulo;

    public DetalleIngresos() {
    }

    public DetalleIngresos(Integer iddetalleIngreso) {
        this.iddetalleIngreso = iddetalleIngreso;
    }

    public DetalleIngresos(Integer iddetalleIngreso, int cantidad, long precioCompra, long precioVenta) {
        this.iddetalleIngreso = iddetalleIngreso;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
    }

    public Integer getIddetalleIngreso() {
        return iddetalleIngreso;
    }

    public void setIddetalleIngreso(Integer iddetalleIngreso) {
        this.iddetalleIngreso = iddetalleIngreso;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public long getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(long precioCompra) {
        this.precioCompra = precioCompra;
    }

    public long getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(long precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Ingresos getIdingreso() {
        return idingreso;
    }

    public void setIdingreso(Ingresos idingreso) {
        this.idingreso = idingreso;
    }

    public Articulos getIdarticulo() {
        return idarticulo;
    }

    public void setIdarticulo(Articulos idarticulo) {
        this.idarticulo = idarticulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddetalleIngreso != null ? iddetalleIngreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleIngresos)) {
            return false;
        }
        DetalleIngresos other = (DetalleIngresos) object;
        if ((this.iddetalleIngreso == null && other.iddetalleIngreso != null) || (this.iddetalleIngreso != null && !this.iddetalleIngreso.equals(other.iddetalleIngreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.DetalleIngresos[ iddetalleIngreso=" + iddetalleIngreso + " ]";
    }
    
}
