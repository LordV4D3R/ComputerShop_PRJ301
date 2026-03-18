<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Order | Staff</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body>
    <div class="dashboard-wrapper">
        <jsp:include page="/includes/sidebar_staff.jsp" />

        <div class="main-content">
            <h2 class="page-title">Thêm Order Mới</h2>

            <div class="form-container">
                <c:if test="${not empty error}">
                    <p style="color: red; margin-bottom: 15px;">${error}</p>
                </c:if>

                <form action="${pageContext.request.contextPath}/MainController" method="post">
                    <input type="hidden" name="action" value="insertOrder">

                    <div class="form-grid">
                        <div class="form-group">
                            <label>Họ tên: <span style="color:red;">*</span></label>
                            <input type="text" name="fullname" required>
                        </div>

                        <div class="form-group">
                            <label>Số điện thoại: <span style="color:red;">*</span></label>
                            <input type="text" name="phoneNumber" required>
                        </div>

                        <div class="form-group">
                            <label>Email:</label>
                            <input type="email" name="email">
                        </div>

                        <div class="form-group">
                            <label>Tổng tiền: <span style="color:red;">*</span></label>
                            <input type="number" name="totalPrice" step="0.01" min="0" required>
                        </div>

                        <div class="form-group full-width">
                            <label>Địa chỉ: <span style="color:red;">*</span></label>
                            <textarea name="address" rows="3" required></textarea>
                        </div>

                        <div class="form-group full-width">
                            <label>Trạng thái:</label>
                            <input type="text" value="PENDING" readonly style="background: #f5f5f5; color: #666;">
                            <small style="display: block; margin-top: 5px; color: #888;">
                                Đơn hàng tạo mới thủ công sẽ luôn ở trạng thái PENDING. Duyệt / hủy tại trang danh sách hoặc chi tiết đơn hàng.
                            </small>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary" style="padding: 10px 25px;">Lưu Order</button>
                            <a href="${pageContext.request.contextPath}/MainController?action=listOrder" class="btn" style="background: #e0e0e0; color: #333; padding: 10px 25px;">Hủy</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>