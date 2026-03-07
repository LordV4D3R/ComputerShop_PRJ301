<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật Account</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <style>
        select { width: 100%; padding: 10px 12px; border: 1px solid #dce1e4; border-radius: 6px; }
    </style>
</head>
<body>

    <h2 class="page-title">Cập nhật Account</h2>

    <div class="form-container">
        <c:if test="${not empty error}">
            <p class="text-danger">${error}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="updateAccount">
            <input type="hidden" name="id" value="${account.id}">

            <div class="form-grid">
                <div class="form-group">
                    <label>Username:</label>
                    <input type="text" name="username" value="${account.username}" required>
                </div>

                <div class="form-group">
                    <label>Password:</label>
                    <input type="text" name="hashPassword" value="${account.hashPassword}" required>
                </div>

                <div class="form-group">
                    <label>Fullname:</label>
                    <input type="text" name="fullname" value="${account.fullname}">
                </div>

                <div class="form-group">
                    <label>Email:</label>
                    <input type="email" name="email" value="${account.email}">
                </div>

                <div class="form-group">
                    <label>Phone:</label>
                    <input type="text" name="phoneNumber" value="${account.phoneNumber}">
                </div>

                <div class="form-group">
                    <label>Role:</label>
                    <select name="role" required>
                        <option value="USER" ${account.role == 'USER' ? 'selected' : ''}>USER</option>
                        <option value="ADMIN" ${account.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Status:</label>
                    <select name="status" required>
                        <option value="ACTIVE" ${account.status == 'ACTIVE' ? 'selected' : ''}>ACTIVE</option>
                        <option value="INACTIVE" ${account.status == 'INACTIVE' ? 'selected' : ''}>INACTIVE</option>
                        <option value="BANNED" ${account.status == 'BANNED' ? 'selected' : ''}>BANNED</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Image URL:</label>
                    <input type="text" name="imageUrl" value="${account.imageUrl}">
                </div>

                <div class="form-group full-width">
                    <label>Address:</label>
                    <textarea name="address" rows="3">${account.address}</textarea>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-add">Cập nhật</button>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/MainController?action=listAccount">Hủy</a>
                </div>
            </div>
        </form>
    </div>

</body>
</html>