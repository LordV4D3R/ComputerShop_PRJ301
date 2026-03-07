package dao;

import dto.OrderDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import utils.JPAUtils;

public class OrderDAO {

    public boolean create(OrderDTO order) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(order);
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

    public List<OrderDTO> getAllOrders() {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT o FROM OrderDTO o "
                    + "WHERE o.isDeleted = false "
                    + "ORDER BY o.createdDate DESC";
            TypedQuery<OrderDTO> query = em.createQuery(jpql, OrderDTO.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public OrderDTO getOrderById(String id) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            OrderDTO order = em.find(OrderDTO.class, id);
            if (order != null && !order.isIsDeleted()) {
                return order;
            }
            return null;
        } finally {
            em.close();
        }
    }

    public boolean update(OrderDTO order) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(order);
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
            OrderDTO order = em.find(OrderDTO.class, id);
            if (order != null) {
                order.setIsDeleted(true);
                em.merge(order);
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