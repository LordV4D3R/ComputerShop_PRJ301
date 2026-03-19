package controller;

import models.AccountDAO;
import models.ProductDAO;
import models.ReviewDAO;
import models.AccountDTO;
import models.ProductDTO;
import models.ReviewDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.OrderDAO;
import utils.AppConstants;

public class ReviewController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");
        String url = AppConstants.REVIEW_LIST_PAGE;

        try {
            ReviewDAO reviewDAO = new ReviewDAO();
            AccountDAO accountDAO = new AccountDAO();
            ProductDAO productDAO = new ProductDAO();

            if (AppConstants.ACTION_LIST_REVIEW.equals(action)) {
                List<ReviewDTO> listReview = reviewDAO.getAllReviews();
                request.setAttribute("listReview", listReview);
                url = AppConstants.REVIEW_LIST_PAGE;

            } else if (AppConstants.ACTION_SHOW_CREATE_REVIEW.equals(action)) {
                List<AccountDTO> listAccount = accountDAO.getAllAccounts();
                List<ProductDTO> listProduct = productDAO.getAllProducts();

                request.setAttribute("listAccount", listAccount);
                request.setAttribute("listProduct", listProduct);
                url = AppConstants.REVIEW_CREATE_PAGE;

            } else if (AppConstants.ACTION_INSERT_REVIEW.equals(action)) {
                String productId = trim(request.getParameter("productId"));
                int rating = parseInt(request.getParameter("rating"));
                String comment = trim(request.getParameter("comment"));

                AccountDTO loginUser = getLoginUser(request);
                if (loginUser == null || !isCustomer(loginUser)) {
                    request.getSession().setAttribute("errorMessage", "Bạn cần đăng nhập bằng tài khoản CUSTOMER để đánh giá sản phẩm.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_LOGIN);
                    return;
                }

                if (productId.isEmpty()) {
                    request.getSession().setAttribute("errorMessage", "Không tìm thấy sản phẩm để đánh giá.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_SHOP);
                    return;
                }

                ProductDTO product = productDAO.getProductById(productId);
                if (product == null) {
                    request.getSession().setAttribute("errorMessage", "Sản phẩm không tồn tại hoặc đã bị xóa.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_SHOP);
                    return;
                }

                if (rating < 1 || rating > 5) {
                    request.getSession().setAttribute("errorMessage", "Số sao đánh giá phải từ 1 đến 5.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_PRODUCT_DETAIL + "&id=" + productId);
                    return;
                }

                OrderDAO orderDAO = new OrderDAO();
                if (!orderDAO.hasPurchasedApprovedProduct(loginUser.getId(), productId)) {
                    request.getSession().setAttribute("errorMessage", "Bạn chỉ có thể đánh giá sau khi đã mua sản phẩm và đơn hàng đã được duyệt.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_PRODUCT_DETAIL + "&id=" + productId);
                    return;
                }

                if (reviewDAO.hasReviewByAccountAndProduct(loginUser.getId(), productId)) {
                    request.getSession().setAttribute("errorMessage", "Bạn đã đánh giá sản phẩm này rồi.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_PRODUCT_DETAIL + "&id=" + productId);
                    return;
                }

                ReviewDTO review = new ReviewDTO(productId, loginUser.getId(), rating, comment);
                boolean success = reviewDAO.create(review);

                if (success) {
                    request.getSession().setAttribute("successMessage", "Đánh giá sản phẩm thành công.");
                } else {
                    request.getSession().setAttribute("errorMessage", "Không thể lưu đánh giá. Vui lòng thử lại.");
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_PRODUCT_DETAIL + "&id=" + productId);
                return;

            } else if (AppConstants.ACTION_EDIT_REVIEW.equals(action)) {
                String id = request.getParameter("id");
                ReviewDTO review = reviewDAO.getReviewById(id);

                if (review == null) {
                    request.setAttribute("error", "Review không tồn tại hoặc đã bị xóa.");
                    request.setAttribute("listReview", reviewDAO.getAllReviews());
                    url = AppConstants.REVIEW_LIST_PAGE;
                } else {
                    List<AccountDTO> listAccount = accountDAO.getAllAccounts();
                    List<ProductDTO> listProduct = productDAO.getAllProducts();

                    request.setAttribute("review", review);
                    request.setAttribute("listAccount", listAccount);
                    request.setAttribute("listProduct", listProduct);
                    url = AppConstants.REVIEW_EDIT_PAGE;
                }

            } else if (AppConstants.ACTION_UPDATE_REVIEW.equals(action)) {
                String id = request.getParameter("id");
                ReviewDTO review = reviewDAO.getReviewById(id);

                if (review != null) {
                    review.setProductId(request.getParameter("productId"));
                    review.setAccountId(request.getParameter("accountId"));
                    review.setRating(parseInt(request.getParameter("rating")));
                    review.setComment(request.getParameter("comment"));

                    reviewDAO.update(review);
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_REVIEW);
                return;

            } else if (AppConstants.ACTION_DELETE_REVIEW.equals(action)) {
                String id = request.getParameter("id");
                if (id != null && !id.trim().isEmpty()) {
                    reviewDAO.delete(id);
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_REVIEW);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi tại ReviewController: " + e.getMessage());
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    private int parseInt(String value) {
        try {
            if (value == null || value.trim().isEmpty()) {
                return 0;
            }
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
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
        return account != null && "CUSTOMER".equalsIgnoreCase(trim(account.getRole()));
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
