package dto;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2026-03-11T20:27:50")
@StaticMetamodel(OrderItemDTO.class)
public class OrderItemDTO_ { 

    public static volatile SingularAttribute<OrderItemDTO, Integer> quantity;
    public static volatile SingularAttribute<OrderItemDTO, String> productId;
    public static volatile SingularAttribute<OrderItemDTO, Boolean> isDeleted;
    public static volatile SingularAttribute<OrderItemDTO, String> orderId;
    public static volatile SingularAttribute<OrderItemDTO, Double> price;
    public static volatile SingularAttribute<OrderItemDTO, String> id;

}