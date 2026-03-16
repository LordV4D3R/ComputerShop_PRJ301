package models;

public class CartItemViewDTO {

    private ProductDTO product;
    private int quantity;
    private double subtotal;

    public CartItemViewDTO() {
    }

    public CartItemViewDTO(ProductDTO product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        if (product != null) {
            this.subtotal = product.getPrice() * quantity;
        }
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}