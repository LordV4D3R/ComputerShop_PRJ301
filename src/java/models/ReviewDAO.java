package models;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import utils.JPAUtils;

public class ReviewDAO {

    public boolean create(ReviewDTO review) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(review);
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

    public List<ReviewDTO> getAllReviews() {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT r FROM ReviewDTO r "
                    + "WHERE r.isDeleted = false "
                    + "ORDER BY r.createdAt DESC";
            TypedQuery<ReviewDTO> query = em.createQuery(jpql, ReviewDTO.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public ReviewDTO getReviewById(String id) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            ReviewDTO review = em.find(ReviewDTO.class, id);
            if (review != null && !review.isIsDeleted()) {
                return review;
            }
            return null;
        } finally {
            em.close();
        }
    }

    public boolean update(ReviewDTO review) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(review);
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

    public boolean delete(String id) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            ReviewDTO review = em.find(ReviewDTO.class, id);
            if (review != null) {
                review.setIsDeleted(true);
                em.merge(review);
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

    public List<ReviewDTO> getReviewsByProductId(String productId) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT r FROM ReviewDTO r "
                    + "WHERE r.productId = :productId "
                    + "AND r.isDeleted = false "
                    + "ORDER BY r.createdAt DESC";
            TypedQuery<ReviewDTO> query = em.createQuery(jpql, ReviewDTO.class);
            query.setParameter("productId", productId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public boolean hasReviewByAccountAndProduct(String accountId, String productId) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT COUNT(r) FROM ReviewDTO r "
                    + "WHERE r.accountId = :accountId "
                    + "AND r.productId = :productId "
                    + "AND r.isDeleted = false";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("accountId", accountId);
            query.setParameter("productId", productId);
            Long count = query.getSingleResult();
            return count != null && count > 0;
        } finally {
            em.close();
        }
    }
}
