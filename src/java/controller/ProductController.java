package controller;

import dao.ProductDAO;
import dto.CartItemViewDTO;
import dto.ProductDTO;
import utils.AppConstants;

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

public class ProductController extends HttpServlet {

    private static final String CART_COOKIE_NAME = "cart";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String url = AppConstants.PRODUCT_LIST_PAGE;

        try {
            ProductDAO productDAO = new ProductDAO();

            if (AppConstants.ACTION_LIST_PRODUCT.equals(action)) {
                List<ProductDTO> listProduct = productDAO.getAllProducts();
                request.setAttribute("listProduct", listProduct);
                url = AppConstants.PRODUCT_LIST_PAGE;

            } else if (AppConstants.ACTION_SHOW_SHOP.equals(action)) {
                String keyword = request.getParameter("keyword");
                List<ProductDTO> listProduct;

                if (keyword != null && !keyword.trim().isEmpty()) {
                    listProduct = productDAO.searchByName(keyword.trim());
                } else {
                    listProduct = productDAO.getAllProducts();
                }

                request.setAttribute("listProduct", listProduct);
                url = AppConstants.CLIENT_SHOP_PAGE;

            } else if (AppConstants.ACTION_ADD_TO_CART.equals(action)) {
                String productId = request.getParameter("productId");

                if (productId == null || productId.trim().isEmpty()) {
                    request.getSession().setAttribute("errorMessage", "Không tìm thấy sản phẩm để thêm vào giỏ.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_SHOP);
                    return;
                }

                ProductDTO product = productDAO.getProductById(productId);

                if (product == null) {
                    request.getSession().setAttribute("errorMessage", "Sản phẩm không tồn tại hoặc đã bị xóa.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_SHOP);
                    return;
                }

                if (product.getStockQuantity() <= 0) {
                    request.getSession().setAttribute("errorMessage", "Sản phẩm đã hết hàng.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_SHOP);
                    return;
                }

                String cartValue = getCookieValue(request, CART_COOKIE_NAME);
                String newCartValue = addProductToCartCookie(cartValue, productId);

                Cookie cartCookie = new Cookie(CART_COOKIE_NAME, newCartValue);
                cartCookie.setMaxAge(7 * 24 * 60 * 60);
                cartCookie.setPath(request.getContextPath());
                response.addCookie(cartCookie);

                request.getSession().setAttribute("successMessage", "Đã thêm sản phẩm vào giỏ hàng thành công.");
                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_SHOP);
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
                String imageUrl = request.getParameter("imageUrl");

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
                    product.setImageUrl(request.getParameter("imageUrl"));

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