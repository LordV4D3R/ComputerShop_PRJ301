<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật Order | Staff</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body>
    <div class="dashboard-wrapper">
        <jsp:include page="/includes/sidebar_staff.jsp" />

        <div class="main-content">
            <h2 class="page-title">Cập nhật Order: <span style="color: #1976d2;">#${order.id}</span></h2>

            <div class="form-container">
                <c:if test="${not empty error}">
                    <p style="color: red; margin-bottom: 15px;">${error}</p>
                </c:if>

                <form action="${pageContext.request.contextPath}/MainController" method="post">
                    <input type="hidden" name="action" value="updateOrder">
                    <input type="hidden" name="id" value="${order.id}">

                    <div class="form-grid">
                        <div class="form-group">
                            <label>Họ tên: <span style="color:red;">*</span></label>
                            <input type="text" name="fullname" value="${order.fullname}" required>
                        </div>

                        <div class="form-group">
                            <label>Số điện thoại: <span style="color:red;">*</span></label>
                            <input type="text" name="phoneNumber" value="${order.phoneNumber}" required>
                        </div>

                        <div class="form-group">
                            <label>Email:</label>
                            <input type="email" name="email" value="${order.email}">
                        </div>

                        <div class="form-group">
                            <label>Tổng tiền: <span style="color:red;">*</span></label>
                            <input type="number" name="totalPrice" step="0.01" min="0" value="${order.totalPrice}" required>
                        </div>

                        <div class="form-group full-width">
                            <label>Địa chỉ: <span style="color:red;">*</span></label>
                            <textarea name="address" rows="3" required>${order.address}</textarea>
                        </div>

                        <div class="form-group full-width">
                            <label>Trạng thái:</label>
                            <input type="text" value="${order.status}" readonly style="background: #f5f5f5; color: #666;">
                            <small style="display: block; margin-top: 5px; color: #888;">
                                Không chỉnh sửa trạng thái ở form này. Chỉ dùng Approve / Cancel trong danh sách hoặc chi tiết đơn hàng.
                            </small>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary" style="padding: 10px 25px;">Cập nhật</button>
                            <a href="${pageContext.request.contextPath}/MainController?action=listOrder" class="btn" style="background: #e0e0e0; color: #333; padding: 10px 25px;">Hủy</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>