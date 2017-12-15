/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "articulos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulos.findAll", query = "SELECT a FROM Articulos a")
    , @NamedQuery(name = "Articulos.findByIdarticulo", query = "SELECT a FROM Articulos a WHERE a.idarticulo = :idarticulo")
    , @NamedQuery(name = "Articulos.findByCodigo", query = "SELECT a FROM Articulos a WHERE a.codigo = :codigo")
    , @NamedQuery(name = "Articulos.findByNombre", query = "SELECT a FROM Articulos a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Articulos.findByStock", query = "SELECT a FROM Articulos a WHERE a.stock = :stock")
    , @NamedQuery(name = "Articulos.findByDescripcion", query = "SELECT a FROM Articulos a WHERE a.descripcion = :descripcion")
    , @NamedQuery(name = "Articulos.findByImagen", query = "SELECT a FROM Articulos a WHERE a.imagen = :imagen")
    , @NamedQuery(name = "Articulos.findByEstado", query = "SELECT a FROM Articulos a WHERE a.estado = :estado")})
public class Articulos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idarticulo")
    private Integer idarticulo;
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "stock")
    private int stock;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "imagen")
    private String imagen;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idarticulo")
    private List<DetalleIngresos> detalleIngresosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idarticulo")
    private List<DetalleVentas> detalleVentasList;
    @JoinColumn(name = "idcategoria", referencedColumnName = "idcategoria")
    @ManyToOne(optional = false)
    private Categorias idcategoria;
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Units unitId;

    public Articulos() {
    }

    public Articulos(Integer idarticulo) {
        this.idarticulo = idarticulo;
    }

    public Articulos(Integer idarticulo, String nombre, int stock, String estado) {
        this.idarticulo = idarticulo;
        this.nombre = nombre;
        this.stock = stock;
        this.estado = estado;
    }

    public Integer getIdarticulo() {
        return idarticulo;
    }

    public void setIdarticulo(Integer idarticulo) {
        this.idarticulo = idarticulo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<DetalleIngresos> getDetalleIngresosList() {
        return detalleIngresosList;
    }

    public void setDetalleIngresosList(List<DetalleIngresos> detalleIngresosList) {
        this.detalleIngresosList = detalleIngresosList;
    }

    @XmlTransient
    public List<DetalleVentas> getDetalleVentasList() {
        return detalleVentasList;
    }

    public void setDetalleVentasList(List<DetalleVentas> detalleVentasList) {
        this.detalleVentasList = detalleVentasList;
    }

    public Categorias getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Categorias idcategoria) {
        this.idcategoria = idcategoria;
    }

    public Units getUnitId() {
        return unitId;
    }

    public void setUnitId(Units unitId) {
        this.unitId = unitId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idarticulo != null ? idarticulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulos)) {
            return false;
        }
        Articulos other = (Articulos) object;
        if ((this.idarticulo == null && other.idarticulo != null) || (this.idarticulo != null && !this.idarticulo.equals(other.idarticulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Articulos[ idarticulo=" + idarticulo + " ]";
    }
    
}
