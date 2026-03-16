package models;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import utils.JPAUtils;

public class OrderItemDAO {

    public boolean create(OrderItemDTO item) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(item);
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

    public List<OrderItemDTO> getAllOrderItems() {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT oi FROM OrderItemDTO oi "
                    + "WHERE oi.isDeleted = false";
            TypedQuery<OrderItemDTO> query = em.createQuery(jpql, OrderItemDTO.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<OrderItemDTO> getOrderItemsByOrderId(String orderId) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT oi FROM OrderItemDTO oi "
                    + "WHERE oi.orderId = :orderId AND oi.isDeleted = false";
            TypedQuery<OrderItemDTO> query = em.createQuery(jpql, OrderItemDTO.class);
            query.setParameter("orderId", orderId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public OrderItemDTO getOrderItemById(String id) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            OrderItemDTO item = em.find(OrderItemDTO.class, id);
            if (item != null && !item.isIsDeleted()) {
                return item;
            }
            return null;
        } finally {
            em.close();
        }
    }

    public boolean update(OrderItemDTO item) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(item);
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
            OrderItemDTO item = em.find(OrderItemDTO.class, id);
            if (item != null) {
                item.setIsDeleted(true);
                em.merge(item);
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