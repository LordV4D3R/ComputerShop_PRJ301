<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Account | Staff</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body>
    <div class="dashboard-wrapper">
        
        <jsp:include page="/includes/sidebar_staff.jsp" />

        <div class="main-content">
            <h2 class="page-title">Thêm Account Mới</h2>

            <div class="form-container">
                <c:if test="${not empty error}">
                    <div class="msg-error">${error}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/MainController" method="post">
                    <input type="hidden" name="action" value="insertAccount">

                    <div class="form-grid">
                        <div class="form-group">
                            <label>Username <span style="color:red;">*</span></label>
                            <input type="text" name="username" required>
                        </div>

                        <div class="form-group">
                            <label>Password <span style="color:red;">*</span></label>
                            <input type="text" name="hashPassword" required>
                        </div>

                        <div class="form-group">
                            <label>Fullname</label>
                            <input type="text" name="fullname">
                        </div>

                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" name="email">
                        </div>

                        <div class="form-group">
                            <label>Phone</label>
                            <input type="text" name="phoneNumber">
                        </div>

                        <div class="form-group">
                            <label>Role</label>
                            <select name="role" required>
                                <option value="USER" selected>USER</option>
                                <option value="ADMIN">ADMIN</option>
                                <option value="STAFF">STAFF</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Status</label>
                            <select name="status" required>
                                <option value="ACTIVE" selected>ACTIVE</option>
                                <option value="INACTIVE">INACTIVE</option>
                                <option value="BANNED">BANNED</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Image URL</label>
                            <input type="text" name="imageUrl" placeholder="Ví dụ: assets/images/users/avatar.jpg">
                        </div>

                        <div class="form-group full-width">
                            <label>Address</label>
                            <textarea name="address" rows="3"></textarea>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary" style="padding: 10px 25px;">Lưu Account</button>
                            <a class="btn" style="background: #e0e0e0; color: #333; padding: 10px 25px;" href="${pageContext.request.contextPath}/MainController?action=listAccount">Hủy</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>