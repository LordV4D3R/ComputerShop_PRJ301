<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách đơn hàng | Staff</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body>
    <div class="dashboard-wrapper">
        
        <jsp:include page="/includes/sidebar_staff.jsp" />

        <div class="main-content">
            <h2 class="page-title">Danh sách đơn hàng</h2>

            <c:if test="${not empty sessionScope.successMessage}">
                <div style="background: #d4edda; color: #155724; padding: 12px; border-radius: 6px; margin-bottom: 16px;">
                    ${sessionScope.successMessage}
                </div>
                <c:remove var="successMessage" scope="session"/>
            </c:if>

            <c:if test="${not empty sessionScope.errorMessage}">
                <div style="background: #ffebee; color: #c62828; padding: 12px; border-radius: 6px; margin-bottom: 16px;">
                    ${sessionScope.errorMessage}
                </div>
                <c:remove var="errorMessage" scope="session"/>
            </c:if>

            <div class="table-wrapper" style="overflow-x: auto;">
                <table class="data-table" style="min-width: 1200px;">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Mã ĐH</th>
                            <th>Khách hàng</th>
                            <th>Số điện thoại</th>
                            <th>Email</th>
                            <th>Địa chỉ</th>
                            <th>Tổng tiền</th>
                            <th>Trạng thái</th>
                            <th style="text-align:center;">Nghiệp vụ</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty listOrder}">
                                <c:forEach var="o" items="${listOrder}" varStatus="st">
                                    <tr>
                                        <td>${st.count}</td>
                                        <td><strong>#${o.id}</strong></td>
                                        <td>${o.fullname}</td>
                                        <td>${o.phoneNumber}</td>
                                        <td>${o.email}</td>
                                        <td>${o.address}</td>
                                        <td style="color: #d70018; font-weight: bold;">
                                            <fmt:formatNumber value="${o.totalPrice}" type="number" pattern="#,##0"/> đ
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${o.status eq 'PENDING'}">
                                                    <span class="status-badge status-pending">PENDING</span>
                                                </c:when>
                                                <c:when test="${o.status eq 'APPROVED'}">
                                                    <span class="status-badge status-approved">APPROVED</span>
                                                </c:when>
                                                <c:when test="${o.status eq 'CANCELLED'}">
                                                    <span class="status-badge status-cancelled">CANCELLED</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="status-badge status-pending">${o.status}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td style="text-align:center; white-space: nowrap;">
                                            <a class="btn-sm btn-view" href="${pageContext.request.contextPath}/MainController?action=showOrderDetail&id=${o.id}">
                                                Chi tiết
                                            </a>

                                            <c:choose>
                                                <c:when test="${o.status eq 'PENDING'}">
                                                    <a class="btn-sm" style="background: #28a745; color: white;" 
                                                       href="${pageContext.request.contextPath}/MainController?action=approveOrder&id=${o.id}"
                                                       onclick="return confirm('Duyệt đơn hàng này?');">
                                                        Duyệt
                                                    </a>
                                                    <a class="btn-sm btn-cancel"
                                                       href="${pageContext.request.contextPath}/MainController?action=cancelOrder&id=${o.id}"
                                                       onclick="return confirm('Hủy đơn hàng này?');">
                                                        Hủy
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <span style="color:#777; font-size: 13px; font-style: italic;">Đã xử lý</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="9" style="text-align:center; padding:30px; color:#777;">
                                        Chưa có đơn hàng nào.
                                    </td>
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