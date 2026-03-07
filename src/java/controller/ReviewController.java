package controller;

import dao.AccountDAO;
import dao.ProductDAO;
import dao.ReviewDAO;
import dto.AccountDTO;
import dto.ProductDTO;
import dto.ReviewDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
                String productId = request.getParameter("productId");
                String accountId = request.getParameter("accountId");
                int rating = parseInt(request.getParameter("rating"));
                String comment = request.getParameter("comment");

                ReviewDTO review = new ReviewDTO(productId, accountId, rating, comment);
                reviewDAO.create(review);

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_REVIEW);
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