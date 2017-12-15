package entidades;

import entidades.Categorias;
import entidades.DetalleIngresos;
import entidades.DetalleVentas;
import entidades.Units;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-14T10:53:25")
@StaticMetamodel(Articulos.class)
public class Articulos_ { 

    public static volatile SingularAttribute<Articulos, String> descripcion;
    public static volatile ListAttribute<Articulos, DetalleIngresos> detalleIngresosList;
    public static volatile SingularAttribute<Articulos, String> codigo;
    public static volatile SingularAttribute<Articulos, String> estado;
    public static volatile ListAttribute<Articulos, DetalleVentas> detalleVentasList;
    public static volatile SingularAttribute<Articulos, String> imagen;
    public static volatile SingularAttribute<Articulos, Units> unitId;
    public static volatile SingularAttribute<Articulos, Categorias> idcategoria;
    public static volatile SingularAttribute<Articulos, Integer> stock;
    public static volatile SingularAttribute<Articulos, Integer> idarticulo;
    public static volatile SingularAttribute<Articulos, String> nombre;

}