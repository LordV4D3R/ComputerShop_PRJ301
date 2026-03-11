<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý đơn hàng | Staff</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body>
    <div class="dashboard-wrapper">
        
        <jsp:include page="/includes/sidebar_staff.jsp" />

        <div class="main-content">
            <h2 class="page-title">Quản lý đơn hàng chờ duyệt</h2>
            
            <c:if test="${not empty sessionScope.successMessage}">
                <div style="background: #d4edda; color: #155724; padding: 12px; margin-bottom: 15px; border-radius: 4px;">
                    ${sessionScope.successMessage}
                </div>
                <c:remove var="successMessage" scope="session"/>
            </c:if>

            <div class="table-wrapper">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>Mã ĐH</th>
                            <th>Khách hàng</th>
                            <th>SĐT</th>
                            <th>Tổng tiền</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${listOrders}">
                            <tr>
                                <td><strong>#${order.id}</strong></td>
                                <td>${order.fullname}</td>
                                <td>${order.phoneNumber}</td>
                                <td style="color: #d70018; font-weight: bold;">
                                    <fmt:formatNumber value="${order.total}" type="number" pattern="#,##0"/> đ
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${order.status == 'PENDING'}">
                                            <span class="status-badge status-pending">Chờ duyệt</span>
                                        </c:when>
                                        <c:when test="${order.status == 'APPROVED'}">
                                            <span class="status-badge status-approved">Đã duyệt</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="status-badge status-cancelled">Đã hủy</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${order.status == 'PENDING'}">
                                        <form action="${pageContext.request.contextPath}/MainController" method="post" style="display:inline;">
                                            <input type="hidden" name="action" value="approveOrder">
                                            <input type="hidden" name="orderId" value="${order.id}">
                                            <button type="submit" class="btn-sm btn-approve" onclick="return confirm('Duyệt đơn này và trừ kho?');">Duyệt</button>
                                        </form>
                                        
                                        <form action="${pageContext.request.contextPath}/MainController" method="post" style="display:inline;">
                                            <input type="hidden" name="action" value="cancelOrder">
                                            <input type="hidden" name="orderId" value="${order.id}">
                                            <button type="submit" class="btn-sm btn-cancel" onclick="return confirm('Hủy đơn hàng này?');">Hủy</button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            
            <c:if test="${empty listOrders}">
                <div style="text-align: center; padding: 40px; color: #777;">Không có đơn hàng nào.</div>
            </c:if>

        </div>
    </div>
</body>
</html>