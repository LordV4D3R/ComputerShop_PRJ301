package models;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import utils.IdGenerator;
import utils.JPAUtils;

public class CategoryDAO {

    // 1. CREATE
    public boolean create(CategoryDTO category) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();

            if (category.getId() == null || category.getId().trim().isEmpty()) {
                category.setId(IdGenerator.nextCategoryId(em, category.getName()));
            }

            em.persist(category);
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

    // 2. READ ALL (chưa bị xóa)
    public List<CategoryDTO> getAllCategories() {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT c FROM CategoryDTO c WHERE c.isDeleted = false ORDER BY c.name";
            TypedQuery<CategoryDTO> q = em.createQuery(jpql, CategoryDTO.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    // 3. READ BY ID
    public CategoryDTO getCategoryById(String id) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            CategoryDTO c = em.find(CategoryDTO.class, id);
            if (c != null && !c.isIsDeleted()) {
                return c;
            }
            return null;
        } finally {
            em.close();
        }
    }

    // 4. SEARCH BY NAME
    public List<CategoryDTO> searchByName(String keyword) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT c FROM CategoryDTO c WHERE c.name LIKE :name AND c.isDeleted = false";
            TypedQuery<CategoryDTO> q = em.createQuery(jpql, CategoryDTO.class);
            q.setParameter("name", "%" + keyword + "%");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    // 5. UPDATE
    public boolean update(CategoryDTO category) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(category);
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

    // 6. SOFT DELETE
    public boolean delete(String id) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            CategoryDTO c = em.find(CategoryDTO.class, id);
            if (c != null) {
                c.setIsDeleted(true);
                em.merge(c);
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
