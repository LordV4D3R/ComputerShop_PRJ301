<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đơn hàng của tôi | TechShop</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
    </head>
    <body>
        <div class="dashboard-wrapper">

            <jsp:include page="/includes/sidebar_user.jsp" />

            <div class="main-content">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                    <h2 class="page-title" style="margin-bottom: 0; border: none;">Đơn hàng của tôi</h2>
                    <a href="${pageContext.request.contextPath}/MainController?action=showShop" class="btn btn-primary">🛒 Tiếp tục mua hàng</a>
                </div>

                <c:choose>
                    <c:when test="${empty myOrders}">
                        <div style="background: #fff; padding: 40px; text-align: center; border-radius: 8px; border: 1px solid #eee;">
                            <p style="color: #666; font-size: 16px;">Bạn chưa có đơn hàng nào.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-wrapper">
                            <table class="data-table">
                                <thead>
                                    <tr>
                                        <th>Mã đơn</th>
                                        <th>Ngày tạo</th>
                                        <th>Người nhận</th>
                                        <th>Tổng tiền</th>
                                        <th>Trạng thái</th>
                                        <th style="text-align: center;">Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="order" items="${myOrders}">
                                        <tr>
                                            <td><strong>#${order.id}</strong></td>
                                            <td><fmt:formatDate value="${order.createdDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                            <td>
                                                ${order.fullname}<br>
                                                <span style="font-size: 12px; color: #777;">${order.phoneNumber}</span>
                                            </td>
                                            <td style="color: #d32f2f; font-weight: bold;">
                                                <fmt:formatNumber value="${order.totalPrice}" type="number" pattern="#,##0"/> đ
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${order.status eq 'PENDING'}"><span class="status-badge status-pending">Đang xử lý</span></c:when>
                                                    <c:when test="${order.status eq 'APPROVED'}"><span class="status-badge status-approved">Thành công</span></c:when>
                                                    <c:when test="${order.status eq 'CANCELLED'}"><span class="status-badge status-cancelled">Đã hủy</span></c:when>
                                                    <c:otherwise><span class="status-badge status-pending">${order.status}</span></c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td style="text-align: center;">
                                                <a class="btn-sm btn-view" href="${pageContext.request.contextPath}/MainController?action=showMyOrderDetail&id=${order.id}">
                                                    Xem chi tiết
                                                </a>

                                                <c:if test="${order.status eq 'PENDING'}">
                                                    <a class="btn-sm btn-edit" style="margin-left: 6px;"
                                                       href="${pageContext.request.contextPath}/MainController?action=showQrPayment&orderId=${order.id}">
                                                        Thanh toán QR
                                                    </a>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </body>
</html>