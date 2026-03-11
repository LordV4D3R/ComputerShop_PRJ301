<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="sidebar">
    <div class="sidebar-header">
        <a href="${pageContext.request.contextPath}/MainController?action=showShop">🛒 TechShop</a>
    </div>
    <div style="padding: 10px 20px; background: #eee; font-size: 13px; color: #555;">
        Xin chào, <strong>${sessionScope.loginUser.username}</strong> (Staff)
    </div>
    <ul class="sidebar-menu">
        <li><a href="${pageContext.request.contextPath}/MainController?action=listAllOrders">📋 Quản lý đơn hàng</a></li>
        <li><a href="${pageContext.request.contextPath}/MainController?action=listAllProducts">💻 Quản lý sản phẩm</a></li>
        <li><a href="${pageContext.request.contextPath}/MainController?action=logout" style="color: #c62828;">🚪 Đăng xuất</a></li>
    </ul>
</div>