package models;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import utils.IdGenerator;
import utils.JPAUtils;

public class OrderDAO {

    public boolean create(OrderDTO order) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();

            if (order.getId() == null || order.getId().trim().isEmpty()) {
                order.setId(IdGenerator.nextOrderId(em));
            }

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

    public boolean createOrderWithItems(OrderDTO order, List<OrderItemDTO> orderItems) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();

            if (order.getId() == null || order.getId().trim().isEmpty()) {
                order.setId(IdGenerator.nextOrderId(em));
            }

            em.persist(order);

            if (orderItems != null) {
                for (OrderItemDTO item : orderItems) {
                    if (item.getId() == null || item.getId().trim().isEmpty()) {
                        item.setId(IdGenerator.nextOrderItemId(em));
                    }
                    item.setOrderId(order.getId());
                    em.persist(item);
                }
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

    public List<OrderDTO> getOrdersByAccountId(String accountId) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            if (accountId == null || accountId.trim().isEmpty()) {
                return new ArrayList<OrderDTO>();
            }

            String jpql = "SELECT o FROM OrderDTO o "
                    + "WHERE o.accountId = :accountId "
                    + "AND o.isDeleted = false "
                    + "ORDER BY o.createdDate DESC";

            TypedQuery<OrderDTO> query = em.createQuery(jpql, OrderDTO.class);
            query.setParameter("accountId", accountId);
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

    public OrderDTO getOrderByIdAndAccountId(String orderId, String accountId) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT o FROM OrderDTO o "
                    + "WHERE o.id = :orderId "
                    + "AND o.accountId = :accountId "
                    + "AND o.isDeleted = false";

            TypedQuery<OrderDTO> query = em.createQuery(jpql, OrderDTO.class);
            query.setParameter("orderId", orderId);
            query.setParameter("accountId", accountId);

            return query.getSingleResult();
        } catch (NoResultException e) {
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

    public String approveOrder(String orderId) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();

            OrderDTO order = em.find(OrderDTO.class, orderId);
            if (order == null || order.isIsDeleted()) {
                em.getTransaction().rollback();
                return "Đơn hàng không tồn tại.";
            }

            if (!"PENDING".equalsIgnoreCase(order.getStatus())) {
                em.getTransaction().rollback();
                return "Chỉ có thể duyệt đơn đang ở trạng thái PENDING.";
            }

            String jpql = "SELECT oi FROM OrderItemDTO oi "
                    + "WHERE oi.orderId = :orderId AND oi.isDeleted = false";
            TypedQuery<OrderItemDTO> query = em.createQuery(jpql, OrderItemDTO.class);
            query.setParameter("orderId", orderId);
            List<OrderItemDTO> orderItems = query.getResultList();

            if (orderItems == null || orderItems.isEmpty()) {
                em.getTransaction().rollback();
                return "Đơn hàng chưa có sản phẩm, không thể duyệt.";
            }

            for (OrderItemDTO item : orderItems) {
                ProductDTO product = em.find(ProductDTO.class, item.getProductId());

                if (product == null || product.isIsDeleted()) {
                    em.getTransaction().rollback();
                    return "Có sản phẩm trong đơn không còn tồn tại.";
                }

                if (item.getQuantity() <= 0) {
                    em.getTransaction().rollback();
                    return "Có sản phẩm trong đơn có số lượng không hợp lệ.";
                }

                if (product.getStockQuantity() < item.getQuantity()) {
                    em.getTransaction().rollback();
                    return "Sản phẩm " + product.getName()
                            + " không đủ tồn kho. Còn " + product.getStockQuantity()
                            + ", cần " + item.getQuantity() + ".";
                }
            }

            for (OrderItemDTO item : orderItems) {
                ProductDTO product = em.find(ProductDTO.class, item.getProductId());
                product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
                em.merge(product);
            }

            order.setStatus("APPROVED");
            em.merge(order);

            em.getTransaction().commit();
            return null;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return "Có lỗi xảy ra khi duyệt đơn hàng.";
        } finally {
            em.close();
        }
    }

    public String cancelOrder(String orderId) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();

            OrderDTO order = em.find(OrderDTO.class, orderId);
            if (order == null || order.isIsDeleted()) {
                em.getTransaction().rollback();
                return "Đơn hàng không tồn tại.";
            }

            if (!"PENDING".equalsIgnoreCase(order.getStatus())) {
                em.getTransaction().rollback();
                return "Chỉ có thể hủy đơn đang ở trạng thái PENDING.";
            }

            order.setStatus("CANCELLED");
            em.merge(order);

            em.getTransaction().commit();
            return null;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return "Có lỗi xảy ra khi hủy đơn hàng.";
        } finally {
            em.close();
        }
    }

    public boolean hasPurchasedApprovedProduct(String accountId, String productId) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            if (accountId == null || accountId.trim().isEmpty()
                    || productId == null || productId.trim().isEmpty()) {
                return false;
            }

            String jpql = "SELECT COUNT(oi) FROM OrderItemDTO oi, OrderDTO o "
                    + "WHERE oi.orderId = o.id "
                    + "AND o.accountId = :accountId "
                    + "AND oi.productId = :productId "
                    + "AND o.status = 'APPROVED' "
                    + "AND o.isDeleted = false "
                    + "AND oi.isDeleted = false";

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
