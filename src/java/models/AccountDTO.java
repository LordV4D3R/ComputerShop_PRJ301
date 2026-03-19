package models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class AccountDTO implements Serializable {

    @Id
    private String id;

    @Column(name = "username", nullable = false, unique = true, columnDefinition = "NVARCHAR(100)")
    private String username;

    @Column(name = "hash_password", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String hashPassword;

    @Column(name = "address", columnDefinition = "NVARCHAR(255)")
    private String address;

    @Column(name = "phone_number", columnDefinition = "NVARCHAR(50)")
    private String phoneNumber;

    @Column(name = "fullname", columnDefinition = "NVARCHAR(255)")
    private String fullname;

    @Column(name = "email", columnDefinition = "NVARCHAR(255)")
    private String email;

    @Column(name = "role", columnDefinition = "NVARCHAR(50)")
    private String role;

    @Column(name = "image_url", columnDefinition = "NVARCHAR(255)")
    private String imageUrl;

    @Column(name = "status", columnDefinition = "NVARCHAR(50)")
    private String status;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @PrePersist
    protected void onCreate() {
        if (this.status == null || this.status.trim().isEmpty()) {
            this.status = "ACTIVE";
        }
        this.isDeleted = false;
    }

    public AccountDTO() {
    }

    public AccountDTO(String username, String hashPassword, String address,
            String phoneNumber, String fullname, String email,
            String role, String imageUrl, String status) {
        this.username = username;
        this.hashPassword = hashPassword;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.fullname = fullname;
        this.email = email;
        this.role = role;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
