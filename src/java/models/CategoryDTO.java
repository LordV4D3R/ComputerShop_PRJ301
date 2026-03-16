package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "category")
public class CategoryDTO implements Serializable {

    @Id
    private String id;

@Column(name="name", columnDefinition="NVARCHAR(255)")
private String name;

@Column(name="description", columnDefinition="NVARCHAR(255)")
    private String description;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @PrePersist
    protected void onCreate() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUID.randomUUID().toString();
        }
        this.isDeleted = false;
    }

    public CategoryDTO() {
    }

    public CategoryDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // getters/setters (giữ style giống ProductDTO)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isIsDeleted() { return isDeleted; }
    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }

    @Override
    public String toString() {
        return "CategoryDTO{" + "id=" + id + ", name=" + name + ", isDeleted=" + isDeleted + '}';
    }
}