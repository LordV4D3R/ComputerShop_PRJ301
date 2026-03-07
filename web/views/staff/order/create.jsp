<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Order</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <style>
        select { width: 100%; padding: 10px 12px; border: 1px solid #dce1e4; border-radius: 6px; }
    </style>
</head>
<body>

    <h2 class="page-title">Thêm Order</h2>

    <div class="form-container">
        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="insertOrder">

            <div class="form-grid">
                <div class="form-group">
                    <label>Họ tên:</label>
                    <input type="text" name="fullname" required>
                </div>

                <div class="form-group">
                    <label>Số điện thoại:</label>
                    <input type="text" name="phoneNumber" required>
                </div>

                <div class="form-group">
                    <label>Email:</label>
                    <input type="email" name="email">
                </div>

                <div class="form-group">
                    <label>Tổng tiền:</label>
                    <input type="number" name="totalPrice" step="0.01" required>
                </div>

                <div class="form-group full-width">
                    <label>Địa chỉ:</label>
                    <textarea name="address" rows="3" required></textarea>
                </div>

                <div class="form-group">
                    <label>Trạng thái:</label>
                    <select name="status" required>
                        <option value="Pending" selected>Pending (Chờ thanh toán)</option>
                        <option value="Paid">Paid (Đã thanh toán QR)</option>
                        <option value="Cancelled">Cancelled (Đã hủy)</option>
                    </select>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-add">Lưu Order</button>
                    <a href="${pageContext.request.contextPath}/MainController?action=listOrder" class="btn btn-secondary">Hủy</a>
                </div>
            </div>
        </form>
    </div>

</body>
</html>