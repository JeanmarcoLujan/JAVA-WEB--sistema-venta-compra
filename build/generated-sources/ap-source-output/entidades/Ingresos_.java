package entidades;

import entidades.DetalleIngresos;
import entidades.Personas;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-14T10:53:25")
@StaticMetamodel(Ingresos.class)
public class Ingresos_ { 

    public static volatile SingularAttribute<Ingresos, String> numComprobante;
    public static volatile SingularAttribute<Ingresos, Personas> idproveedor;
    public static volatile ListAttribute<Ingresos, DetalleIngresos> detalleIngresosList;
    public static volatile SingularAttribute<Ingresos, String> estado;
    public static volatile SingularAttribute<Ingresos, String> serieComprobante;
    public static volatile SingularAttribute<Ingresos, Date> fechaHora;
    public static volatile SingularAttribute<Ingresos, String> tipoComprobante;
    public static volatile SingularAttribute<Ingresos, Integer> idingreso;

}