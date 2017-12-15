/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author User
 */
public class ArticuloVenta {
    private Integer idarticulo;
    private String nombre;
    private int stock;
    private double precio_venta;

    public ArticuloVenta() {
    }

    public ArticuloVenta(Integer idarticulo, String nombre, int stock, double precio_venta) {
        this.idarticulo = idarticulo;
        this.nombre = nombre;
        this.stock = stock;
        this.precio_venta = precio_venta;
    }
    

    public Integer getIdarticulo() {
        return idarticulo;
    }

    public void setIdarticulo(Integer idarticulo) {
        this.idarticulo = idarticulo;
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

    public double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precio_venta = precio_venta;
    }
    
    
    
    
}
