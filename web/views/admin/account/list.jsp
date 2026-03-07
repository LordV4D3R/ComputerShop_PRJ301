<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Account</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
</head>
<body>

    <h2 class="page-title">Danh sách Account</h2>

    <c:if test="${not empty error}">
        <p class="text-danger">${error}</p>
    </c:if>

    <div style="margin-bottom: 15px;">
        <a class="btn btn-add" href="${pageContext.request.contextPath}/MainController?action=showCreateAccount">
            + Thêm Account
        </a>
    </div>

    <div class="table-container" style="overflow-x: auto;">
        <table style="min-width: 1100px;">
            <thead>
                <tr>
                    <th>#</th>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Fullname</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Address</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>Image</th>
                    <th style="text-align: center;">Action</th>
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
                                <td>${a.address}</td>
                                <td>
                                    <span style="padding: 4px 8px; border-radius: 4px; font-size: 0.85rem; color: #fff; background-color: ${a.role == 'ADMIN' ? '#e74c3c' : '#3498db'}">
                                        ${a.role}
                                    </span>
                                </td>
                                <td>
                                    <span style="padding: 4px 8px; border-radius: 4px; font-size: 0.85rem; color: #fff; background-color: ${a.status == 'ACTIVE' ? '#2ecc71' : '#95a5a6'}">
                                        ${a.status}
                                    </span>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty a.imageUrl}">
                                            <img src="${pageContext.request.contextPath}/${a.imageUrl}" alt="${a.username}" width="50" style="border-radius: 50%;">
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color: #999; font-size: 0.9em;">No Image</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td style="text-align: center; white-space: nowrap;">
                                    <a class="btn btn-edit" href="${pageContext.request.contextPath}/MainController?action=editAccount&id=${a.id}">
                                        Edit
                                    </a>
                                    <a class="btn btn-delete" href="${pageContext.request.contextPath}/MainController?action=deleteAccount&id=${a.id}" onclick="return confirm('Xóa account này?');">
                                        Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="11" style="text-align:center; padding: 20px;">Chưa có account nào.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>

</body>
</html>