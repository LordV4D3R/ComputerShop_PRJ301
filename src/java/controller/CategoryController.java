package controller;

import models.CategoryDAO;
import models.CategoryDTO;
import utils.AppConstants;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CategoryController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");
        String url = AppConstants.CATEGORY_LIST_PAGE;

        try {
            CategoryDAO dao = new CategoryDAO();

            if (AppConstants.ACTION_LIST_CATEGORY.equals(action)) {
                List<CategoryDTO> list = dao.getAllCategories();
                request.setAttribute("listCategory", list);
                url = AppConstants.CATEGORY_LIST_PAGE;

            } else if (AppConstants.ACTION_SHOW_CREATE_CATEGORY.equals(action)) {
                url = AppConstants.CATEGORY_CREATE_PAGE;

            } else if (AppConstants.ACTION_INSERT_CATEGORY.equals(action)) {
                String name = request.getParameter("name");
                String description = request.getParameter("description");

                dao.create(new CategoryDTO(name, description));
                url = AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_CATEGORY;

            } else if (AppConstants.ACTION_DELETE_CATEGORY.equals(action)) {
                String id = request.getParameter("id");
                if (id != null) {
                    dao.delete(id);
                }

                url = AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_CATEGORY;

            } else if (AppConstants.ACTION_EDIT_CATEGORY.equals(action)) {
                String id = request.getParameter("id");
                CategoryDTO c = dao.getCategoryById(id);

                if (c == null) {
                    request.setAttribute("error", "Category không tồn tại hoặc đã bị xóa.");
                    request.setAttribute("listCategory", dao.getAllCategories());
                    url = AppConstants.CATEGORY_LIST_PAGE;
                } else {
                    request.setAttribute("category", c);
                    url = AppConstants.CATEGORY_EDIT_PAGE;
                }

            } else if (AppConstants.ACTION_UPDATE_CATEGORY.equals(action)) {
                String id = request.getParameter("id");
                CategoryDTO c = dao.getCategoryById(id);

                if (c != null) {
                    c.setName(request.getParameter("name"));
                    c.setDescription(request.getParameter("description"));
                    dao.update(c);
                }

                url = AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_CATEGORY;
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
