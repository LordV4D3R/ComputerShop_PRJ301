package dto;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2026-03-13T14:20:34")
@StaticMetamodel(ReviewDTO.class)
public class ReviewDTO_ { 

    public static volatile SingularAttribute<ReviewDTO, String> accountId;
    public static volatile SingularAttribute<ReviewDTO, Timestamp> createdAt;
    public static volatile SingularAttribute<ReviewDTO, String> productId;
    public static volatile SingularAttribute<ReviewDTO, Boolean> isDeleted;
    public static volatile SingularAttribute<ReviewDTO, Integer> rating;
    public static volatile SingularAttribute<ReviewDTO, String> comment;
    public static volatile SingularAttribute<ReviewDTO, String> id;

}