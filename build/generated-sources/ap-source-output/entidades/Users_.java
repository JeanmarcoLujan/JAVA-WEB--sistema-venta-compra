package entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-14T10:53:25")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> firstName;
    public static volatile SingularAttribute<Users, String> lastName;
    public static volatile SingularAttribute<Users, Date> createdAt;
    public static volatile SingularAttribute<Users, String> password;
    public static volatile SingularAttribute<Users, String> foto;
    public static volatile SingularAttribute<Users, Integer> id;
    public static volatile SingularAttribute<Users, String> rememberToken;
    public static volatile SingularAttribute<Users, String> email;
    public static volatile SingularAttribute<Users, Date> updatedAt;

}