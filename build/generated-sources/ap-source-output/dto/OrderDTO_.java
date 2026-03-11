package dto;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2026-03-11T20:27:50")
@StaticMetamodel(OrderDTO.class)
public class OrderDTO_ { 

    public static volatile SingularAttribute<OrderDTO, String> accountId;
    public static volatile SingularAttribute<OrderDTO, String> address;
    public static volatile SingularAttribute<OrderDTO, String> phoneNumber;
    public static volatile SingularAttribute<OrderDTO, Timestamp> createdDate;
    public static volatile SingularAttribute<OrderDTO, Boolean> isDeleted;
    public static volatile SingularAttribute<OrderDTO, Double> totalPrice;
    public static volatile SingularAttribute<OrderDTO, String> id;
    public static volatile SingularAttribute<OrderDTO, String> fullname;
    public static volatile SingularAttribute<OrderDTO, String> email;
    public static volatile SingularAttribute<OrderDTO, String> status;

}