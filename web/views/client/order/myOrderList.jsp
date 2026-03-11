<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đơn hàng của tôi</title>
</head>
<body>
    <h1>Đơn hàng của tôi</h1>

    <p>
        <a href="${pageContext.request.contextPath}/MainController?action=showShop">Tiếp tục mua hàng</a>
    </p>

    <c:choose>
        <c:when test="${empty myOrders}">
            <p>Bạn chưa có đơn hàng nào.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8" cellspacing="0">
                <thead>
                    <tr>
                        <th>Mã đơn</th>
                        <th>Ngày tạo</th>
                        <th>Người nhận</th>
                        <th>SĐT</th>
                        <th>Tổng tiền</th>
                        <th>Trạng thái</th>
                        <th>Chi tiết</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${myOrders}">
                        <tr>
                            <td>${order.id}</td>
                            <td>
                                <fmt:formatDate value="${order.createdDate}" pattern="dd/MM/yyyy HH:mm"/>
                            </td>
                            <td>${order.fullname}</td>
                            <td>${order.phoneNumber}</td>
                            <td>
                                <fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/>
                            </td>
                            <td>${order.status}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/MainController?action=showMyOrderDetail&id=${order.id}">
                                    Xem chi tiết
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>