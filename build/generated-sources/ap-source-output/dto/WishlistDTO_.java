package dto;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2026-03-13T14:20:34")
@StaticMetamodel(WishlistDTO.class)
public class WishlistDTO_ { 

    public static volatile SingularAttribute<WishlistDTO, String> accountId;
    public static volatile SingularAttribute<WishlistDTO, Timestamp> createdAt;
    public static volatile SingularAttribute<WishlistDTO, String> productId;
    public static volatile SingularAttribute<WishlistDTO, Boolean> isDeleted;
    public static volatile SingularAttribute<WishlistDTO, String> id;

}