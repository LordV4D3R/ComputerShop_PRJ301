package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "product")
public class ProductDTO implements Serializable {

    @Id
    private String id;

    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "name", columnDefinition = "NVARCHAR(255)")
    private String name;
    @Column(name = "brand", columnDefinition = "NVARCHAR(255)")
    private String brand;
    @Column(name = "cpu", columnDefinition = "NVARCHAR(255)")
    private String cpu;
    @Column(name = "ram", columnDefinition = "NVARCHAR(255)")
    private String ram;
    @Column(name = "storage", columnDefinition = "NVARCHAR(255)")
    private String storage;
    private double price;

    @Column(name = "stock_quantity")
    private int stockQuantity;
    @Column(name = "description", columnDefinition = "NVARCHAR(255)")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @PrePersist
    protected void onCreate() {
        this.isDeleted = false;
    }

    public ProductDTO() {
    }

    public ProductDTO(String categoryId, String name, String brand, String cpu, String ram, String storage, double price, int stockQuantity, String description, String imageUrl) {
        this.categoryId = categoryId;
        this.name = name;
        this.brand = brand;
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "ProductDTO{" + "id=" + id + ", name=" + name + ", brand=" + brand + ", price=" + price + ", isDeleted=" + isDeleted + '}';
    }
}
