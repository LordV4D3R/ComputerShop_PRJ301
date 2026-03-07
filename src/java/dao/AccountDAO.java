package dao;

import dto.AccountDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import utils.JPAUtils;

public class AccountDAO {

    public boolean create(AccountDTO account) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(account);
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

    public List<AccountDTO> getAllAccounts() {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT a FROM AccountDTO a "
                    + "WHERE a.isDeleted = false "
                    + "ORDER BY a.username ASC";
            TypedQuery<AccountDTO> query = em.createQuery(jpql, AccountDTO.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public AccountDTO getAccountById(String id) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            AccountDTO account = em.find(AccountDTO.class, id);
            if (account != null && !account.isIsDeleted()) {
                return account;
            }
            return null;
        } finally {
            em.close();
        }
    }

    public AccountDTO getAccountByUsername(String username) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT a FROM AccountDTO a "
                    + "WHERE a.username = :username AND a.isDeleted = false";
            TypedQuery<AccountDTO> query = em.createQuery(jpql, AccountDTO.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean update(AccountDTO account) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(account);
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
            AccountDTO account = em.find(AccountDTO.class, id);
            if (account != null) {
                account.setIsDeleted(true);
                em.merge(account);
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