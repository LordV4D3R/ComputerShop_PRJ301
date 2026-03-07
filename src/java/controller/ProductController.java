package controller;

import dao.ProductDAO;
import dto.ProductDTO;
import utils.AppConstants;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductController extends HttpServlet {

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

            } else if (AppConstants.ACTION_SHOW_CREATE_PRODUCT.equals(action)) {
                url = AppConstants.PRODUCT_CREATE_PAGE;

            } else if (AppConstants.ACTION_INSERT_PRODUCT.equals(action)) {
                // đọc form
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
            } // ====== NEW: SHOW EDIT FORM ======
            else if (AppConstants.ACTION_EDIT_PRODUCT.equals(action)) {
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
            } // ====== NEW: SUBMIT UPDATE ======
            else if (AppConstants.ACTION_UPDATE_PRODUCT.equals(action)) {
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
