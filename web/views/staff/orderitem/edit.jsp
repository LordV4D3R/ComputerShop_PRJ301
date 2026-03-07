<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật Order Item</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <style>
        select { width: 100%; padding: 10px 12px; border: 1px solid #dce1e4; border-radius: 6px; }
    </style>
</head>
<body>

    <h2 class="page-title">Cập nhật Order Item</h2>

    <div class="form-container">
        <c:if test="${not empty error}">
            <p class="text-danger">${error}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="updateOrderItem">
            <input type="hidden" name="id" value="${orderItem.id}">

            <div class="form-grid">
                <div class="form-group">
                    <label>Order:</label>
                    <select name="orderId" required>
                        <option value="">-- Chọn Order --</option>
                        <c:forEach var="o" items="${listOrder}">
                            <option value="${o.id}" ${o.id == orderItem.orderId ? 'selected' : ''}>
                                ${o.id} - ${o.fullname}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Product:</label>
                    <select name="productId" required>
                        <option value="">-- Chọn Product --</option>
                        <c:forEach var="p" items="${listProduct}">
                            <option value="${p.id}" ${p.id == orderItem.productId ? 'selected' : ''}>
                                ${p.id} - ${p.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Quantity:</label>
                    <input type="number" name="quantity" min="1" value="${orderItem.quantity}" required>
                </div>

                <div class="form-group">
                    <label>Price:</label>
                    <input type="number" name="price" step="0.01" min="0" value="${orderItem.price}" required>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-add">Cập nhật</button>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/MainController?action=listOrderItem">Hủy</a>
                </div>
            </div>
        </form>
    </div>

</body>
</html>