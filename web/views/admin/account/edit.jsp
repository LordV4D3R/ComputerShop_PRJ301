<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật Account | Staff</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body>
    <div class="dashboard-wrapper">
        
        <jsp:include page="/includes/sidebar_staff.jsp" />

        <div class="main-content">
            <h2 class="page-title">Cập nhật Account: <span style="color: #1976d2;">${account.username}</span></h2>

            <div class="form-container">
                <c:if test="${not empty error}">
                    <div class="msg-error">${error}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/MainController" method="post">
                    <input type="hidden" name="action" value="updateAccount">
                    <input type="hidden" name="id" value="${account.id}">

                    <div class="form-grid">
                        <div class="form-group">
                            <label>Username <span style="color:red;">*</span></label>
                            <input type="text" name="username" value="${account.username}" required>
                        </div>

                        <div class="form-group">
                            <label>Mật khẩu mới</label>
                            <input type="password" name="newPassword" placeholder="Để trống nếu không muốn đổi">
                        </div>

                        <div class="form-group">
                            <label>Fullname</label>
                            <input type="text" name="fullname" value="${account.fullname}">
                        </div>

                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" name="email" value="${account.email}">
                        </div>

                        <div class="form-group">
                            <label>Phone</label>
                            <input type="text" name="phoneNumber" value="${account.phoneNumber}">
                        </div>

                        <div class="form-group">
                            <label>Role</label>
                            <select name="role" required>
                                <option value="USER" ${account.role == 'USER' ? 'selected' : ''}>USER</option>
                                <option value="ADMIN" ${account.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                                <option value="STAFF" ${account.role == 'STAFF' ? 'selected' : ''}>STAFF</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Status</label>
                            <select name="status" required>
                                <option value="ACTIVE" ${account.status == 'ACTIVE' ? 'selected' : ''}>ACTIVE</option>
                                <option value="INACTIVE" ${account.status == 'INACTIVE' ? 'selected' : ''}>INACTIVE</option>
                                <option value="BANNED" ${account.status == 'BANNED' ? 'selected' : ''}>BANNED</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Image URL</label>
                            <input type="text" name="imageUrl" value="${account.imageUrl}">
                        </div>

                        <div class="form-group full-width">
                            <label>Address</label>
                            <textarea name="address" rows="3">${account.address}</textarea>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary" style="padding: 10px 25px;">Cập nhật</button>
                            <a class="btn" style="background: #e0e0e0; color: #333; padding: 10px 25px;" href="${pageContext.request.contextPath}/MainController?action=listAccount">Hủy</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>