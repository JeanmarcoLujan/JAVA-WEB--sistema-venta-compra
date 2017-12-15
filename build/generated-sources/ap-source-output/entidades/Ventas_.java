package entidades;

import entidades.DetalleVentas;
import entidades.Personas;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-14T10:53:25")
@StaticMetamodel(Ventas.class)
public class Ventas_ { 

    public static volatile SingularAttribute<Ventas, Integer> idventa;
    public static volatile SingularAttribute<Ventas, String> numComprobante;
    public static volatile SingularAttribute<Ventas, String> estado;
    public static volatile ListAttribute<Ventas, DetalleVentas> detalleVentasList;
    public static volatile SingularAttribute<Ventas, String> serieComprobante;
    public static volatile SingularAttribute<Ventas, Date> fechaHora;
    public static volatile SingularAttribute<Ventas, String> tipoComprobante;
    public static volatile SingularAttribute<Ventas, Personas> idcliente;

}