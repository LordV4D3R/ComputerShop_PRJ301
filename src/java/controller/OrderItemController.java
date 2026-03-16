package controller;

import models.OrderDAO;
import models.OrderItemDAO;
import models.ProductDAO;
import models.OrderDTO;
import models.OrderItemDTO;
import models.ProductDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.AppConstants;

public class OrderItemController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");
        String url = AppConstants.ORDER_ITEM_LIST_PAGE;

        try {
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            OrderDAO orderDAO = new OrderDAO();
            ProductDAO productDAO = new ProductDAO();

            if (AppConstants.ACTION_LIST_ORDER_ITEM.equals(action)) {
                List<OrderItemDTO> listOrderItem = orderItemDAO.getAllOrderItems();
                request.setAttribute("listOrderItem", listOrderItem);
                url = AppConstants.ORDER_ITEM_LIST_PAGE;

            } else if (AppConstants.ACTION_SHOW_CREATE_ORDER_ITEM.equals(action)) {
                List<OrderDTO> listOrder = orderDAO.getAllOrders();
                List<ProductDTO> listProduct = productDAO.getAllProducts();

                request.setAttribute("listOrder", listOrder);
                request.setAttribute("listProduct", listProduct);
                url = AppConstants.ORDER_ITEM_CREATE_PAGE;

            } else if (AppConstants.ACTION_INSERT_ORDER_ITEM.equals(action)) {
                String orderId = request.getParameter("orderId");
                String productId = request.getParameter("productId");
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                double price = Double.parseDouble(request.getParameter("price"));

                OrderItemDTO item = new OrderItemDTO(orderId, productId, quantity, price);
                orderItemDAO.create(item);

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ORDER_ITEM);
                return;

            } else if (AppConstants.ACTION_EDIT_ORDER_ITEM.equals(action)) {
                String id = request.getParameter("id");
                OrderItemDTO item = orderItemDAO.getOrderItemById(id);

                if (item == null) {
                    request.setAttribute("error", "Order item không tồn tại hoặc đã bị xóa.");
                    request.setAttribute("listOrderItem", orderItemDAO.getAllOrderItems());
                    url = AppConstants.ORDER_ITEM_LIST_PAGE;
                } else {
                    List<OrderDTO> listOrder = orderDAO.getAllOrders();
                    List<ProductDTO> listProduct = productDAO.getAllProducts();

                    request.setAttribute("orderItem", item);
                    request.setAttribute("listOrder", listOrder);
                    request.setAttribute("listProduct", listProduct);
                    url = AppConstants.ORDER_ITEM_EDIT_PAGE;
                }

            } else if (AppConstants.ACTION_UPDATE_ORDER_ITEM.equals(action)) {
                String id = request.getParameter("id");
                OrderItemDTO item = orderItemDAO.getOrderItemById(id);

                if (item != null) {
                    item.setOrderId(request.getParameter("orderId"));
                    item.setProductId(request.getParameter("productId"));
                    item.setQuantity(Integer.parseInt(request.getParameter("quantity")));
                    item.setPrice(Double.parseDouble(request.getParameter("price")));

                    orderItemDAO.update(item);
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ORDER_ITEM);
                return;

            } else if (AppConstants.ACTION_DELETE_ORDER_ITEM.equals(action)) {
                String id = request.getParameter("id");
                if (id != null && !id.trim().isEmpty()) {
                    orderItemDAO.delete(id);
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ORDER_ITEM);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi tại OrderItemController: " + e.getMessage());
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