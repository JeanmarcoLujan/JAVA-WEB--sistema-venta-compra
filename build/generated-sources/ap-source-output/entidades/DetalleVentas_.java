package entidades;

import entidades.Articulos;
import entidades.Ventas;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-14T10:53:25")
@StaticMetamodel(DetalleVentas.class)
public class DetalleVentas_ { 

    public static volatile SingularAttribute<DetalleVentas, Ventas> idventa;
    public static volatile SingularAttribute<DetalleVentas, BigDecimal> descuento;
    public static volatile SingularAttribute<DetalleVentas, Integer> cantidad;
    public static volatile SingularAttribute<DetalleVentas, BigDecimal> precioVenta;
    public static volatile SingularAttribute<DetalleVentas, Articulos> idarticulo;
    public static volatile SingularAttribute<DetalleVentas, Integer> iddetalleVenta;

}