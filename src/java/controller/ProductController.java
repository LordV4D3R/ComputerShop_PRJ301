package controller;

import models.OrderDAO;
import models.ReviewDAO;
import models.ReviewDTO;
import java.io.File;
import models.ProductDAO;
import models.CartItemViewDTO;
import models.ProductDTO;
import utils.AppConstants;
import models.CategoryDTO;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import utils.JPAUtils;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import models.AccountDTO;
import models.WishlistDAO;
import models.WishlistDTO;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ProductController extends HttpServlet {

    private static final String CART_COOKIE_NAME = "cart";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String url = AppConstants.PRODUCT_LIST_PAGE;

        if (action == null || action.trim().isEmpty()) {
            action = AppConstants.ACTION_SHOW_HOME;
        }
        try {
            ProductDAO productDAO = new ProductDAO();

            if (AppConstants.ACTION_SHOW_HOME.equals(action)) {
                List<ProductDTO> listProduct = productDAO.getAllProducts();

                if (listProduct.size() > 8) {
                    listProduct = new ArrayList<ProductDTO>(listProduct.subList(0, 8));
                }

                request.setAttribute("listProduct", listProduct);
                loadWishlistState(request, listProduct);
                loadProductRatings(request, listProduct);
                url = AppConstants.HOME_PAGE;

            } else if (AppConstants.ACTION_SHOW_SHOP.equals(action)) {
                String keyword = request.getParameter("keyword");
                List<ProductDTO> listProduct;

                if (keyword != null && !keyword.trim().isEmpty()) {
                    listProduct = productDAO.searchByName(keyword.trim());
                } else {
                    listProduct = productDAO.getAllProducts();
                }

                request.setAttribute("listProduct", listProduct);
                loadWishlistState(request, listProduct);
                loadProductRatings(request, listProduct);
                url = AppConstants.CLIENT_SHOP_PAGE;

            } else if (AppConstants.ACTION_SHOW_PRODUCT_DETAIL.equals(action)) {
                String id = request.getParameter("id");
                ProductDTO product = productDAO.getProductById(id);

                if (product == null) {
                    request.getSession().setAttribute("errorMessage", "Sản phẩm không tồn tại hoặc đã bị xóa.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_SHOP);
                    return;
                }

                ReviewDAO reviewDAO = new ReviewDAO();
                List<ReviewDTO> listReview = reviewDAO.getReviewsByProductId(id);
                double averageRating = calculateAverageRating(listReview);
                int reviewCount = listReview.size();
                Map<String, String> reviewerNameMap = getReviewerNameMap(listReview);
                boolean canReview = false;
                boolean reviewedAlready = false;

                AccountDTO loginUser = getLoginUser(request);
                if (isCustomer(loginUser)) {
                    OrderDAO orderDAO = new OrderDAO();
                    canReview = orderDAO.hasPurchasedApprovedProduct(loginUser.getId(), id);
                    reviewedAlready = reviewDAO.hasReviewByAccountAndProduct(loginUser.getId(), id);

                    if (reviewedAlready) {
                        canReview = false;
                    }
                }
                Map<String, Boolean> wishlistProductMap = new HashMap<String, Boolean>();
                if (isCustomer(loginUser)) {
                    WishlistDAO wishlistDAO = new WishlistDAO();
                    // Kiểm tra xem user này đã thả tim sản phẩm này chưa
                    WishlistDTO activeWishlist = wishlistDAO.getActiveWishlist(loginUser.getId(), product.getId());
                    wishlistProductMap.put(product.getId(), activeWishlist != null);
                } else {
                    wishlistProductMap.put(product.getId(), false);
                }
                request.setAttribute("wishlistProductMap", wishlistProductMap);
                request.setAttribute("product", product);
                request.setAttribute("categoryName", getCategoryNameById(product.getCategoryId()));
                request.setAttribute("listReview", listReview);
                request.setAttribute("canReview", canReview);
                request.setAttribute("reviewedAlready", reviewedAlready);
                request.setAttribute("averageRating", averageRating);
                request.setAttribute("reviewCount", reviewCount);
                url = AppConstants.PRODUCT_DETAIL_PAGE;

            } else if (AppConstants.ACTION_LIST_PRODUCT.equals(action)) {
                List<ProductDTO> listProduct = productDAO.getAllProducts();
                request.setAttribute("listProduct", listProduct);
                url = AppConstants.PRODUCT_LIST_PAGE;

            } else if (AppConstants.ACTION_ADD_TO_CART.equals(action)) {
                String productId = request.getParameter("productId");
                String redirect = request.getParameter("redirect");

                String redirectUrl = AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_SHOP;
                if ("detail".equals(redirect) && productId != null && !productId.trim().isEmpty()) {
                    redirectUrl = AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_PRODUCT_DETAIL + "&id=" + productId;
                }

                if (productId == null || productId.trim().isEmpty()) {
                    request.getSession().setAttribute("errorMessage", "Không tìm thấy sản phẩm để thêm vào giỏ.");
                    response.sendRedirect(redirectUrl);
                    return;
                }

                ProductDTO product = productDAO.getProductById(productId);

                if (product == null) {
                    request.getSession().setAttribute("errorMessage", "Sản phẩm không tồn tại hoặc đã bị xóa.");
                    response.sendRedirect(redirectUrl);
                    return;
                }

                if (product.getStockQuantity() <= 0) {
                    request.getSession().setAttribute("errorMessage", "Sản phẩm đã hết hàng.");
                    response.sendRedirect(redirectUrl);
                    return;
                }

                String cartValue = getCookieValue(request, CART_COOKIE_NAME);
                String newCartValue = addProductToCartCookie(cartValue, productId);

                Cookie cartCookie = new Cookie(CART_COOKIE_NAME, newCartValue);
                cartCookie.setMaxAge(7 * 24 * 60 * 60);
                cartCookie.setPath(request.getContextPath());
                response.addCookie(cartCookie);

                request.getSession().setAttribute("successMessage", "Đã thêm sản phẩm vào giỏ hàng thành công.");
                response.sendRedirect(redirectUrl);
                return;

            } else if (AppConstants.ACTION_SHOW_CART.equals(action)) {
                loadCartData(request, productDAO);
                url = AppConstants.CLIENT_CART_PAGE;

            } else if (AppConstants.ACTION_INCREASE_CART_ITEM.equals(action)) {
                String productId = request.getParameter("productId");
                String cartValue = getCookieValue(request, CART_COOKIE_NAME);

                Map<String, Integer> cartMap = parseCartCookie(cartValue);

                if (cartMap.containsKey(productId)) {
                    ProductDTO product = productDAO.getProductById(productId);

                    if (product == null) {
                        cartMap.remove(productId);
                        saveCartCookie(response, request, cartMap);
                        request.getSession().setAttribute("errorMessage", "Sản phẩm không còn tồn tại trong hệ thống.");
                    } else {
                        int oldQty = cartMap.get(productId);

                        if (oldQty >= product.getStockQuantity()) {
                            request.getSession().setAttribute("errorMessage", "Không thể tăng thêm. Số lượng đã đạt tồn kho hiện tại.");
                        } else {
                            cartMap.put(productId, oldQty + 1);
                            saveCartCookie(response, request, cartMap);
                            request.getSession().setAttribute("successMessage", "Đã tăng số lượng sản phẩm.");
                        }
                    }
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_CART);
                return;

            } else if (AppConstants.ACTION_DECREASE_CART_ITEM.equals(action)) {
                String productId = request.getParameter("productId");
                String cartValue = getCookieValue(request, CART_COOKIE_NAME);

                Map<String, Integer> cartMap = parseCartCookie(cartValue);

                if (cartMap.containsKey(productId)) {
                    int oldQty = cartMap.get(productId);

                    if (oldQty <= 1) {
                        cartMap.remove(productId);
                    } else {
                        cartMap.put(productId, oldQty - 1);
                    }

                    saveCartCookie(response, request, cartMap);
                    request.getSession().setAttribute("successMessage", "Đã giảm số lượng sản phẩm.");
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_CART);
                return;

            } else if (AppConstants.ACTION_REMOVE_FROM_CART.equals(action)) {
                String productId = request.getParameter("productId");
                String cartValue = getCookieValue(request, CART_COOKIE_NAME);

                Map<String, Integer> cartMap = parseCartCookie(cartValue);

                if (cartMap.containsKey(productId)) {
                    cartMap.remove(productId);
                    saveCartCookie(response, request, cartMap);
                    request.getSession().setAttribute("successMessage", "Đã xóa sản phẩm khỏi giỏ hàng.");
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_CART);
                return;

            } else if (AppConstants.ACTION_SHOW_CREATE_PRODUCT.equals(action)) {
                List<CategoryDTO> listCategory = getAllCategories();
                request.setAttribute("listCategory", listCategory);
                url = AppConstants.PRODUCT_CREATE_PAGE;

            } else if (AppConstants.ACTION_INSERT_PRODUCT.equals(action)) {
                String name = request.getParameter("name");
                String brand = request.getParameter("brand");
                String categoryId = request.getParameter("categoryId");
                String cpu = request.getParameter("cpu");
                String ram = request.getParameter("ram");
                String storage = request.getParameter("storage");
                double price = Double.parseDouble(request.getParameter("price"));
                int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
                String description = request.getParameter("description");

                String imageUrl = request.getParameter("oldImageUrl");
                if (imageUrl == null) {
                    imageUrl = "";
                }

                Part filePart = request.getPart("imageFile");
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String uniqueFileName =fileName;

                    String uploadPath = request.getServletContext().getRealPath("/images/products");
                    if (uploadPath == null) {
                        uploadPath = request.getServletContext().getRealPath("") + File.separator + "images" + File.separator + "products";
                    }

                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    filePart.write(uploadPath + File.separator + uniqueFileName);
                    imageUrl = "images/products/" + uniqueFileName;
                }

                ProductDTO newProduct = new ProductDTO(categoryId, name, brand, cpu, ram, storage, price, stockQuantity, description, imageUrl);
                productDAO.create(newProduct);

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_PRODUCT);
                return;

            } else if (AppConstants.ACTION_DELETE_PRODUCT.equals(action)) {
                String id = request.getParameter("id");
                if (id != null) {
                    productDAO.delete(id);
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_PRODUCT);
                return;

            } else if (AppConstants.ACTION_EDIT_PRODUCT.equals(action)) {
                String id = request.getParameter("id");
                ProductDTO product = productDAO.getProductById(id);

                if (product == null) {
                    request.setAttribute("error", "Product không tồn tại hoặc đã bị xóa.");
                    List<ProductDTO> listProduct = productDAO.getAllProducts();
                    request.setAttribute("listProduct", listProduct);
                    url = AppConstants.PRODUCT_LIST_PAGE;
                } else {
                    List<CategoryDTO> listCategory = getAllCategories();
                    request.setAttribute("listCategory", listCategory);
                    request.setAttribute("product", product);
                    url = AppConstants.PRODUCT_EDIT_PAGE;
                }

            } else if (AppConstants.ACTION_UPDATE_PRODUCT.equals(action)) {
                String id = request.getParameter("id");
                ProductDTO product = productDAO.getProductById(id);

                if (product != null) {
                    product.setName(request.getParameter("name"));
                    product.setBrand(request.getParameter("brand"));
                    product.setCategoryId(request.getParameter("categoryId"));
                    product.setCpu(request.getParameter("cpu"));
                    product.setRam(request.getParameter("ram"));
                    product.setStorage(request.getParameter("storage"));
                    product.setPrice(Double.parseDouble(request.getParameter("price")));
                    product.setStockQuantity(Integer.parseInt(request.getParameter("stockQuantity")));
                    product.setDescription(request.getParameter("description"));

                    // XỬ LÝ ẢNH CHO PHẦN UPDATE
                    String imageUrl = request.getParameter("oldImageUrl");
                    if (imageUrl == null) {
                        imageUrl = "";
                    }

                    Part filePart = request.getPart("imageFile");
                    if (filePart != null && filePart.getSize() > 0) {
                        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                        String uniqueFileName = fileName;

                        String uploadPath = request.getServletContext().getRealPath("/images/products");
                        if (uploadPath == null) {
                            uploadPath = request.getServletContext().getRealPath("") + File.separator + "images" + File.separator + "products";
                        }

                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }

                        filePart.write(uploadPath + File.separator + uniqueFileName);
                        imageUrl = "images/products/" + uniqueFileName;
                    }
                    product.setImageUrl(imageUrl);
                    // KẾT THÚC XỬ LÝ ẢNH

                    productDAO.update(product);
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_PRODUCT);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Có lỗi xảy ra khi xử lý sản phẩm.");
            response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_SHOP);
            return;
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    private void loadCartData(HttpServletRequest request, ProductDAO productDAO) {
        String cartValue = getCookieValue(request, CART_COOKIE_NAME);
        Map<String, Integer> cartMap = parseCartCookie(cartValue);

        List<CartItemViewDTO> cartItems = new ArrayList<>();
        double total = 0;

        for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();

            ProductDTO product = productDAO.getProductById(productId);
            if (product != null) {
                if (quantity > product.getStockQuantity()) {
                    quantity = product.getStockQuantity();
                }

                if (quantity > 0) {
                    CartItemViewDTO item = new CartItemViewDTO(product, quantity);
                    cartItems.add(item);
                    total += item.getSubtotal();
                }
            }
        }

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("cartTotal", total);
        request.setAttribute("cartSize", cartItems.size());
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "";
        }

        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";
    }

    private String addProductToCartCookie(String cartValue, String productId) {
        if (cartValue == null || cartValue.trim().isEmpty()) {
            return productId + ":1";
        }

        String[] items = cartValue.split("#");
        List<String> updatedItems = new ArrayList<>();
        boolean found = false;

        for (String item : items) {
            if (item == null || item.trim().isEmpty()) {
                continue;
            }

            String[] parts = item.split(":");
            if (parts.length != 2) {
                continue;
            }

            String oldProductId = parts[0].trim();
            int quantity;

            try {
                quantity = Integer.parseInt(parts[1].trim());
            } catch (NumberFormatException e) {
                continue;
            }

            if (oldProductId.equals(productId)) {
                quantity++;
                found = true;
            }

            updatedItems.add(oldProductId + ":" + quantity);
        }

        if (!found) {
            updatedItems.add(productId + ":1");
        }

        return String.join("#", updatedItems);
    }

    private Map<String, Integer> parseCartCookie(String cartValue) {
        Map<String, Integer> cartMap = new LinkedHashMap<>();

        if (cartValue == null || cartValue.trim().isEmpty()) {
            return cartMap;
        }

        String[] items = cartValue.split("#");
        for (String item : items) {
            if (item == null || item.trim().isEmpty()) {
                continue;
            }

            String[] parts = item.split(":");
            if (parts.length != 2) {
                continue;
            }

            String productId = parts[0].trim();
            int quantity;

            try {
                quantity = Integer.parseInt(parts[1].trim());
            } catch (NumberFormatException e) {
                continue;
            }

            if (quantity > 0) {
                cartMap.put(productId, quantity);
            }
        }

        return cartMap;
    }

    private void saveCartCookie(HttpServletResponse response, HttpServletRequest request, Map<String, Integer> cartMap) {
        if (cartMap == null || cartMap.isEmpty()) {
            Cookie cartCookie = new Cookie(CART_COOKIE_NAME, "");
            cartCookie.setMaxAge(0);
            cartCookie.setPath(request.getContextPath());
            response.addCookie(cartCookie);
            return;
        }

        List<String> items = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
            items.add(entry.getKey() + ":" + entry.getValue());
        }

        String newCartValue = String.join("#", items);

        Cookie cartCookie = new Cookie(CART_COOKIE_NAME, newCartValue);
        cartCookie.setMaxAge(7 * 24 * 60 * 60);
        cartCookie.setPath(request.getContextPath());
        response.addCookie(cartCookie);
    }

    private List<CategoryDTO> getAllCategories() {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT c FROM CategoryDTO c WHERE c.isDeleted = false ORDER BY c.name ASC";
            TypedQuery<CategoryDTO> query = em.createQuery(jpql, CategoryDTO.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    private String getCategoryNameById(String categoryId) {
        if (categoryId == null || categoryId.trim().isEmpty()) {
            return "";
        }

        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT c.name FROM CategoryDTO c WHERE c.id = :id AND c.isDeleted = false";
            TypedQuery<String> query = em.createQuery(jpql, String.class);
            query.setParameter("id", categoryId);

            List<String> result = query.setMaxResults(1).getResultList();
            if (result != null && !result.isEmpty()) {
                return result.get(0);
            }
            return "";
        } finally {
            em.close();
        }
    }

    private void loadWishlistState(HttpServletRequest request, List<ProductDTO> listProduct) {
        Map<String, Boolean> wishlistProductMap = new HashMap<String, Boolean>();
        request.setAttribute("wishlistProductMap", wishlistProductMap);

        AccountDTO loginUser = getLoginUser(request);
        if (!isCustomer(loginUser) || listProduct == null || listProduct.isEmpty()) {
            return;
        }

        WishlistDAO wishlistDAO = new WishlistDAO();
        Set<String> wishlistedProductIds = wishlistDAO.getWishlistedProductIdsByAccountId(loginUser.getId());

        for (ProductDTO product : listProduct) {
            if (product != null && product.getId() != null) {
                wishlistProductMap.put(product.getId(), wishlistedProductIds.contains(product.getId()));
            }
        }
    }

    private AccountDTO getLoginUser(HttpServletRequest request) {
        Object obj = request.getSession().getAttribute(AppConstants.SESSION_LOGIN_USER);
        if (obj instanceof AccountDTO) {
            return (AccountDTO) obj;
        }
        return null;
    }

    private boolean isCustomer(AccountDTO account) {
        return account != null && "CUSTOMER".equalsIgnoreCase(account.getRole());
    }

    private double calculateAverageRating(List<ReviewDTO> listReview) {
        if (listReview == null || listReview.isEmpty()) {
            return 0;
        }

        int total = 0;
        for (ReviewDTO review : listReview) {
            if (review != null) {
                total += review.getRating();
            }
        }

        return (double) total / listReview.size();
    }

    private Map<String, String> getReviewerNameMap(List<ReviewDTO> listReview) {
        Map<String, String> reviewerNameMap = new HashMap<String, String>();

        if (listReview == null || listReview.isEmpty()) {
            return reviewerNameMap;
        }

        Set<String> accountIds = new HashSet<String>();
        for (ReviewDTO review : listReview) {
            if (review != null && review.getAccountId() != null && !review.getAccountId().trim().isEmpty()) {
                accountIds.add(review.getAccountId());
            }
        }

        if (accountIds.isEmpty()) {
            return reviewerNameMap;
        }

        EntityManager em = JPAUtils.getEntityManager();
        try {
            String jpql = "SELECT a.id, a.fullname, a.username "
                    + "FROM AccountDTO a "
                    + "WHERE a.id IN :ids AND a.isDeleted = false";

            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("ids", accountIds);

            List<Object[]> rows = query.getResultList();
            for (Object[] row : rows) {
                String id = row[0] != null ? row[0].toString() : "";
                String fullname = row[1] != null ? row[1].toString().trim() : "";
                String username = row[2] != null ? row[2].toString().trim() : "";

                if (!id.isEmpty()) {
                    reviewerNameMap.put(id, !fullname.isEmpty() ? fullname : username);
                }
            }
        } finally {
            em.close();
        }

        return reviewerNameMap;
    }

    private void loadProductRatings(HttpServletRequest request, List<ProductDTO> listProduct) {
        Map<String, Double> ratingMap = new HashMap<String, Double>();
        Map<String, Integer> countMap = new HashMap<String, Integer>();
        ReviewDAO reviewDAO = new ReviewDAO();

        if (listProduct != null) {
            for (ProductDTO p : listProduct) {
                List<ReviewDTO> reviews = reviewDAO.getReviewsByProductId(p.getId());
                ratingMap.put(p.getId(), calculateAverageRating(reviews));
                countMap.put(p.getId(), reviews.size());
            }
        }
        request.setAttribute("ratingMap", ratingMap);
        request.setAttribute("countMap", countMap);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
