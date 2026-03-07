<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Order Items</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
</head>
<body>

    <h2 class="page-title">Danh sách Order Items</h2>

    <c:if test="${not empty error}">
        <p class="text-danger">${error}</p>
    </c:if>

    <div style="margin-bottom: 15px;">
        <a class="btn btn-add" href="${pageContext.request.contextPath}/MainController?action=showCreateOrderItem">
            + Thêm Order Item
        </a>
    </div>

    <div class="table-container" style="overflow-x: auto;">
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>ID</th>
                    <th>Order ID</th>
                    <th>Product ID</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Subtotal</th>
                    <th style="text-align: center;">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty listOrderItem}">
                        <c:forEach var="item" items="${listOrderItem}" varStatus="st">
                            <tr>
                                <td>${st.count}</td>
                                <td>${item.id}</td>
                                <td>${item.orderId}</td>
                                <td>${item.productId}</td>
                                <td>${item.quantity}</td>
                                <td><strong>${item.price}</strong></td>
                                <td><strong style="color: #e74c3c;">${item.quantity * item.price}</strong></td>
                                <td style="text-align: center; white-space: nowrap;">
                                    <a class="btn btn-edit" href="${pageContext.request.contextPath}/MainController?action=editOrderItem&id=${item.id}">
                                        Edit
                                    </a>
                                    <a class="btn btn-delete" href="${pageContext.request.contextPath}/MainController?action=deleteOrderItem&id=${item.id}" onclick="return confirm('Xóa order item này?');">
                                        Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="8" style="text-align:center; padding: 20px;">Chưa có order item nào.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>

</body>
</html>