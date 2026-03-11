<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Account | Staff</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body>
    <div class="dashboard-wrapper">
        
        <jsp:include page="/includes/sidebar_staff.jsp" />

        <div class="main-content">
            <h2 class="page-title">Quản lý Tài khoản (Account)</h2>

            <c:if test="${not empty error}">
                <div class="msg-error">${error}</div>
            </c:if>

            <div style="margin-bottom: 20px;">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/MainController?action=showCreateAccount">
                    + Thêm Account Mới
                </a>
            </div>

            <div class="table-wrapper" style="overflow-x: auto;">
                <table class="data-table" style="min-width: 1000px;">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>ID</th>
                            <th>Username</th>
                            <th>Fullname</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Role</th>
                            <th>Status</th>
                            <th>Image</th>
                            <th style="text-align: center;">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty listAccount}">
                                <c:forEach var="a" items="${listAccount}" varStatus="st">
                                    <tr>
                                        <td>${st.count}</td>
                                        <td>${a.id}</td>
                                        <td><strong>${a.username}</strong></td>
                                        <td>${a.fullname}</td>
                                        <td>${a.email}</td>
                                        <td>${a.phoneNumber}</td>
                                        <td>
                                            <span style="font-weight: bold; color: ${a.role == 'ADMIN' ? '#c62828' : '#1976d2'};">
                                                ${a.role}
                                            </span>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${a.status == 'ACTIVE'}"><span class="status-badge status-approved">ACTIVE</span></c:when>
                                                <c:when test="${a.status == 'INACTIVE'}"><span class="status-badge status-pending">INACTIVE</span></c:when>
                                                <c:otherwise><span class="status-badge status-cancelled">BANNED</span></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty a.imageUrl}">
                                                    <img src="${pageContext.request.contextPath}/${a.imageUrl}" alt="avatar" width="40" height="40" style="border-radius: 50%; object-fit: cover;">
                                                </c:when>
                                                <c:otherwise>
                                                    <span style="color: #999; font-size: 0.85em;">No Image</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td style="text-align: center; white-space: nowrap;">
                                            <a class="btn-sm btn-view" style="background: #ff9800;" href="${pageContext.request.contextPath}/MainController?action=editAccount&id=${a.id}">Sửa</a>
                                            <a class="btn-sm btn-cancel" href="${pageContext.request.contextPath}/MainController?action=deleteAccount&id=${a.id}" onclick="return confirm('Bạn có chắc chắn muốn xóa account này?');">Xóa</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="10" style="text-align:center; padding: 30px; color: #777;">Chưa có dữ liệu account.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</body>
</html>