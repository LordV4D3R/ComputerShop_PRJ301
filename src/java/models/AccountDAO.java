package models;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import utils.IdGenerator;
import utils.JPAUtils;
import utils.PasswordUtils;

public class AccountDAO {

    private static final String HARD_CODED_ADMIN_ID = "ADMIN-IN-MEMORY";
    private static final String HARD_CODED_ADMIN_USERNAME = "admin";
    private static final String HARD_CODED_ADMIN_PASSWORD = "admin123";

    public boolean create(AccountDTO account) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();

            if (account.getId() == null || account.getId().trim().isEmpty()) {
                account.setId(IdGenerator.nextAccountId(em, account.getRole()));
            }

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
        if (isHardcodedAdminUsername(username)) {
            return buildHardcodedAdmin();
        }

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

    public AccountDTO checkLogin(String username, String hashedPassword) {
        if (isHardcodedAdminLogin(username, hashedPassword)) {
            return buildHardcodedAdmin();
        }

        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT a FROM AccountDTO a "
                    + "WHERE a.username = :username "
                    + "AND a.hashPassword = :hashedPassword "
                    + "AND a.status = :status "
                    + "AND a.isDeleted = false";

            TypedQuery<AccountDTO> query = em.createQuery(jpql, AccountDTO.class);
            query.setParameter("username", username);
            query.setParameter("hashedPassword", hashedPassword);
            query.setParameter("status", "ACTIVE");

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

    private boolean isHardcodedAdminUsername(String username) {
        return HARD_CODED_ADMIN_USERNAME.equalsIgnoreCase(trim(username));
    }

    private boolean isHardcodedAdminLogin(String username, String hashedPassword) {
        return isHardcodedAdminUsername(username)
                && PasswordUtils.hashPassword(HARD_CODED_ADMIN_PASSWORD).equals(hashedPassword);
    }

    private AccountDTO buildHardcodedAdmin() {
        AccountDTO account = new AccountDTO(
                HARD_CODED_ADMIN_USERNAME,
                PasswordUtils.hashPassword(HARD_CODED_ADMIN_PASSWORD),
                "",
                "",
                "System Admin",
                "admin@local",
                "ADMIN",
                "",
                "ACTIVE"
        );

        account.setId(HARD_CODED_ADMIN_ID);
        account.setIsDeleted(false);
        return account;
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
