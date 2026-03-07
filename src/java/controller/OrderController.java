package controller;

import dao.OrderDAO;
import dto.OrderDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.AppConstants;

public class OrderController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");
        String url = AppConstants.ORDER_LIST_PAGE;

        try {
            OrderDAO orderDAO = new OrderDAO();

            if (AppConstants.ACTION_LIST_ORDER.equals(action)) {
                List<OrderDTO> listOrder = orderDAO.getAllOrders();
                request.setAttribute("listOrder", listOrder);
                url = AppConstants.ORDER_LIST_PAGE;

            } else if (AppConstants.ACTION_SHOW_CREATE_ORDER.equals(action)) {
                url = AppConstants.ORDER_CREATE_PAGE;

            } else if (AppConstants.ACTION_INSERT_ORDER.equals(action)) {
                String fullname = request.getParameter("fullname");
                String phoneNumber = request.getParameter("phoneNumber");
                String email = request.getParameter("email");
                String address = request.getParameter("address");
                double totalPrice = parseDouble(request.getParameter("totalPrice"));
                String status = request.getParameter("status");

                OrderDTO order = new OrderDTO(address, phoneNumber, fullname, email, totalPrice, status);
                orderDAO.create(order);

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ORDER);
                return;

            } else if (AppConstants.ACTION_EDIT_ORDER.equals(action)) {
                String id = request.getParameter("id");
                OrderDTO order = orderDAO.getOrderById(id);

                if (order == null) {
                    request.setAttribute("error", "Order không tồn tại hoặc đã bị xóa.");
                    List<OrderDTO> listOrder = orderDAO.getAllOrders();
                    request.setAttribute("listOrder", listOrder);
                    url = AppConstants.ORDER_LIST_PAGE;
                } else {
                    request.setAttribute("order", order);
                    url = AppConstants.ORDER_EDIT_PAGE;
                }

            } else if (AppConstants.ACTION_UPDATE_ORDER.equals(action)) {
                String id = request.getParameter("id");
                OrderDTO order = orderDAO.getOrderById(id);

                if (order != null) {
                    order.setFullname(request.getParameter("fullname"));
                    order.setPhoneNumber(request.getParameter("phoneNumber"));
                    order.setEmail(request.getParameter("email"));
                    order.setAddress(request.getParameter("address"));
                    order.setTotalPrice(parseDouble(request.getParameter("totalPrice")));
                    order.setStatus(request.getParameter("status"));

                    orderDAO.update(order);
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ORDER);
                return;

            } else if (AppConstants.ACTION_DELETE_ORDER.equals(action)) {
                String id = request.getParameter("id");
                if (id != null && !id.trim().isEmpty()) {
                    orderDAO.delete(id);
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ORDER);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra ở OrderController: " + e.getMessage());
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    private double parseDouble(String value) {
        try {
            if (value == null || value.trim().isEmpty()) {
                return 0;
            }
            return Double.parseDouble(value);
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