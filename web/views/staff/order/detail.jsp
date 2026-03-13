<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết đơn hàng | Staff</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body>

    <div class="dashboard-wrapper">
        <jsp:include page="/includes/sidebar_staff.jsp" />

        <div class="main-content">
            <h2 class="page-title">Chi tiết đơn hàng: <span style="color: #1976d2;">#${order.id}</span></h2>

            <div class="form-container" style="margin-bottom: 20px;">
                <h3 style="margin-top: 0; border-bottom: 1px solid #eee; padding-bottom: 10px;">Thông tin khách hàng</h3>
                <div class="form-grid">
                    <div><strong>Trạng thái:</strong> 
                        <c:choose>
                            <c:when test="${order.status eq 'PENDING'}"><span class="status-badge status-pending">PENDING</span></c:when>
                            <c:when test="${order.status eq 'APPROVED'}"><span class="status-badge status-approved">APPROVED</span></c:when>
                            <c:when test="${order.status eq 'CANCELLED'}"><span class="status-badge status-cancelled">CANCELLED</span></c:when>
                            <c:otherwise>${order.status}</c:otherwise>
                        </c:choose>
                    </div>
                    <div><strong>Khách hàng:</strong> ${order.fullname}</div>
                    <div><strong>Số điện thoại:</strong> ${order.phoneNumber}</div>
                    <div><strong>Email:</strong> ${order.email}</div>
                    <div style="grid-column: 1 / -1;"><strong>Tổng tiền:</strong> <span style="color: #d70018; font-weight: bold; font-size: 18px;"><fmt:formatNumber value="${order.totalPrice}" type="number" pattern="#,##0"/> đ</span></div>
                    <div style="grid-column: 1 / -1;"><strong>Địa chỉ:</strong> ${order.address}</div>
                </div>

                <div style="margin-top: 20px; padding-top: 15px; border-top: 1px solid #eee;">
                    <a class="btn" style="background: #6c757d; color: white;" href="${pageContext.request.contextPath}/MainController?action=listOrder">Quay lại</a>
                    <c:if test="${order.status eq 'PENDING'}">
                        <a class="btn" style="background: #28a745; color: white; margin-left: 10px;" 
                           href="${pageContext.request.contextPath}/MainController?action=approveOrder&id=${order.id}"
                           onclick="return confirm('Duyệt đơn hàng này?');">Duyệt Đơn (Approve)</a>
                        <a class="btn" style="background: #dc3545; color: white; margin-left: 10px;" 
                           href="${pageContext.request.contextPath}/MainController?action=cancelOrder&id=${order.id}"
                           onclick="return confirm('Hủy đơn hàng này?');">Hủy Đơn (Cancel)</a>
                    </c:if>
                </div>
            </div>

            <h3 style="margin-bottom: 15px; color: #333;">Sản phẩm trong đơn</h3>
            <div class="table-wrapper">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th style="width: 100px; text-align: center;">Ảnh</th>
                            <th>Sản phẩm</th>
                            <th>Đơn giá</th>
                            <th style="text-align: center;">Số lượng</th>
                            <th>Thành tiền</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty orderItemViews}">
                                <c:forEach var="item" items="${orderItemViews}">
                                    <tr>
                                        <td style="text-align: center;">
                                            <c:choose>
                                                <c:when test="${not empty item.imageUrl}">
                                                    <img src="${pageContext.request.contextPath}/${item.imageUrl}" alt="${item.productName}" width="60" style="border-radius: 4px; object-fit: contain;">
                                                </c:when>
                                                <c:otherwise><span style="color:#999; font-size:12px;">No Image</span></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <strong>${item.productName}</strong><br>
                                            <span style="color:#777; font-size:12px;">Mã SP: ${item.productId}</span>
                                        </td>
                                        <td><fmt:formatNumber value="${item.price}" type="number" pattern="#,##0"/> đ</td>
                                        <td style="text-align: center; font-weight: bold;">${item.quantity}</td>
                                        <td style="color: #d70018; font-weight: bold;">
                                            <fmt:formatNumber value="${item.subtotal}" type="number" pattern="#,##0"/> đ
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="5" style="text-align:center; padding:30px; color:#777;">Đơn hàng này chưa có sản phẩm.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

        </div>
    </div>

</body>
</html>