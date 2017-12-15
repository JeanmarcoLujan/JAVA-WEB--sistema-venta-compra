package entidades;

import entidades.Articulos;
import entidades.Ingresos;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-14T10:53:25")
@StaticMetamodel(DetalleIngresos.class)
public class DetalleIngresos_ { 

    public static volatile SingularAttribute<DetalleIngresos, Integer> iddetalleIngreso;
    public static volatile SingularAttribute<DetalleIngresos, Long> precioCompra;
    public static volatile SingularAttribute<DetalleIngresos, Integer> cantidad;
    public static volatile SingularAttribute<DetalleIngresos, Long> precioVenta;
    public static volatile SingularAttribute<DetalleIngresos, Ingresos> idingreso;
    public static volatile SingularAttribute<DetalleIngresos, Articulos> idarticulo;

}