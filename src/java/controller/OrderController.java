package controller;

import dao.OrderDAO;
import dao.OrderItemDAO;
import dao.ProductDAO;
import dto.AccountDTO;
import dto.CartItemViewDTO;
import dto.OrderDTO;
import dto.OrderItemDTO;
import dto.OrderItemViewDTO;
import dto.ProductDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.AppConstants;

public class OrderController extends HttpServlet {

    private static final String CART_COOKIE_NAME = "cart";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");
        String url = AppConstants.ORDER_LIST_PAGE;

        try {
            OrderDAO orderDAO = new OrderDAO();
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            ProductDAO productDAO = new ProductDAO();

            if (AppConstants.ACTION_LIST_MY_ORDER.equals(action)) {
                AccountDTO loginUser = getLoginUser(request);

                if (!isCustomer(loginUser)) {
                    handleCustomerOnlyAccess(request, response);
                    return;
                }

                List<OrderDTO> myOrders = orderDAO.getOrdersByAccountId(loginUser.getId());
                request.setAttribute("myOrders", myOrders);
                url = AppConstants.MY_ORDER_LIST_PAGE;

            } else if (AppConstants.ACTION_SHOW_MY_ORDER_DETAIL.equals(action)) {
                AccountDTO loginUser = getLoginUser(request);

                if (!isCustomer(loginUser)) {
                    handleCustomerOnlyAccess(request, response);
                    return;
                }

                String id = trim(request.getParameter("id"));
                OrderDTO order = orderDAO.getOrderByIdAndAccountId(id, loginUser.getId());

                if (order == null) {
                    request.getSession().setAttribute("errorMessage", "Không tìm thấy đơn hàng của bạn.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_MY_ORDER);
                    return;
                }

                List<OrderItemViewDTO> orderItemViews = buildOrderItemViews(id, orderItemDAO, productDAO);

                request.setAttribute("order", order);
                request.setAttribute("orderItemViews", orderItemViews);
                url = AppConstants.MY_ORDER_DETAIL_PAGE;

            } else if (AppConstants.ACTION_LIST_ORDER.equals(action)) {
                List<OrderDTO> listOrder = orderDAO.getAllOrders();
                request.setAttribute("listOrder", listOrder);
                url = AppConstants.ORDER_LIST_PAGE;

            } else if (AppConstants.ACTION_SHOW_CREATE_ORDER.equals(action)) {
                url = AppConstants.ORDER_CREATE_PAGE;

            } else if (AppConstants.ACTION_INSERT_ORDER.equals(action)) {
                String fullname = trim(request.getParameter("fullname"));
                String phoneNumber = trim(request.getParameter("phoneNumber"));
                String email = trim(request.getParameter("email"));
                String address = trim(request.getParameter("address"));
                double totalPrice = parseDouble(request.getParameter("totalPrice"));

                if (fullname.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
                    request.setAttribute("error", "Vui lòng nhập đầy đủ họ tên, số điện thoại và địa chỉ.");
                    url = AppConstants.ORDER_CREATE_PAGE;
                } else {
                    OrderDTO order = new OrderDTO(address, phoneNumber, fullname, email, totalPrice, "PENDING");

                    boolean success = orderDAO.create(order);
                    if (success) {
                        request.getSession().setAttribute("successMessage", "Tạo đơn hàng thành công ở trạng thái PENDING.");
                    } else {
                        request.getSession().setAttribute("errorMessage", "Không thể tạo đơn hàng.");
                    }

                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ORDER);
                    return;
                }

            } else if (AppConstants.ACTION_SHOW_CHECKOUT.equals(action)) {
                boolean hasCart = loadCheckoutData(request, productDAO);

                if (!hasCart) {
                    request.getSession().setAttribute("errorMessage", "Giỏ hàng đang trống.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_CART);
                    return;
                }

                loadCheckoutCustomerInfo(request);
                url = AppConstants.CLIENT_CHECKOUT_PAGE;

            } else if (AppConstants.ACTION_PLACE_ORDER_FROM_CART.equals(action)) {
                String fullname = trim(request.getParameter("fullname"));
                String phoneNumber = trim(request.getParameter("phoneNumber"));
                String email = trim(request.getParameter("email"));
                String address = trim(request.getParameter("address"));

                if (fullname.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || address.isEmpty()) {
                    request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin nhận hàng.");

                    boolean hasCart = loadCheckoutData(request, productDAO);
                    if (!hasCart) {
                        request.getSession().setAttribute("errorMessage", "Giỏ hàng đang trống.");
                        response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_CART);
                        return;
                    }

                    setCheckoutFormData(request, fullname, phoneNumber, email, address);
                    url = AppConstants.CLIENT_CHECKOUT_PAGE;
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                }

                String cartValue = getCookieValue(request, CART_COOKIE_NAME);
                Map<String, Integer> cartMap = parseCartCookie(cartValue);

                if (cartMap.isEmpty()) {
                    request.getSession().setAttribute("errorMessage", "Giỏ hàng đang trống.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_CART);
                    return;
                }

                List<OrderItemDTO> orderItems = new ArrayList<OrderItemDTO>();
                double totalPrice = 0;

                for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
                    String productId = entry.getKey();
                    int quantity = entry.getValue();

                    ProductDTO product = productDAO.getProductById(productId);

                    if (product == null) {
                        request.setAttribute("error", "Có sản phẩm trong giỏ không còn tồn tại.");
                        loadCheckoutData(request, productDAO);
                        setCheckoutFormData(request, fullname, phoneNumber, email, address);
                        url = AppConstants.CLIENT_CHECKOUT_PAGE;
                        request.getRequestDispatcher(url).forward(request, response);
                        return;
                    }

                    if (quantity <= 0) {
                        request.setAttribute("error", "Số lượng sản phẩm trong giỏ không hợp lệ.");
                        loadCheckoutData(request, productDAO);
                        setCheckoutFormData(request, fullname, phoneNumber, email, address);
                        url = AppConstants.CLIENT_CHECKOUT_PAGE;
                        request.getRequestDispatcher(url).forward(request, response);
                        return;
                    }

                    if (product.getStockQuantity() <= 0) {
                        request.setAttribute("error", "Sản phẩm " + product.getName() + " hiện đã hết hàng.");
                        loadCheckoutData(request, productDAO);
                        setCheckoutFormData(request, fullname, phoneNumber, email, address);
                        url = AppConstants.CLIENT_CHECKOUT_PAGE;
                        request.getRequestDispatcher(url).forward(request, response);
                        return;
                    }

                    if (quantity > product.getStockQuantity()) {
                        request.setAttribute("error", "Sản phẩm " + product.getName() + " chỉ còn " + product.getStockQuantity() + " trong kho.");
                        loadCheckoutData(request, productDAO);
                        setCheckoutFormData(request, fullname, phoneNumber, email, address);
                        url = AppConstants.CLIENT_CHECKOUT_PAGE;
                        request.getRequestDispatcher(url).forward(request, response);
                        return;
                    }

                    double itemPrice = product.getPrice();
                    totalPrice += itemPrice * quantity;

                    OrderItemDTO orderItem = new OrderItemDTO("", productId, quantity, itemPrice);
                    orderItems.add(orderItem);
                }

                AccountDTO loginUser = getLoginUser(request);
                OrderDTO order;

                if (isCustomer(loginUser) && !safe(loginUser.getId()).isEmpty()) {
                    order = new OrderDTO(loginUser.getId(), address, phoneNumber, fullname, email, totalPrice, "PENDING");
                } else {
                    order = new OrderDTO(address, phoneNumber, fullname, email, totalPrice, "PENDING");
                }

                boolean success = orderDAO.createOrderWithItems(order, orderItems);

                if (!success) {
                    request.setAttribute("error", "Không thể tạo đơn hàng. Vui lòng thử lại.");
                    loadCheckoutData(request, productDAO);
                    setCheckoutFormData(request, fullname, phoneNumber, email, address);
                    url = AppConstants.CLIENT_CHECKOUT_PAGE;
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                }

                clearCartCookie(response, request);
                request.getSession().setAttribute("successMessage",
                        "Đặt hàng thành công. Mã đơn: " + order.getId() + ". Nhân viên sẽ duyệt đơn sau.");

                if (isCustomer(loginUser) && !safe(loginUser.getId()).isEmpty()) {
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action="
                            + AppConstants.ACTION_SHOW_MY_ORDER_DETAIL + "&id=" + order.getId());
                } else {
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_SHOP);
                }
                return;

            } else if (AppConstants.ACTION_SHOW_ORDER_DETAIL.equals(action)) {
                String id = request.getParameter("id");
                OrderDTO order = orderDAO.getOrderById(id);

                if (order == null) {
                    request.getSession().setAttribute("errorMessage", "Order không tồn tại hoặc đã bị xóa.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ORDER);
                    return;
                }

                List<OrderItemViewDTO> orderItemViews = buildOrderItemViews(id, orderItemDAO, productDAO);

                request.setAttribute("order", order);
                request.setAttribute("orderItemViews", orderItemViews);
                url = AppConstants.ORDER_DETAIL_PAGE;

            } else if (AppConstants.ACTION_APPROVE_ORDER.equals(action)) {
                String id = request.getParameter("id");

                String errorMsg = orderDAO.approveOrder(id);
                if (errorMsg == null) {
                    request.getSession().setAttribute("successMessage", "Duyệt đơn hàng thành công.");
                } else {
                    request.getSession().setAttribute("errorMessage", errorMsg);
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ORDER);
                return;

            } else if (AppConstants.ACTION_CANCEL_ORDER.equals(action)) {
                String id = request.getParameter("id");

                String errorMsg = orderDAO.cancelOrder(id);
                if (errorMsg == null) {
                    request.getSession().setAttribute("successMessage", "Hủy đơn hàng thành công.");
                } else {
                    request.getSession().setAttribute("errorMessage", errorMsg);
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ORDER);
                return;

            } else if (AppConstants.ACTION_EDIT_ORDER.equals(action)) {
                String id = request.getParameter("id");
                OrderDTO order = orderDAO.getOrderById(id);

                if (order == null) {
                    request.getSession().setAttribute("errorMessage", "Order không tồn tại hoặc đã bị xóa.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ORDER);
                    return;
                }

                if (!"PENDING".equalsIgnoreCase(order.getStatus())) {
                    request.getSession().setAttribute("errorMessage",
                            "Chỉ được chỉnh sửa đơn hàng khi đang ở trạng thái PENDING.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ORDER);
                    return;
                }

                request.setAttribute("order", order);
                url = AppConstants.ORDER_EDIT_PAGE;

            } else if (AppConstants.ACTION_UPDATE_ORDER.equals(action)) {
                String id = request.getParameter("id");
                OrderDTO order = orderDAO.getOrderById(id);

                if (order == null) {
                    request.getSession().setAttribute("errorMessage", "Order không tồn tại hoặc đã bị xóa.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ORDER);
                    return;
                }

                if (!"PENDING".equalsIgnoreCase(order.getStatus())) {
                    request.getSession().setAttribute("errorMessage",
                            "Chỉ được cập nhật đơn hàng khi đang ở trạng thái PENDING.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ORDER);
                    return;
                }

                String fullname = trim(request.getParameter("fullname"));
                String phoneNumber = trim(request.getParameter("phoneNumber"));
                String email = trim(request.getParameter("email"));
                String address = trim(request.getParameter("address"));
                double totalPrice = parseDouble(request.getParameter("totalPrice"));

                if (fullname.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
                    request.setAttribute("error", "Vui lòng nhập đầy đủ họ tên, số điện thoại và địa chỉ.");
                    request.setAttribute("order", order);
                    url = AppConstants.ORDER_EDIT_PAGE;
                } else {
                    order.setFullname(fullname);
                    order.setPhoneNumber(phoneNumber);
                    order.setEmail(email);
                    order.setAddress(address);
                    order.setTotalPrice(totalPrice);

                    boolean success = orderDAO.update(order);

                    if (success) {
                        request.getSession().setAttribute("successMessage", "Cập nhật đơn hàng thành công.");
                    } else {
                        request.getSession().setAttribute("errorMessage", "Không thể cập nhật đơn hàng.");
                    }

                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ORDER);
                    return;
                }

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

    private List<OrderItemViewDTO> buildOrderItemViews(String orderId, OrderItemDAO orderItemDAO, ProductDAO productDAO) {
        List<OrderItemDTO> orderItems = orderItemDAO.getOrderItemsByOrderId(orderId);
        List<OrderItemViewDTO> orderItemViews = new ArrayList<OrderItemViewDTO>();

        for (OrderItemDTO item : orderItems) {
            ProductDTO product = productDAO.getProductById(item.getProductId());

            String productName = "Sản phẩm không tồn tại";
            String imageUrl = "";

            if (product != null) {
                productName = product.getName();
                imageUrl = product.getImageUrl();
            }

            OrderItemViewDTO viewItem = new OrderItemViewDTO(
                    item.getId(),
                    item.getProductId(),
                    productName,
                    imageUrl,
                    item.getQuantity(),
                    item.getPrice()
            );

            orderItemViews.add(viewItem);
        }

        return orderItemViews;
    }

    private boolean loadCheckoutData(HttpServletRequest request, ProductDAO productDAO) {
        String cartValue = getCookieValue(request, CART_COOKIE_NAME);
        Map<String, Integer> cartMap = parseCartCookie(cartValue);

        List<CartItemViewDTO> cartItems = new ArrayList<CartItemViewDTO>();
        double total = 0;

        for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();

            ProductDTO product = productDAO.getProductById(productId);
            if (product != null && quantity > 0) {
                CartItemViewDTO item = new CartItemViewDTO(product, quantity);
                cartItems.add(item);
                total += item.getSubtotal();
            }
        }

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("cartTotal", total);
        request.setAttribute("cartSize", cartItems.size());

        return !cartItems.isEmpty();
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

    private Map<String, Integer> parseCartCookie(String cartValue) {
        Map<String, Integer> cartMap = new LinkedHashMap<String, Integer>();

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

    private void clearCartCookie(HttpServletResponse response, HttpServletRequest request) {
        Cookie cartCookie = new Cookie(CART_COOKIE_NAME, "");
        cartCookie.setMaxAge(0);

        String contextPath = request.getContextPath();
        if (contextPath == null || contextPath.trim().isEmpty()) {
            cartCookie.setPath("/");
        } else {
            cartCookie.setPath(contextPath);
        }

        response.addCookie(cartCookie);
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

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }

    private void loadCheckoutCustomerInfo(HttpServletRequest request) {
        AccountDTO loginUser = getLoginUser(request);
        if (!isCustomer(loginUser)) {
            return;
        }

        request.setAttribute("checkoutFullname", safe(loginUser.getFullname()));
        request.setAttribute("checkoutPhoneNumber", safe(loginUser.getPhoneNumber()));
        request.setAttribute("checkoutEmail", safe(loginUser.getEmail()));
        request.setAttribute("checkoutAddress", safe(loginUser.getAddress()));
    }

    private void setCheckoutFormData(HttpServletRequest request, String fullname,
            String phoneNumber, String email, String address) {
        request.setAttribute("checkoutFullname", safe(fullname));
        request.setAttribute("checkoutPhoneNumber", safe(phoneNumber));
        request.setAttribute("checkoutEmail", safe(email));
        request.setAttribute("checkoutAddress", safe(address));
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

    private void handleCustomerOnlyAccess(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        AccountDTO loginUser = getLoginUser(request);

        if (loginUser == null) {
            request.getSession().setAttribute("errorMessage", "Vui lòng đăng nhập để xem đơn hàng của bạn.");
            response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_LOGIN);
            return;
        }

        request.getSession().setAttribute("errorMessage", "Chỉ tài khoản CUSTOMER mới dùng được tính năng này.");
        response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_SHOP);
    }

    private String safe(String value) {
        return value == null ? "" : value;
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
