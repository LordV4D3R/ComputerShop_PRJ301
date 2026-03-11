<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết đơn hàng của tôi</title>
</head>
<body>
    <h1>Chi tiết đơn hàng</h1>

    <p>
        <a href="${pageContext.request.contextPath}/MainController?action=listMyOrder">← Quay lại đơn hàng của tôi</a>
    </p>

    <c:if test="${not empty order}">
        <h3>Thông tin đơn hàng</h3>
        <p><strong>Mã đơn:</strong> ${order.id}</p>
        <p><strong>Ngày tạo:</strong> <fmt:formatDate value="${order.createdDate}" pattern="dd/MM/yyyy HH:mm"/></p>
        <p><strong>Trạng thái:</strong> ${order.status}</p>
        <p><strong>Người nhận:</strong> ${order.fullname}</p>
        <p><strong>Số điện thoại:</strong> ${order.phoneNumber}</p>
        <p><strong>Email:</strong> ${order.email}</p>
        <p><strong>Địa chỉ:</strong> ${order.address}</p>
        <p>
            <strong>Tổng tiền:</strong>
            <fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/>
        </p>

        <h3>Sản phẩm</h3>
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
                <tr>
                    <th>Ảnh</th>
                    <th>Tên sản phẩm</th>
                    <th>Số lượng</th>
                    <th>Đơn giá</th>
                    <th>Thành tiền</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${orderItemViews}">
                    <tr>
                        <td>
                            <c:if test="${not empty item.imageUrl}">
                                <img src="${item.imageUrl}" alt="${item.productName}" width="80">
                            </c:if>
                        </td>
                        <td>${item.productName}</td>
                        <td>${item.quantity}</td>
                        <td>
                            <fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/>
                        </td>
                        <td>
                            <fmt:formatNumber value="${item.price * item.quantity}" type="number" groupingUsed="true"/>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>