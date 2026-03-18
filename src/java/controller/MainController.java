package controller;

import utils.AppConstants;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class MainController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");
        String url = AppConstants.PRODUCT_CONTROLLER;
        if (action == null || action.trim().isEmpty()) {
            action = AppConstants.ACTION_SHOW_HOME;
        }

        try {
            if (AppConstants.ACTION_SHOW_HOME.equals(action)) {
                url = AppConstants.PRODUCT_CONTROLLER;
            } else if (AppConstants.ACTION_SHOW_SHOP.equals(action)) {
                url = AppConstants.PRODUCT_CONTROLLER;
            } else if (AppConstants.ACTION_SHOW_LOGIN.equals(action)
                    || AppConstants.ACTION_LOGIN.equals(action)
                    || AppConstants.ACTION_LOGOUT.equals(action)
                    || AppConstants.ACTION_SHOW_REGISTER.equals(action)
                    || AppConstants.ACTION_REGISTER.equals(action)
                    || AppConstants.ACTION_LIST_ACCOUNT.equals(action)
                    || AppConstants.ACTION_SHOW_CREATE_ACCOUNT.equals(action)
                    || AppConstants.ACTION_INSERT_ACCOUNT.equals(action)
                    || AppConstants.ACTION_DELETE_ACCOUNT.equals(action)
                    || AppConstants.ACTION_EDIT_ACCOUNT.equals(action)
                    || AppConstants.ACTION_UPDATE_ACCOUNT.equals(action)
                    || AppConstants.ACTION_SHOW_ACCOUNT.equals(action)
                    || AppConstants.ACTION_UPDATE_PROFILE.equals(action)) {

                url = AppConstants.ACCOUNT_CONTROLLER;

            } else if (AppConstants.ACTION_LIST_PRODUCT.equals(action)
                    || AppConstants.ACTION_SHOW_CREATE_PRODUCT.equals(action)
                    || AppConstants.ACTION_INSERT_PRODUCT.equals(action)
                    || AppConstants.ACTION_DELETE_PRODUCT.equals(action)
                    || AppConstants.ACTION_EDIT_PRODUCT.equals(action)
                    || AppConstants.ACTION_UPDATE_PRODUCT.equals(action)
                    || AppConstants.ACTION_SHOW_SHOP.equals(action)
                    || AppConstants.ACTION_ADD_TO_CART.equals(action)
                    || AppConstants.ACTION_SHOW_CART.equals(action)
                    || AppConstants.ACTION_INCREASE_CART_ITEM.equals(action)
                    || AppConstants.ACTION_DECREASE_CART_ITEM.equals(action)
                    || AppConstants.ACTION_REMOVE_FROM_CART.equals(action)) {

                url = AppConstants.PRODUCT_CONTROLLER;

            } else if (AppConstants.ACTION_LIST_CATEGORY.equals(action)
                    || AppConstants.ACTION_SHOW_CREATE_CATEGORY.equals(action)
                    || AppConstants.ACTION_INSERT_CATEGORY.equals(action)
                    || AppConstants.ACTION_DELETE_CATEGORY.equals(action)
                    || AppConstants.ACTION_EDIT_CATEGORY.equals(action)
                    || AppConstants.ACTION_UPDATE_CATEGORY.equals(action)) {

                url = AppConstants.CATEGORY_CONTROLLER;

            } else if (AppConstants.ACTION_LIST_ORDER.equals(action)
                    || AppConstants.ACTION_SHOW_CREATE_ORDER.equals(action)
                    || AppConstants.ACTION_INSERT_ORDER.equals(action)
                    || AppConstants.ACTION_EDIT_ORDER.equals(action)
                    || AppConstants.ACTION_UPDATE_ORDER.equals(action)
                    || AppConstants.ACTION_DELETE_ORDER.equals(action)
                    || AppConstants.ACTION_SHOW_ORDER_DETAIL.equals(action)
                    || AppConstants.ACTION_APPROVE_ORDER.equals(action)
                    || AppConstants.ACTION_CANCEL_ORDER.equals(action)
                    || AppConstants.ACTION_SHOW_CHECKOUT.equals(action)
                    || AppConstants.ACTION_PLACE_ORDER_FROM_CART.equals(action)
                    || AppConstants.ACTION_SHOW_QR_PAYMENT.equals(action)
                    || AppConstants.ACTION_LIST_MY_ORDER.equals(action)
                    || AppConstants.ACTION_SHOW_MY_ORDER_DETAIL.equals(action)) {
                url = "OrderController";

            } else if (AppConstants.ACTION_LIST_ORDER_ITEM.equals(action)
                    || AppConstants.ACTION_SHOW_CREATE_ORDER_ITEM.equals(action)
                    || AppConstants.ACTION_INSERT_ORDER_ITEM.equals(action)
                    || AppConstants.ACTION_DELETE_ORDER_ITEM.equals(action)
                    || AppConstants.ACTION_EDIT_ORDER_ITEM.equals(action)
                    || AppConstants.ACTION_UPDATE_ORDER_ITEM.equals(action)) {

                url = AppConstants.ORDER_ITEM_CONTROLLER;

            } else if (AppConstants.ACTION_LIST_WISHLIST.equals(action)
                    || AppConstants.ACTION_SHOW_CREATE_WISHLIST.equals(action)
                    || AppConstants.ACTION_INSERT_WISHLIST.equals(action)
                    || AppConstants.ACTION_DELETE_WISHLIST.equals(action)
                    || AppConstants.ACTION_EDIT_WISHLIST.equals(action)
                    || AppConstants.ACTION_UPDATE_WISHLIST.equals(action)) {

                url = AppConstants.WISHLIST_CONTROLLER;

            } else if (AppConstants.ACTION_LIST_REVIEW.equals(action)
                    || AppConstants.ACTION_SHOW_CREATE_REVIEW.equals(action)
                    || AppConstants.ACTION_INSERT_REVIEW.equals(action)
                    || AppConstants.ACTION_DELETE_REVIEW.equals(action)
                    || AppConstants.ACTION_EDIT_REVIEW.equals(action)
                    || AppConstants.ACTION_UPDATE_REVIEW.equals(action)) {

                url = AppConstants.REVIEW_CONTROLLER;
            }

        } catch (Exception e) {
            log("Lỗi tại MainController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
