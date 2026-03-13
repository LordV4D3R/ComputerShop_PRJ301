<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
    /* CSS cho Dropdown menu của User */
    .user-dropdown {
        position: relative;
        display: inline-block;
    }
    
    .user-dropdown > a.user-greeting {
        cursor: pointer;
        display: flex;
        align-items: center;
        gap: 5px;
    }
    
    .dropdown-content {
        display: none;
        position: absolute;
        right: 0;
        top: 100%;
        background-color: #ffffff;
        min-width: 220px;
        box-shadow: 0px 8px 24px rgba(0,0,0,0.15);
        z-index: 9999;
        border-radius: 8px;
        overflow: hidden;
        border: 1px solid #eaeaea;
        margin-top: 15px; /* Đẩy menu xuống một chút cho đẹp */
    }
    
    /* Hiển thị dropdown khi di chuột (hover) vào cụm user-dropdown */
    .user-dropdown:hover .dropdown-content {
        display: block;
    }
    
    /* Cầu nối vô hình giúp chuột không bị trượt khi di chuyển từ tên xuống menu */
    .user-dropdown::after {
        content: "";
        position: absolute;
        width: 100%;
        height: 15px;
        bottom: -15px;
        left: 0;
    }

    /* Style cho các link trong dropdown */
    .dropdown-content a {
        color: #333 !important;
        padding: 14px 16px;
        text-decoration: none;
        display: flex;
        align-items: center;
        gap: 10px;
        font-size: 14px;
        border-bottom: 1px solid #f5f5f5;
        transition: all 0.2s ease;
    }
    
    .dropdown-content a:last-child {
        border-bottom: none;
    }
    
    .dropdown-content a:hover {
        background-color: #f4f6f8;
        color: #1976d2 !important;
        padding-left: 20px; /* Hiệu ứng thụt lề khi hover */
    }
</style>

<header class="main-header">
    <div class="container header-container">
        <a href="${pageContext.request.contextPath}/MainController?action=showHome" class="header-logo">
            💻 TechShop
        </a>
        
        <form class="header-search" action="${pageContext.request.contextPath}/MainController" method="get">
            <input type="hidden" name="action" value="showShop">
            <input type="text" name="keyword" value="${param.keyword}" placeholder="Bạn cần tìm laptop, linh kiện gì?">
            <button type="submit" class="btn btn-secondary">🔍</button>
        </form>

        <div class="header-actions">
            <a href="${pageContext.request.contextPath}/MainController?action=showCart">
                🛒 Giỏ hàng
            </a>
            
            <%-- Gom biến session để xử lý đồng nhất (bắt được cả LOGIN_USER và loginUser) --%>
            <c:set var="currentUser" value="${not empty sessionScope.LOGIN_USER ? sessionScope.LOGIN_USER : sessionScope.loginUser}" />

            <c:choose>
                <%-- NẾU ĐÃ ĐĂNG NHẬP --%>
                <c:when test="${not empty currentUser}">
                    <div class="user-dropdown">
                        <a class="user-greeting">
                            <%-- Hiển thị Fullname nếu có, không thì hiển thị Username --%>
                            👤 Xin chào, ${not empty currentUser.fullname ? currentUser.fullname : currentUser.username} ▾
                        </a>
                        
                        <div class="dropdown-content">
                            <a href="${pageContext.request.contextPath}/MainController?action=showAccount">
                                👤 Thông tin cá nhân
                            </a>
                            <a href="${pageContext.request.contextPath}/MainController?action=listMyOrder">
                                📦 Đơn hàng của tôi
                            </a>
                            
                            <%-- Chỉ hiển thị link Trang quản trị nếu tài khoản là Admin hoặc Staff --%>
                            <c:if test="${currentUser.role eq 'ADMIN' or currentUser.role eq 'STAFF'}">
                                <a href="${pageContext.request.contextPath}/MainController?action=listOrder">
                                    ⚙️ Trang quản trị
                                </a>
                            </c:if>
                            
                            <a href="${pageContext.request.contextPath}/MainController?action=logout" style="color: #d32f2f !important; font-weight: bold;">
                                🚪 Đăng xuất
                            </a>
                        </div>
                    </div>
                </c:when>
                
                <%-- NẾU CHƯA ĐĂNG NHẬP --%>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/MainController?action=showLogin">Đăng nhập</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>