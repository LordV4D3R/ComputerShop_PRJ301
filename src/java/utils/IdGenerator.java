package utils;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public final class IdGenerator {

    private IdGenerator() {
    }

    public static String nextCategoryId(EntityManager em, String categoryName) {
        String baseCode = buildCategoryCode(categoryName);
        String candidate = "CAT_" + baseCode;

        Long count = ((Number) em.createNativeQuery(
                "SELECT COUNT(*) FROM category WHERE id = ?")
                .setParameter(1, candidate)
                .getSingleResult()).longValue();

        if (count == 0) {
            return candidate;
        }

        int index = 2;
        while (true) {
            String nextCandidate = candidate + "_" + String.format("%02d", index);
            Long nextCount = ((Number) em.createNativeQuery(
                    "SELECT COUNT(*) FROM category WHERE id = ?")
                    .setParameter(1, nextCandidate)
                    .getSingleResult()).longValue();

            if (nextCount == 0) {
                return nextCandidate;
            }
            index++;
        }
    }

    public static String nextAccountId(EntityManager em, String role) {
        String prefix;
        if (role == null) {
            prefix = "ACC_CUS_";
        } else {
            switch (role.trim().toUpperCase()) {
                case "ADMIN":
                    prefix = "ACC_ADMIN_";
                    break;
                case "STAFF":
                    prefix = "ACC_STAFF_";
                    break;
                default:
                    prefix = "ACC_CUS_";
                    break;
            }
        }
        return nextNumericId(em, "account", prefix, 3);
    }

    public static String nextProductId(EntityManager em) {
        return nextNumericId(em, "product", "PROD_", 3);
    }

    public static String nextOrderId(EntityManager em) {
        return nextNumericId(em, "orders", "ORD_", 3);
    }

    public static String nextOrderItemId(EntityManager em) {
        return nextNumericId(em, "order_items", "ITM_", 3);
    }

    public static String nextReviewId(EntityManager em) {
        return nextNumericId(em, "review", "REV_", 3);
    }

    public static String nextWishlistId(EntityManager em) {
        return nextNumericId(em, "wishlist", "WISH_", 3);
    }

    private static String nextNumericId(EntityManager em, String tableName, String prefix, int padLength) {
        String sql = "SELECT TOP 1 id FROM " + tableName + " WHERE id LIKE ? ORDER BY id DESC";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, prefix + "%");

        @SuppressWarnings("unchecked")
        List<Object> results = query.getResultList();

        int nextNumber = 1;
        if (!results.isEmpty() && results.get(0) != null) {
            String lastId = results.get(0).toString();
            nextNumber = extractNumber(lastId, prefix) + 1;
        }

        return prefix + String.format("%0" + padLength + "d", nextNumber);
    }

    private static int extractNumber(String id, String prefix) {
        if (id == null || !id.startsWith(prefix)) {
            return 0;
        }

        String numberPart = id.substring(prefix.length()).replaceAll("[^0-9]", "");
        if (numberPart.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(numberPart);
    }

    private static String buildCategoryCode(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "GEN";
        }

        String normalized = removeAccent(name).toUpperCase(Locale.ROOT).trim();

        switch (normalized) {
            case "LAPTOP":
                return "LAP";
            case "PC GAMING":
                return "PC";
            case "MAN HINH":
                return "MON";
            case "BAN PHIM":
                return "KEY";
            case "CHUOT":
                return "MOU";
            case "TAI NGHE":
                return "HP";
            default:
                String[] words = normalized.split("\\s+");
                StringBuilder sb = new StringBuilder();

                for (String word : words) {
                    if (!word.isEmpty()) {
                        sb.append(word.charAt(0));
                    }
                    if (sb.length() == 3) {
                        break;
                    }
                }

                while (sb.length() < 3) {
                    sb.append('X');
                }

                return sb.toString();
        }
    }

    private static String removeAccent(String value) {
        String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
        temp = temp.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        temp = temp.replace('Đ', 'D').replace('đ', 'd');
        return temp;
    }
}