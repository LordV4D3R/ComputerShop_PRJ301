package models;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import utils.JPAUtils; 

public class ProductDAO {

    // 1. CREATE: Thêm sản phẩm mới
    public boolean create(ProductDTO product) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(product); // ID và isDeleted sẽ được @PrePersist tự động xử lý
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // 2. READ: Lấy danh sách TẤT CẢ sản phẩm (CHƯA BỊ XÓA)
    public List<ProductDTO> getAllProducts() {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            // Chỉ lấy những sản phẩm có isDeleted = false
            String jpql = "SELECT p FROM ProductDTO p WHERE p.isDeleted = false";
            TypedQuery<ProductDTO> query = em.createQuery(jpql, ProductDTO.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 3. READ: Tìm sản phẩm theo ID (Dùng khi xem chi tiết hoặc chuẩn bị edit)
    public ProductDTO getProductById(String id) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            ProductDTO product = em.find(ProductDTO.class, id);
            // Kiểm tra xem sản phẩm có tồn tại và chưa bị xóa hay không
            if (product != null && !product.isIsDeleted()) {
                return product;
            }
            return null;
        } finally {
            em.close();
        }
    }

    // 4. READ: Tìm kiếm sản phẩm theo tên (Có phân biệt chưa bị xóa)
    public List<ProductDTO> searchByName(String keyword) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT p FROM ProductDTO p WHERE p.name LIKE :name AND p.isDeleted = false";
            TypedQuery<ProductDTO> query = em.createQuery(jpql, ProductDTO.class);
            query.setParameter("name", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 5. UPDATE: Cập nhật thông tin sản phẩm
    public boolean update(ProductDTO product) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(product); 
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // 6. DELETE (SOFT DELETE): Xóa mềm sản phẩm bằng cách set isDeleted = true
    public boolean delete(String id) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            // Tìm sản phẩm cần xóa
            ProductDTO product = em.find(ProductDTO.class, id);
            if (product != null) {
                // Đổi trạng thái thành đã xóa thay vì dùng em.remove()
                product.setIsDeleted(true);
                em.merge(product); 
            }
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}