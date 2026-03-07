package dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "review")
public class ReviewDTO implements Serializable {

    @Id
    private String id;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "rating")
    private int rating;

    @Column(name = "comment", columnDefinition = "NVARCHAR(1000)")
    private String comment;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @PrePersist
    protected void onCreate() {
        if (this.id == null || this.id.trim().isEmpty()) {
            this.id = UUID.randomUUID().toString();
        }
        if (this.createdAt == null) {
            this.createdAt = new Timestamp(System.currentTimeMillis());
        }
        this.isDeleted = false;
    }

    public ReviewDTO() {
    }

    public ReviewDTO(String productId, String accountId, int rating, String comment) {
        this.productId = productId;
        this.accountId = accountId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}