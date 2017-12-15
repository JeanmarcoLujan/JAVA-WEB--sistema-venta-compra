package entidades;

import entidades.Ingresos;
import entidades.Ventas;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-14T10:53:25")
@StaticMetamodel(Personas.class)
public class Personas_ { 

    public static volatile SingularAttribute<Personas, String> tipoDocumento;
    public static volatile SingularAttribute<Personas, String> numDocumento;
    public static volatile SingularAttribute<Personas, String> tipoPersona;
    public static volatile SingularAttribute<Personas, Integer> idpersona;
    public static volatile SingularAttribute<Personas, String> direccion;
    public static volatile ListAttribute<Personas, Ventas> ventasList;
    public static volatile SingularAttribute<Personas, String> telefono;
    public static volatile SingularAttribute<Personas, String> nombre;
    public static volatile ListAttribute<Personas, Ingresos> ingresosList;
    public static volatile SingularAttribute<Personas, String> email;

}