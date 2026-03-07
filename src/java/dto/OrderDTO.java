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
@Table(name = "orders")
public class OrderDTO implements Serializable {

    @Id
    private String id;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "address", columnDefinition = "NVARCHAR(255)")
    private String address;

    @Column(name = "phone_number", columnDefinition = "NVARCHAR(50)")
    private String phoneNumber;

    @Column(name = "fullname", columnDefinition = "NVARCHAR(255)")
    private String fullname;

    @Column(name = "email")
    private String email;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "status", columnDefinition = "NVARCHAR(50)")
    private String status;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @PrePersist
    protected void onCreate() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUID.randomUUID().toString();
        }
        if (this.createdDate == null) {
            this.createdDate = new Timestamp(System.currentTimeMillis());
        }
        this.isDeleted = false;
    }

    public OrderDTO() {
    }

    public OrderDTO(String address, String phoneNumber, String fullname,
            String email, double totalPrice, String status) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.fullname = fullname;
        this.email = email;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public OrderDTO(String accountId, String address, String phoneNumber,
            String fullname, String email, double totalPrice, String status) {
        this.accountId = accountId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.fullname = fullname;
        this.email = email;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "OrderDTO{"
                + "id=" + id
                + ", fullname=" + fullname
                + ", phoneNumber=" + phoneNumber
                + ", totalPrice=" + totalPrice
                + ", status=" + status
                + ", isDeleted=" + isDeleted
                + '}';
    }
}