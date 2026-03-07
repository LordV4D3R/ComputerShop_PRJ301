<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Account</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <style>
        select { width: 100%; padding: 10px 12px; border: 1px solid #dce1e4; border-radius: 6px; }
    </style>
</head>
<body>

    <h2 class="page-title">Thêm Account</h2>

    <div class="form-container">
        <c:if test="${not empty error}">
            <p class="text-danger">${error}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="insertAccount">

            <div class="form-grid">
                <div class="form-group">
                    <label>Username:</label>
                    <input type="text" name="username" required>
                </div>

                <div class="form-group">
                    <label>Password (tạm lưu dạng chữ):</label>
                    <input type="text" name="hashPassword" required>
                </div>

                <div class="form-group">
                    <label>Fullname:</label>
                    <input type="text" name="fullname">
                </div>

                <div class="form-group">
                    <label>Email:</label>
                    <input type="email" name="email">
                </div>

                <div class="form-group">
                    <label>Phone:</label>
                    <input type="text" name="phoneNumber">
                </div>

                <div class="form-group">
                    <label>Role:</label>
                    <select name="role" required>
                        <option value="USER" selected>USER</option>
                        <option value="ADMIN">ADMIN</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Status:</label>
                    <select name="status" required>
                        <option value="ACTIVE" selected>ACTIVE</option>
                        <option value="INACTIVE">INACTIVE</option>
                        <option value="BANNED">BANNED</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Image URL:</label>
                    <input type="text" name="imageUrl" placeholder="Ví dụ: assets/images/users/avatar1.jpg">
                </div>

                <div class="form-group full-width">
                    <label>Address:</label>
                    <textarea name="address" rows="3"></textarea>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-add">Lưu Account</button>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/MainController?action=listAccount">Hủy</a>
                </div>
            </div>
        </form>
    </div>

</body>
</html>