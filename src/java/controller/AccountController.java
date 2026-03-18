package controller;

import models.AccountDAO;
import models.AccountDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.AppConstants;
import utils.PasswordUtils;
import javax.servlet.http.Part;
import java.io.File;
import java.util.UUID;
import java.nio.file.Paths;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // Tối đa 10MB
        maxRequestSize = 1024 * 1024 * 50 // Tối đa 50MB
)
public class AccountController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");
        String url = AppConstants.LOGIN_PAGE;

        try {
            AccountDAO accountDAO = new AccountDAO();

            if (action == null || AppConstants.ACTION_SHOW_LOGIN.equals(action)) {
                url = AppConstants.LOGIN_PAGE;

            } else if (AppConstants.ACTION_SHOW_REGISTER.equals(action)) {
                url = AppConstants.REGISTER_PAGE;

            } else if (AppConstants.ACTION_LOGIN.equals(action)) {
                String username = trim(request.getParameter("username"));
                String password = trim(request.getParameter("hashPassword"));

                if (username.isEmpty() || password.isEmpty()) {
                    request.setAttribute("error", "Vui lòng nhập đầy đủ username và password.");
                    request.setAttribute("username", username);
                    url = AppConstants.LOGIN_PAGE;
                } else {
                    String hashedPassword = PasswordUtils.hashPassword(password);
                    AccountDTO loginUser = accountDAO.checkLogin(username, hashedPassword);

                    if (loginUser == null) {
                        request.setAttribute("error", "Sai tài khoản, mật khẩu hoặc tài khoản không hoạt động.");
                        request.setAttribute("username", username);
                        url = AppConstants.LOGIN_PAGE;
                    } else {
                        request.getSession().setAttribute(AppConstants.SESSION_LOGIN_USER, loginUser);
                        request.getSession().setAttribute("successMessage", "Đăng nhập thành công.");
                        redirectByRole(response, loginUser);
                        return;
                    }
                }

            } else if (AppConstants.ACTION_LOGOUT.equals(action)) {
                request.getSession().removeAttribute(AppConstants.SESSION_LOGIN_USER);
                request.getSession().setAttribute("successMessage", "Đăng xuất thành công.");
                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_LOGIN);
                return;

            } else if (AppConstants.ACTION_REGISTER.equals(action)) {
                String username = trim(request.getParameter("username"));
                String password = trim(request.getParameter("hashPassword"));
                String confirmPassword = trim(request.getParameter("confirmPassword"));
                String fullname = trim(request.getParameter("fullname"));
                String email = trim(request.getParameter("email"));
                String phoneNumber = trim(request.getParameter("phoneNumber"));
                String address = trim(request.getParameter("address"));

                request.setAttribute("username", username);
                request.setAttribute("fullname", fullname);
                request.setAttribute("email", email);
                request.setAttribute("phoneNumber", phoneNumber);
                request.setAttribute("address", address);

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    request.setAttribute("error", "Username, password và confirm password không được để trống.");
                    url = AppConstants.REGISTER_PAGE;

                } else if (!password.equals(confirmPassword)) {
                    request.setAttribute("error", "Confirm password không khớp.");
                    url = AppConstants.REGISTER_PAGE;

                } else {
                    AccountDTO existed = accountDAO.getAccountByUsername(username);
                    if (existed != null) {
                        request.setAttribute("error", "Username đã tồn tại.");
                        url = AppConstants.REGISTER_PAGE;
                    } else {
                        AccountDTO account = new AccountDTO(
                                username,
                                PasswordUtils.hashPassword(password),
                                address,
                                phoneNumber,
                                fullname,
                                email,
                                "CUSTOMER",
                                "",
                                "ACTIVE"
                        );

                        boolean success = accountDAO.create(account);
                        if (success) {
                            request.getSession().setAttribute("successMessage", "Đăng ký thành công. Bạn hãy đăng nhập.");
                            response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_LOGIN);
                            return;
                        } else {
                            request.setAttribute("error", "Đăng ký thất bại.");
                            url = AppConstants.REGISTER_PAGE;
                        }
                    }
                }

            } else if (AppConstants.ACTION_SHOW_ACCOUNT.equals(action)) {
                AccountDTO currentUser = getLoginUser(request);
                if (currentUser == null) {
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_LOGIN);
                    return;
                }
                url = "profile.jsp";

            } else if (AppConstants.ACTION_UPDATE_PROFILE.equals(action)) {
                AccountDTO currentUser = getLoginUser(request);
                if (currentUser == null) {
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_LOGIN);
                    return;
                }

                String fullname = trim(request.getParameter("fullname"));
                String email = trim(request.getParameter("email"));
                String phoneNumber = trim(request.getParameter("phoneNumber"));
                String address = trim(request.getParameter("address"));
                String newPassword = trim(request.getParameter("newPassword"));

                Part filePart = request.getPart("avatarFile");
                String imageUrl = currentUser.getImageUrl();

                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

                    String uploadPath = request.getServletContext().getRealPath("/images/users");
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    filePart.write(uploadPath + File.separator + uniqueFileName);
                    imageUrl = "images/users/" + uniqueFileName;
                }

                // CẬP NHẬT THÔNG TIN
                currentUser.setFullname(fullname);
                currentUser.setEmail(email);
                currentUser.setPhoneNumber(phoneNumber);
                currentUser.setAddress(address);
                currentUser.setImageUrl(imageUrl);

                if (!newPassword.isEmpty()) {
                    currentUser.setHashPassword(PasswordUtils.hashPassword(newPassword));
                }

                accountDAO.update(currentUser);
                request.getSession().setAttribute(AppConstants.SESSION_LOGIN_USER, currentUser);
                request.getSession().setAttribute("successMessage", "Cập nhật thông tin thành công!");

                response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_ACCOUNT);
                return;
                // ============ KẾT THÚC: PHẦN CẬP NHẬT THÔNG TIN CÁ NHÂN ============

                // GIỮ NGUYÊN KHỐI ELSE DÀNH CHO ADMIN BÊN DƯỚI
            } else {
                AccountDTO loginUser = getLoginUser(request);
                if (!isAdmin(loginUser)) {
                    request.getSession().setAttribute("errorMessage", "Chỉ ADMIN mới được quản lý tài khoản.");
                    response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_LOGIN);
                    return;
                }

                if (AppConstants.ACTION_LIST_ACCOUNT.equals(action)) {
                    List<AccountDTO> listAccount = accountDAO.getAllAccounts();
                    request.setAttribute("listAccount", listAccount);
                    url = AppConstants.ACCOUNT_LIST_PAGE;

                } else if (AppConstants.ACTION_SHOW_CREATE_ACCOUNT.equals(action)) {
                    url = AppConstants.ACCOUNT_CREATE_PAGE;

                } else if (AppConstants.ACTION_INSERT_ACCOUNT.equals(action)) {
                    String username = trim(request.getParameter("username"));
                    String password = trim(request.getParameter("hashPassword"));
                    String address = trim(request.getParameter("address"));
                    String phoneNumber = trim(request.getParameter("phoneNumber"));
                    String fullname = trim(request.getParameter("fullname"));
                    String email = trim(request.getParameter("email"));
                    String role = normalizeRole(request.getParameter("role"));
                    String imageUrl = trim(request.getParameter("imageUrl"));
                    String status = trim(request.getParameter("status"));

                    if (username.isEmpty() || password.isEmpty()) {
                        request.setAttribute("error", "Username và password không được để trống.");
                        url = AppConstants.ACCOUNT_CREATE_PAGE;
                    } else {
                        AccountDTO existed = accountDAO.getAccountByUsername(username);
                        if (existed != null) {
                            request.setAttribute("error", "Username đã tồn tại.");
                            url = AppConstants.ACCOUNT_CREATE_PAGE;
                        } else {
                            AccountDTO account = new AccountDTO(
                                    username,
                                    PasswordUtils.hashPassword(password),
                                    address,
                                    phoneNumber,
                                    fullname,
                                    email,
                                    role,
                                    imageUrl,
                                    status
                            );

                            accountDAO.create(account);
                            response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ACCOUNT);
                            return;
                        }
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
                        account.setUsername(trim(request.getParameter("username")));
                        account.setAddress(trim(request.getParameter("address")));
                        account.setPhoneNumber(trim(request.getParameter("phoneNumber")));
                        account.setFullname(trim(request.getParameter("fullname")));
                        account.setEmail(trim(request.getParameter("email")));
                        account.setRole(normalizeRole(request.getParameter("role")));
                        account.setImageUrl(trim(request.getParameter("imageUrl")));
                        account.setStatus(trim(request.getParameter("status")));

                        String newPassword = trim(request.getParameter("newPassword"));
                        if (!newPassword.isEmpty()) {
                            account.setHashPassword(PasswordUtils.hashPassword(newPassword));
                        }

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
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi tại AccountController: " + e.getMessage());
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    private AccountDTO getLoginUser(HttpServletRequest request) {
        Object obj = request.getSession().getAttribute(AppConstants.SESSION_LOGIN_USER);
        if (obj instanceof AccountDTO) {
            return (AccountDTO) obj;
        }
        return null;
    }

    private boolean isAdmin(AccountDTO account) {
        return account != null && "ADMIN".equalsIgnoreCase(trim(account.getRole()));
    }

    private boolean isStaff(AccountDTO account) {
        return account != null && "STAFF".equalsIgnoreCase(trim(account.getRole()));
    }

    private void redirectByRole(HttpServletResponse response, AccountDTO loginUser) throws IOException {
        if (isAdmin(loginUser)) {
            response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_ACCOUNT);
        } else if (isStaff(loginUser)) {
            response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_LIST_PRODUCT);
        } else {
            response.sendRedirect(AppConstants.MAIN_CONTROLLER + "?action=" + AppConstants.ACTION_SHOW_SHOP);
        }
    }

    private static String trim(String value) {
        return value == null ? "" : value.trim();
    }

    private String normalizeRole(String role) {
        String normalizedRole = trim(role).toUpperCase();

        if ("USER".equals(normalizedRole)) {
            return "CUSTOMER";
        }

        if (!"ADMIN".equals(normalizedRole)
                && !"STAFF".equals(normalizedRole)
                && !"CUSTOMER".equals(normalizedRole)) {
            return "CUSTOMER";
        }

        return normalizedRole;
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
