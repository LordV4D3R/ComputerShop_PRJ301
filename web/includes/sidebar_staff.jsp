<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="sidebar">
    <div class="sidebar-header">
        <a href="${pageContext.request.contextPath}/MainController?action=showHome">🛒 TechShop</a>
    </div>
    
    <div style="padding: 10px 20px; background: #eee; font-size: 13px; color: #555;">
        Xin chào, <strong>${sessionScope.LOGIN_USER.username}</strong> (${sessionScope.LOGIN_USER.role})
    </div>
    
    <ul class="sidebar-menu">
        <li><a href="${pageContext.request.contextPath}/MainController?action=listOrder">📋 Quản lý đơn hàng</a></li>
        <li><a href="${pageContext.request.contextPath}/MainController?action=listProduct">💻 Quản lý sản phẩm</a></li>
        <li><a href="${pageContext.request.contextPath}/MainController?action=listCategory">🏷️ Quản lý danh mục</a></li>
        
        <%-- Tab Tài khoản: Chỉ hiển thị nếu role là ADMIN --%>
        <c:if test="${sessionScope.LOGIN_USER.role eq 'ADMIN'}">
            <li><a href="${pageContext.request.contextPath}/MainController?action=listAccount">👥 Quản lý tài khoản</a></li>
        </c:if>
        
        <li><a href="${pageContext.request.contextPath}/MainController?action=logout" style="color: #c62828;">🚪 Đăng xuất</a></li>
    </ul>
</div>