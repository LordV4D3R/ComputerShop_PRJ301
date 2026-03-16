package controller;

import models.AccountDAO;
import models.ProductDAO;
import models.WishlistDAO;
import models.AccountDTO;
import models.ProductDTO;
import models.WishlistDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.AppConstants;

public class WishlistController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");
        String url = AppConstants.WISHLIST_LIST_PAGE;

        try {
            WishlistDAO wishlistDAO = new WishlistDAO();
            AccountDAO accountDAO = new AccountDAO();
            ProductDAO productDAO = new ProductDAO();

            if (AppConstants.ACTION_LIST_WISHLIST.equals(action)) {
                List<WishlistDTO> listWishlist = wishlistDAO.getAllWishlists();
                request.setAttribute("listWishlist", listWishlist);
                url = AppConstants.WISHLIST_LIST_PAGE;

            } else if (AppConstants.ACTION_SHOW_CREATE_WISHLIST.equals(action)) {
                List<AccountDTO> listAccount = accountDAO.getAllAccounts();
                List<ProductDTO> listProduct = productDAO.getAllProducts();

                request.setAttribute("listAccount", listAccount);
                request.setAttribute("listProduct", listProduct);
                url = AppConstants.WISHLIST_CREATE_PAGE;

            } else if (AppConstants.ACTION_INSERT_WISHLIST.equals(action)) {
                String accountId = request.getParameter("accountId");
                String productId = request.getParameter("productId");

                WishlistDTO wishlist = new WishlistDTO(accountId, productId);
                wishlistDAO.create(wishlist);

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_WISHLIST);
                return;

            } else if (AppConstants.ACTION_EDIT_WISHLIST.equals(action)) {
                String id = request.getParameter("id");
                WishlistDTO wishlist = wishlistDAO.getWishlistById(id);

                if (wishlist == null) {
                    request.setAttribute("error", "Wishlist không tồn tại hoặc đã bị xóa.");
                    request.setAttribute("listWishlist", wishlistDAO.getAllWishlists());
                    url = AppConstants.WISHLIST_LIST_PAGE;
                } else {
                    List<AccountDTO> listAccount = accountDAO.getAllAccounts();
                    List<ProductDTO> listProduct = productDAO.getAllProducts();

                    request.setAttribute("wishlist", wishlist);
                    request.setAttribute("listAccount", listAccount);
                    request.setAttribute("listProduct", listProduct);
                    url = AppConstants.WISHLIST_EDIT_PAGE;
                }

            } else if (AppConstants.ACTION_UPDATE_WISHLIST.equals(action)) {
                String id = request.getParameter("id");
                WishlistDTO wishlist = wishlistDAO.getWishlistById(id);

                if (wishlist != null) {
                    wishlist.setAccountId(request.getParameter("accountId"));
                    wishlist.setProductId(request.getParameter("productId"));
                    wishlistDAO.update(wishlist);
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_WISHLIST);
                return;

            } else if (AppConstants.ACTION_DELETE_WISHLIST.equals(action)) {
                String id = request.getParameter("id");
                if (id != null && !id.trim().isEmpty()) {
                    wishlistDAO.delete(id);
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_WISHLIST);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi tại WishlistController: " + e.getMessage());
        }

        request.getRequestDispatcher(url).forward(request, response);
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