package models;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import utils.IdGenerator;
import utils.JPAUtils;

public class WishlistDAO {

    public boolean create(WishlistDTO wishlist) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();

            if (wishlist.getId() == null || wishlist.getId().trim().isEmpty()) {
                wishlist.setId(IdGenerator.nextWishlistId(em));
            }

            em.persist(wishlist);
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

    public List<WishlistDTO> getAllWishlists() {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT w FROM WishlistDTO w "
                    + "WHERE w.isDeleted = false "
                    + "ORDER BY w.createdAt DESC";
            TypedQuery<WishlistDTO> query = em.createQuery(jpql, WishlistDTO.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public WishlistDTO getWishlistById(String id) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            WishlistDTO wishlist = em.find(WishlistDTO.class, id);
            if (wishlist != null && !wishlist.isIsDeleted()) {
                return wishlist;
            }
            return null;
        } finally {
            em.close();
        }
    }

    public WishlistDTO getActiveWishlist(String accountId, String productId) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT w FROM WishlistDTO w "
                    + "WHERE w.accountId = :accountId "
                    + "AND w.productId = :productId "
                    + "AND w.isDeleted = false";

            TypedQuery<WishlistDTO> query = em.createQuery(jpql, WishlistDTO.class);
            query.setParameter("accountId", accountId);
            query.setParameter("productId", productId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<WishlistDTO> getWishlistsByAccountId(String accountId) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT w FROM WishlistDTO w "
                    + "WHERE w.accountId = :accountId "
                    + "AND w.isDeleted = false "
                    + "ORDER BY w.createdAt DESC";

            TypedQuery<WishlistDTO> query = em.createQuery(jpql, WishlistDTO.class);
            query.setParameter("accountId", accountId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Set<String> getWishlistedProductIdsByAccountId(String accountId) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT w.productId FROM WishlistDTO w "
                    + "WHERE w.accountId = :accountId "
                    + "AND w.isDeleted = false";

            TypedQuery<String> query = em.createQuery(jpql, String.class);
            query.setParameter("accountId", accountId);
            return new LinkedHashSet<String>(query.getResultList());
        } finally {
            em.close();
        }
    }

    public boolean update(WishlistDTO wishlist) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(wishlist);
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
            WishlistDTO wishlist = em.find(WishlistDTO.class, id);
            if (wishlist != null) {
                wishlist.setIsDeleted(true);
                em.merge(wishlist);
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
