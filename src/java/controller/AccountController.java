package controller;

import dao.AccountDAO;
import dto.AccountDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.AppConstants;

public class AccountController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");
        String url = AppConstants.ACCOUNT_LIST_PAGE;

        try {
            AccountDAO accountDAO = new AccountDAO();

            if (AppConstants.ACTION_LIST_ACCOUNT.equals(action)) {
                List<AccountDTO> listAccount = accountDAO.getAllAccounts();
                request.setAttribute("listAccount", listAccount);
                url = AppConstants.ACCOUNT_LIST_PAGE;

            } else if (AppConstants.ACTION_SHOW_CREATE_ACCOUNT.equals(action)) {
                url = AppConstants.ACCOUNT_CREATE_PAGE;

            } else if (AppConstants.ACTION_INSERT_ACCOUNT.equals(action)) {
                String username = request.getParameter("username");
                String hashPassword = request.getParameter("hashPassword");
                String address = request.getParameter("address");
                String phoneNumber = request.getParameter("phoneNumber");
                String fullname = request.getParameter("fullname");
                String email = request.getParameter("email");
                String role = request.getParameter("role");
                String imageUrl = request.getParameter("imageUrl");
                String status = request.getParameter("status");

                AccountDTO existed = accountDAO.getAccountByUsername(username);
                if (existed != null) {
                    request.setAttribute("error", "Username đã tồn tại.");
                    url = AppConstants.ACCOUNT_CREATE_PAGE;
                } else {
                    AccountDTO account = new AccountDTO(
                            username, hashPassword, address, phoneNumber,
                            fullname, email, role, imageUrl, status
                    );

                    accountDAO.create(account);
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ACCOUNT);
                    return;
                }

            } else if (AppConstants.ACTION_EDIT_ACCOUNT.equals(action)) {
                String id = request.getParameter("id");
                AccountDTO account = accountDAO.getAccountById(id);

                if (account == null) {
                    request.setAttribute("error", "Account không tồn tại hoặc đã bị xóa.");
                    request.setAttribute("listAccount", accountDAO.getAllAccounts());
                    url = AppConstants.ACCOUNT_LIST_PAGE;
                } else {
                    request.setAttribute("account", account);
                    url = AppConstants.ACCOUNT_EDIT_PAGE;
                }

            } else if (AppConstants.ACTION_UPDATE_ACCOUNT.equals(action)) {
                String id = request.getParameter("id");
                AccountDTO account = accountDAO.getAccountById(id);

                if (account != null) {
                    account.setUsername(request.getParameter("username"));
                    account.setHashPassword(request.getParameter("hashPassword"));
                    account.setAddress(request.getParameter("address"));
                    account.setPhoneNumber(request.getParameter("phoneNumber"));
                    account.setFullname(request.getParameter("fullname"));
                    account.setEmail(request.getParameter("email"));
                    account.setRole(request.getParameter("role"));
                    account.setImageUrl(request.getParameter("imageUrl"));
                    account.setStatus(request.getParameter("status"));

                    accountDAO.update(account);
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ACCOUNT);
                return;

            } else if (AppConstants.ACTION_DELETE_ACCOUNT.equals(action)) {
                String id = request.getParameter("id");
                if (id != null && !id.trim().isEmpty()) {
                    accountDAO.delete(id);
                }

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ACCOUNT);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi tại AccountController: " + e.getMessage());
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