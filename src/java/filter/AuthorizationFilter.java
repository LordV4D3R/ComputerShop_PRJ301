package filter;

import models.AccountDTO;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.AppConstants;

public class AuthorizationFilter implements Filter {

    private static final Set<String> PUBLIC_ACTIONS = new HashSet<String>(Arrays.asList(
            AppConstants.ACTION_SHOW_HOME,
            AppConstants.ACTION_SHOW_LOGIN,
            AppConstants.ACTION_LOGIN,
            AppConstants.ACTION_LOGOUT,
            AppConstants.ACTION_SHOW_REGISTER,
            AppConstants.ACTION_REGISTER,
            AppConstants.ACTION_SHOW_SHOP,
            AppConstants.ACTION_SHOW_PRODUCT_DETAIL,
            AppConstants.ACTION_ADD_TO_CART,
            AppConstants.ACTION_SHOW_CART,
            AppConstants.ACTION_INCREASE_CART_ITEM,
            AppConstants.ACTION_DECREASE_CART_ITEM,
            AppConstants.ACTION_REMOVE_FROM_CART
    ));

    private static final Set<String> ADMIN_ACTIONS = new HashSet<String>(Arrays.asList(
            AppConstants.ACTION_LIST_ACCOUNT,
            AppConstants.ACTION_SHOW_CREATE_ACCOUNT,
            AppConstants.ACTION_INSERT_ACCOUNT,
            AppConstants.ACTION_DELETE_ACCOUNT,
            AppConstants.ACTION_EDIT_ACCOUNT,
            AppConstants.ACTION_UPDATE_ACCOUNT
    ));

    private static final Set<String> STAFF_OR_ADMIN_ACTIONS = new HashSet<String>(Arrays.asList(
            AppConstants.ACTION_LIST_PRODUCT,
            AppConstants.ACTION_SHOW_CREATE_PRODUCT,
            AppConstants.ACTION_INSERT_PRODUCT,
            AppConstants.ACTION_DELETE_PRODUCT,
            AppConstants.ACTION_EDIT_PRODUCT,
            AppConstants.ACTION_UPDATE_PRODUCT,
            AppConstants.ACTION_LIST_CATEGORY,
            AppConstants.ACTION_SHOW_CREATE_CATEGORY,
            AppConstants.ACTION_INSERT_CATEGORY,
            AppConstants.ACTION_DELETE_CATEGORY,
            AppConstants.ACTION_EDIT_CATEGORY,
            AppConstants.ACTION_UPDATE_CATEGORY,
            AppConstants.ACTION_LIST_ORDER,
            AppConstants.ACTION_SHOW_CREATE_ORDER,
            AppConstants.ACTION_INSERT_ORDER,
            AppConstants.ACTION_DELETE_ORDER,
            AppConstants.ACTION_EDIT_ORDER,
            AppConstants.ACTION_UPDATE_ORDER,
            AppConstants.ACTION_SHOW_ORDER_DETAIL,
            AppConstants.ACTION_APPROVE_ORDER,
            AppConstants.ACTION_CANCEL_ORDER,
            AppConstants.ACTION_LIST_ORDER_ITEM,
            AppConstants.ACTION_SHOW_CREATE_ORDER_ITEM,
            AppConstants.ACTION_INSERT_ORDER_ITEM,
            AppConstants.ACTION_DELETE_ORDER_ITEM,
            AppConstants.ACTION_EDIT_ORDER_ITEM,
            AppConstants.ACTION_UPDATE_ORDER_ITEM,
            AppConstants.ACTION_LIST_REVIEW,
            AppConstants.ACTION_DELETE_REVIEW,
            AppConstants.ACTION_EDIT_REVIEW,
            AppConstants.ACTION_UPDATE_REVIEW
    ));

    private static final Set<String> CUSTOMER_ONLY_ACTIONS = new HashSet<String>(Arrays.asList(
            AppConstants.ACTION_SHOW_CHECKOUT,
            AppConstants.ACTION_PLACE_ORDER_FROM_CART,
            AppConstants.ACTION_LIST_MY_ORDER,
            AppConstants.ACTION_SHOW_MY_ORDER_DETAIL,
            AppConstants.ACTION_LIST_WISHLIST,
            AppConstants.ACTION_SHOW_CREATE_WISHLIST,
            AppConstants.ACTION_INSERT_WISHLIST,
            AppConstants.ACTION_DELETE_WISHLIST,
            AppConstants.ACTION_EDIT_WISHLIST,
            AppConstants.ACTION_UPDATE_WISHLIST,
            AppConstants.ACTION_SHOW_CREATE_REVIEW,
            AppConstants.ACTION_INSERT_REVIEW
    ));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
//        res.setContentType("text/html; charset=UTF-8");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String contextPath = request.getContextPath();
        String requestUri = request.getRequestURI();
        String path = requestUri.substring(contextPath.length());
        String action = trim(request.getParameter("action"));

        AccountDTO loginUser = getLoginUser(request);

        if (path == null || path.isEmpty()) {
            path = "/";
        }

        if (isStaticResource(path) || isPublicPage(path) || "/".equals(path)) {
            chain.doFilter(req, res);
            return;
        }
        res.setContentType("text/html; charset=UTF-8");

        if (isAdminView(path)) {
            if (isAdmin(loginUser)) {
                chain.doFilter(req, res);
            } else {
                handleUnauthorized(request, response, loginUser);
            }
            return;
        }

        if (isStaffView(path)) {
            if (isAdminOrStaff(loginUser)) {
                chain.doFilter(req, res);
            } else {
                handleUnauthorized(request, response, loginUser);
            }
            return;
        }

        if (isCustomerOnlyView(path)) {
            if (isCustomer(loginUser)) {
                chain.doFilter(req, res);
            } else {
                handleCustomerOnlyUnauthorized(request, response, loginUser);
            }
            return;
        }

        if (isControllerPath(path)) {
            if ("/MainController".equals(path) && action.isEmpty()) {
                chain.doFilter(req, res);
                return;
            }

            if (action.isEmpty()) {
                handleUnauthorized(request, response, loginUser);
                return;
            }

            if (PUBLIC_ACTIONS.contains(action)) {
                chain.doFilter(req, res);
                return;
            }

            if (ADMIN_ACTIONS.contains(action)) {
                if (isAdmin(loginUser)) {
                    chain.doFilter(req, res);
                } else {
                    handleUnauthorized(request, response, loginUser);
                }
                return;
            }

            if (STAFF_OR_ADMIN_ACTIONS.contains(action)) {
                if (isAdminOrStaff(loginUser)) {
                    chain.doFilter(req, res);
                } else {
                    handleUnauthorized(request, response, loginUser);
                }
                return;
            }

            if (CUSTOMER_ONLY_ACTIONS.contains(action)) {
                if (isCustomer(loginUser)) {
                    chain.doFilter(req, res);
                } else {
                    handleCustomerOnlyUnauthorized(request, response, loginUser);
                }
                return;
            }

            chain.doFilter(req, res);
            return;
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
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

    private boolean isCustomer(AccountDTO account) {
        return account != null && "CUSTOMER".equalsIgnoreCase(trim(account.getRole()));
    }

    private boolean isAdminOrStaff(AccountDTO account) {
        return isAdmin(account) || isStaff(account);
    }

    private boolean isStaticResource(String path) {
        return path.startsWith("/assets/")
                || path.startsWith("/images/")
                || path.endsWith(".css")
                || path.endsWith(".js")
                || path.endsWith(".png")
                || path.endsWith(".jpg")
                || path.endsWith(".jpeg")
                || path.endsWith(".gif")
                || path.endsWith(".svg")
                || path.endsWith(".webp")
                || path.endsWith(".ico");
    }

    private boolean isPublicPage(String path) {
        return "/login.jsp".equals(path)
                || "/register.jsp".equals(path);
    }

    private boolean isAdminView(String path) {
        return path.startsWith("/views/admin/");
    }

    private boolean isStaffView(String path) {
        return path.startsWith("/views/staff/");
    }

    private boolean isCustomerOnlyView(String path) {
        return path.startsWith("/views/client/wishlist/")
                || path.startsWith("/views/client/review/")
                || "/views/client/order/myOrderList.jsp".equals(path)
                || "/views/client/order/myOrderDetail.jsp".equals(path);
    }

    private boolean isControllerPath(String path) {
        return "/MainController".equals(path)
                || "/AccountController".equals(path)
                || "/ProductController".equals(path)
                || "/CategoryController".equals(path)
                || "/OrderController".equals(path)
                || "/OrderItemController".equals(path)
                || "/WishlistController".equals(path)
                || "/ReviewController".equals(path);
    }

    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("errorMessage", "Vui lòng đăng nhập để dùng tính năng này.");
        response.sendRedirect(request.getContextPath() + "/MainController?action=" + AppConstants.ACTION_SHOW_LOGIN);
    }

    private void handleUnauthorized(HttpServletRequest request, HttpServletResponse response, AccountDTO loginUser)
            throws IOException {

        if (loginUser == null) {
            redirectToLogin(request, response);
            return;
        }

        if (isAdmin(loginUser)) {
            response.sendRedirect(request.getContextPath() + "/MainController?action=" + AppConstants.ACTION_LIST_ACCOUNT);
        } else if (isStaff(loginUser)) {
            response.sendRedirect(request.getContextPath() + "/MainController?action=" + AppConstants.ACTION_LIST_PRODUCT);
        } else {
            response.sendRedirect(request.getContextPath() + "/MainController?action=" + AppConstants.ACTION_SHOW_SHOP);
        }
    }

    private void handleCustomerOnlyUnauthorized(HttpServletRequest request, HttpServletResponse response, AccountDTO loginUser)
            throws IOException {

        if (loginUser == null) {
            request.getSession().setAttribute("errorMessage", "Vui lòng đăng nhập bằng tài khoản CUSTOMER để dùng tính năng này.");
            response.sendRedirect(request.getContextPath() + "/MainController?action=" + AppConstants.ACTION_SHOW_LOGIN);
            return;
        }

        request.getSession().setAttribute("errorMessage", "Chỉ tài khoản CUSTOMER mới được dùng tính năng này.");
        response.sendRedirect(request.getContextPath() + "/MainController?action=" + AppConstants.ACTION_SHOW_SHOP);
    }

    private static String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
