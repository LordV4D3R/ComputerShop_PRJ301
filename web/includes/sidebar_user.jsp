<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="sidebar">
    <div class="sidebar-header">
        <a href="${pageContext.request.contextPath}/MainController?action=showHome">🛒 TechShop</a>
    </div>
    
    <div style="padding: 10px 20px; background: #eee; font-size: 13px; color: #555;">
        Xin chào, <strong>${sessionScope.LOGIN_USER.fullname != null && not empty sessionScope.LOGIN_USER.fullname ? sessionScope.LOGIN_USER.fullname : sessionScope.LOGIN_USER.username}</strong> 
        <br><span style="font-size: 11px; color: #1976d2;">(Khách hàng)</span>
    </div>
    
    <ul class="sidebar-menu">
        <li><a href="${pageContext.request.contextPath}/MainController?action=showShop">🛒 Tiếp tục mua sắm</a></li>
        <li><a href="${pageContext.request.contextPath}/MainController?action=showAccount">👤 Thông tin cá nhân</a></li>
        <li><a href="${pageContext.request.contextPath}/MainController?action=listMyOrder">📦 Đơn hàng của tôi</a></li>
        
        <li style="margin-top: 20px; border-top: 1px solid #eee;">
            <a href="${pageContext.request.contextPath}/MainController?action=logout" style="color: #c62828;">🚪 Đăng xuất</a>
        </li>
    </ul>
</div>