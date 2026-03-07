<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật Order</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <style>
        select { width: 100%; padding: 10px 12px; border: 1px solid #dce1e4; border-radius: 6px; }
    </style>
</head>
<body>

    <h2 class="page-title">Cập nhật Order</h2>

    <div class="form-container">

        <c:if test="${not empty error}">
            <p class="text-danger">${error}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="updateOrder">
            <input type="hidden" name="id" value="${order.id}">

            <div class="form-grid">
                <div class="form-group">
                    <label>Họ tên:</label>
                    <input type="text" name="fullname" value="${order.fullname}" required>
                </div>

                <div class="form-group">
                    <label>Số điện thoại:</label>
                    <input type="text" name="phoneNumber" value="${order.phoneNumber}" required>
                </div>

                <div class="form-group">
                    <label>Email:</label>
                    <input type="email" name="email" value="${order.email}">
                </div>

                <div class="form-group">
                    <label>Tổng tiền:</label>
                    <input type="number" name="totalPrice" step="0.01" value="${order.totalPrice}" required>
                </div>

                <div class="form-group full-width">
                    <label>Địa chỉ:</label>
                    <textarea name="address" rows="3" required>${order.address}</textarea>
                </div>

                <div class="form-group">
                    <label>Trạng thái (Admin đối soát QR):</label>
                    <select name="status" required>
                        <option value="Pending" ${order.status == 'Pending' ? 'selected' : ''}>Pending (Chờ thanh toán)</option>
                        <option value="Paid" ${order.status == 'Paid' ? 'selected' : ''}>Paid (Đã thanh toán QR)</option>
                        <option value="Cancelled" ${order.status == 'Cancelled' ? 'selected' : ''}>Cancelled (Đã hủy)</option>
                    </select>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-add">Cập nhật</button>
                    <a href="${pageContext.request.contextPath}/MainController?action=listOrder" class="btn btn-secondary">Hủy</a>
                </div>
            </div>
        </form>
    </div>

</body>
</html>