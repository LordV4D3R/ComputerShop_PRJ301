<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Order</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <style>
        .field-note {
            display: block;
            margin-top: 6px;
            color: #666;
            font-size: 13px;
        }
    </style>
</head>
<body>

    <h2 class="page-title">Thêm Order</h2>

    <div class="form-container">

        <c:if test="${not empty error}">
            <p class="text-danger">${error}</p>
        </c:if>

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
                    <input type="number" name="totalPrice" step="0.01" min="0" required>
                </div>

                <div class="form-group full-width">
                    <label>Địa chỉ:</label>
                    <textarea name="address" rows="3" required></textarea>
                </div>

                <div class="form-group">
                    <label>Trạng thái:</label>
                    <input type="text" value="PENDING" readonly>
                    <small class="field-note">
                        Đơn hàng tạo mới thủ công sẽ luôn ở trạng thái PENDING.
                        Duyệt / hủy tại trang danh sách hoặc chi tiết đơn hàng.
                    </small>
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