package dao;

import dto.WishlistDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import utils.JPAUtils;

public class WishlistDAO {

    public boolean create(WishlistDTO wishlist) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
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