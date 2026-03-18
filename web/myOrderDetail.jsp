<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết đơn hàng | TechShop</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
    </head>
    <body>
        <div class="dashboard-wrapper">
            <jsp:include page="/includes/sidebar_user.jsp" />

            <div class="main-content">
                <h2 class="page-title">Chi tiết đơn hàng: <span style="color: #1976d2;">#${order.id}</span></h2>

                <c:if test="${not empty order}">
                    <div class="form-container" style="max-width: 100%; margin-bottom: 20px;">
                        <div class="form-grid">
                            <div class="form-group">
                                <label>Trạng thái đơn hàng:</label>
                                <c:choose>
                                    <c:when test="${order.status eq 'PENDING'}"><span class="status-badge status-pending">Đang xử lý</span></c:when>
                                    <c:when test="${order.status eq 'APPROVED'}"><span class="status-badge status-approved">Thành công</span></c:when>
                                    <c:when test="${order.status eq 'CANCELLED'}"><span class="status-badge status-cancelled">Đã hủy</span></c:when>
                                    <c:otherwise><span class="status-badge status-pending">${order.status}</span></c:otherwise>
                                </c:choose>
                            </div>
                            <div class="form-group"><label>Ngày tạo:</label> <fmt:formatDate value="${order.createdDate}" pattern="dd/MM/yyyy HH:mm"/></div>
                            <div class="form-group"><label>Người nhận:</label> ${order.fullname}</div>
                            <div class="form-group"><label>Số điện thoại:</label> ${order.phoneNumber}</div>
                            <div class="form-group full-width"><label>Địa chỉ giao hàng:</label> ${order.address}</div>
                            <div class="form-group full-width" style="border-top: 1px solid #eee; padding-top: 15px;">
                                <label>Tổng thanh toán:</label> 
                                <span style="color: #d32f2f; font-size: 20px; font-weight: bold;"><fmt:formatNumber value="${order.totalPrice}" type="number" pattern="#,##0"/> đ</span>
                            </div>
                        </div>
                    </div>

                    <div class="table-wrapper">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th style="width: 80px; text-align: center;">Ảnh</th>
                                    <th>Tên sản phẩm</th>
                                    <th style="text-align: center;">Số lượng</th>
                                    <th>Đơn giá</th>
                                    <th>Thành tiền</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${orderItemViews}">
                                    <tr>
                                        <td style="text-align: center;">
                                            <c:if test="${not empty item.imageUrl}">
                                                <img src="${pageContext.request.contextPath}/${item.imageUrl}" alt="${item.productName}" width="60" style="border-radius: 4px; object-fit: contain;">
                                            </c:if>
                                        </td>
                                        <td><strong>${item.productName}</strong></td>
                                        <td style="text-align: center; font-weight: bold;">${item.quantity}</td>
                                        <td><fmt:formatNumber value="${item.price}" type="number" pattern="#,##0"/> đ</td>
                                        <td style="color: #d32f2f; font-weight: bold;">
                                            <fmt:formatNumber value="${item.price * item.quantity}" type="number" pattern="#,##0"/> đ
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div style="margin-top: 20px; display: flex; gap: 10px; flex-wrap: wrap;">
                        <a class="btn" style="background: #6c757d; color: white;"
                           href="${pageContext.request.contextPath}/MainController?action=listMyOrder">
                            ← Quay lại danh sách
                        </a>

                        <c:if test="${order.status eq 'PENDING'}">
                            <a class="btn btn-primary"
                               href="${pageContext.request.contextPath}/MainController?action=showQrPayment&orderId=${order.id}">
                                Thanh toán QR
                            </a>
                        </c:if>
                    </div>
                </c:if>
            </div>
        </div>
    </body>
</html>