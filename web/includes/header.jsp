<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header class="main-header">
    <div class="container header-container">
        <a href="${pageContext.request.contextPath}/MainController?action=showShop" class="header-logo">
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
            
            <%-- Giả sử biến session lưu user là 'loginUser', bạn điều chỉnh lại tên biến cho đúng với backend --%>
            <c:choose>
                <c:when test="${not empty sessionScope.loginUser}">
                    <a href="${pageContext.request.contextPath}/MainController?action=showAccount">👤 ${sessionScope.loginUser.username}</a>
                    <a href="${pageContext.request.contextPath}/MainController?action=logout">Đăng xuất</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/MainController?action=showLogin">Đăng nhập</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>