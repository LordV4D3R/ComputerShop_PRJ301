package controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.AccountDTO;
import models.ProductDAO;
import models.ProductDTO;
import models.WishlistDAO;
import models.WishlistDTO;
import utils.AppConstants;

public class WishlistController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = trim(request.getParameter("action"));
        String url = AppConstants.WISHLIST_LIST_PAGE;

        try {
            WishlistDAO wishlistDAO = new WishlistDAO();
            ProductDAO productDAO = new ProductDAO();
            AccountDTO loginUser = getLoginUser(request);

            if (AppConstants.ACTION_LIST_WISHLIST.equals(action)) {
                if (!ensureCustomer(request, response, loginUser)) {
                    return;
                }

                List<WishlistDTO> listWishlist = wishlistDAO.getWishlistsByAccountId(loginUser.getId());
                Map<String, ProductDTO> productMap = buildProductMap(listWishlist, productDAO);

                request.setAttribute("listWishlist", listWishlist);
                request.setAttribute("productMap", productMap);
                url = AppConstants.WISHLIST_LIST_PAGE;

            } else if (AppConstants.ACTION_INSERT_WISHLIST.equals(action)) {
                if (!ensureCustomer(request, response, loginUser)) {
                    return;
                }

                String productId = trim(request.getParameter("productId"));
                String redirect = trim(request.getParameter("redirect"));

                if (productId.isEmpty()) {
                    request.getSession().setAttribute("errorMessage", "Không tìm thấy sản phẩm để thêm wishlist.");
                    response.sendRedirect(buildRedirectUrl(request, redirect, productId));
                    return;
                }

                ProductDTO product = productDAO.getProductById(productId);
                if (product == null) {
                    request.getSession().setAttribute("errorMessage", "Sản phẩm không tồn tại hoặc đã bị xóa.");
                    response.sendRedirect(buildRedirectUrl(request, redirect, productId));
                    return;
                }

                WishlistDTO existed = wishlistDAO.getActiveWishlist(loginUser.getId(), productId);
                if (existed != null) {
                    request.getSession().setAttribute("successMessage", "Sản phẩm đã có sẵn trong wishlist.");
                    response.sendRedirect(buildRedirectUrl(request, redirect, productId));
                    return;
                }

                WishlistDTO wishlist = new WishlistDTO(loginUser.getId(), productId);
                boolean created = wishlistDAO.create(wishlist);

                if (created) {
                    request.getSession().setAttribute("successMessage", "Đã thêm sản phẩm vào wishlist.");
                } else {
                    request.getSession().setAttribute("errorMessage", "Không thể thêm sản phẩm vào wishlist.");
                }

                response.sendRedirect(buildRedirectUrl(request, redirect, productId));
                return;

            } else if (AppConstants.ACTION_DELETE_WISHLIST.equals(action)) {
                if (!ensureCustomer(request, response, loginUser)) {
                    return;
                }

                String id = trim(request.getParameter("id"));
                String productId = trim(request.getParameter("productId"));
                String redirect = trim(request.getParameter("redirect"));

                WishlistDTO wishlist = null;

                if (!id.isEmpty()) {
                    wishlist = wishlistDAO.getWishlistById(id);
                    if (wishlist != null && !loginUser.getId().equals(wishlist.getAccountId())) {
                        wishlist = null;
                    }
                } else if (!productId.isEmpty()) {
                    wishlist = wishlistDAO.getActiveWishlist(loginUser.getId(), productId);
                }

                if (wishlist == null) {
                    request.getSession().setAttribute("errorMessage", "Không tìm thấy wishlist để xóa.");
                    response.sendRedirect(buildRedirectUrl(request, redirect, productId));
                    return;
                }

                boolean deleted = wishlistDAO.delete(wishlist.getId());
                if (deleted) {
                    request.getSession().setAttribute("successMessage", "Đã xóa sản phẩm khỏi wishlist.");
                } else {
                    request.getSession().setAttribute("errorMessage", "Không thể xóa sản phẩm khỏi wishlist.");
                }

                response.sendRedirect(buildRedirectUrl(request, redirect, productId));
                return;

            } else {
                response.sendRedirect(request.getContextPath() + "/MainController?action=" + AppConstants.ACTION_LIST_WISHLIST);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Có lỗi tại WishlistController: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/MainController?action=" + AppConstants.ACTION_SHOW_SHOP);
            return;
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    private Map<String, ProductDTO> buildProductMap(List<WishlistDTO> listWishlist, ProductDAO productDAO) {
        Map<String, ProductDTO> productMap = new LinkedHashMap<String, ProductDTO>();

        if (listWishlist == null) {
            return productMap;
        }

        for (WishlistDTO wishlist : listWishlist) {
            if (wishlist == null) {
                continue;
            }

            String productId = wishlist.getProductId();
            if (productId == null || productId.trim().isEmpty() || productMap.containsKey(productId)) {
                continue;
            }

            ProductDTO product = productDAO.getProductById(productId);
            if (product != null) {
                productMap.put(productId, product);
            }
        }

        return productMap;
    }

    private boolean ensureCustomer(HttpServletRequest request, HttpServletResponse response, AccountDTO loginUser)
            throws IOException {

        if (loginUser == null) {
            request.getSession().setAttribute("errorMessage", "Vui lòng đăng nhập để dùng tính năng wishlist.");
            response.sendRedirect(request.getContextPath() + "/MainController?action=" + AppConstants.ACTION_SHOW_LOGIN);
            return false;
        }

        if (!"CUSTOMER".equalsIgnoreCase(trim(loginUser.getRole()))) {
            request.getSession().setAttribute("errorMessage", "Chỉ tài khoản CUSTOMER mới được dùng wishlist.");
            response.sendRedirect(request.getContextPath() + "/MainController?action=" + AppConstants.ACTION_SHOW_SHOP);
            return false;
        }

        return true;
    }

    private String buildRedirectUrl(HttpServletRequest request, String redirect, String productId) {
        String contextPath = request.getContextPath();

        if ("home".equalsIgnoreCase(redirect)) {
            return contextPath + "/MainController?action=" + AppConstants.ACTION_SHOW_HOME;
        }

        if ("wishlist".equalsIgnoreCase(redirect)) {
            return contextPath + "/MainController?action=" + AppConstants.ACTION_LIST_WISHLIST;
        }

        // ĐOẠN MỚI THÊM VÀO ĐỂ XỬ LÝ REDIRECT CHO TRANG CHI TIẾT (KÈM ID SẢN PHẨM)
        if ("detail".equalsIgnoreCase(redirect) && productId != null && !productId.trim().isEmpty()) {
            return contextPath + "/MainController?action=" + AppConstants.ACTION_SHOW_PRODUCT_DETAIL + "&id=" + productId;
        }

        return contextPath + "/MainController?action=" + AppConstants.ACTION_SHOW_SHOP;
    }

    private AccountDTO getLoginUser(HttpServletRequest request) {
        Object obj = request.getSession().getAttribute(AppConstants.SESSION_LOGIN_USER);
        if (obj instanceof AccountDTO) {
            return (AccountDTO) obj;
        }
        return null;
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
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