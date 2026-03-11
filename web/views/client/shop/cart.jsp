<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng | TechShop</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp" />

    <div class="container" style="margin-top: 30px;">
        <h2 style="margin-bottom: 20px;">Giỏ hàng của bạn</h2>

        <c:choose>
            <c:when test="${not empty cartItems}">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>Sản phẩm</th>
                            <th>Đơn giá</th>
                            <th style="text-align: center;">Số lượng</th>
                            <th>Thành tiền</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${cartItems}">
                            <tr>
                                <td>
                                    <strong>${item.product.name}</strong><br>
                                    <span style="font-size: 13px; color: #666;">Kho: ${item.product.stockQuantity}</span>
                                </td>
                                <td><fmt:formatNumber value="${item.product.price}" type="number" pattern="#,##0"/> đ</td>
                                <td style="text-align: center;">
                                    <a href="${pageContext.request.contextPath}/MainController?action=decreaseCartItem&productId=${item.product.id}" class="btn" style="background:#eee; padding:5px 10px;">-</a>
                                    <span style="margin: 0 10px; font-weight: bold;">${item.quantity}</span>
                                    <a href="${pageContext.request.contextPath}/MainController?action=increaseCartItem&productId=${item.product.id}" class="btn" style="background:#eee; padding:5px 10px;">+</a>
                                </td>
                                <td style="color: #d70018; font-weight: bold;">
                                    <fmt:formatNumber value="${item.subtotal}" type="number" pattern="#,##0"/> đ
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/MainController?action=removeFromCart&productId=${item.product.id}" style="color: #c62828;">🗑 Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div class="box" style="margin-top: 20px; text-align: right;">
                    <h3 style="margin-top: 0;">Tổng tiền: <span style="color: #d70018;"><fmt:formatNumber value="${cartTotal}" type="number" pattern="#,##0"/> đ</span></h3>
                    <a href="${pageContext.request.contextPath}/MainController?action=showCheckout" class="btn btn-primary" style="padding: 12px 30px; font-size: 16px;">Tiến hành đặt hàng</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="box" style="text-align: center; padding: 50px;">
                    <p>Giỏ hàng đang trống.</p>
                    <a href="${pageContext.request.contextPath}/MainController?action=showShop" class="btn btn-primary">Mua sắm ngay</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <jsp:include page="/includes/footer.jsp" />
</body>
</html>