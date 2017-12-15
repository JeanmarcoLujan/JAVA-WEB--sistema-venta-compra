package entidades;

import entidades.Articulos;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-14T10:53:25")
@StaticMetamodel(Categorias.class)
public class Categorias_ { 

    public static volatile SingularAttribute<Categorias, String> descripcion;
    public static volatile ListAttribute<Categorias, Articulos> articulosList;
    public static volatile SingularAttribute<Categorias, Integer> idcategoria;
    public static volatile SingularAttribute<Categorias, String> nombre;
    public static volatile SingularAttribute<Categorias, Boolean> condicion;

}