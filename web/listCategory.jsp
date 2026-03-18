<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Category | Staff</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body>
    <div class="dashboard-wrapper">
        
        <jsp:include page="/includes/sidebar_staff.jsp" />

        <div class="main-content">
            <h2 class="page-title">Quản lý Danh mục (Category)</h2>

            <c:if test="${not empty error}">
                <div style="color: #c62828; margin-bottom: 15px; font-weight: bold;">${error}</div>
            </c:if>

            <div style="margin-bottom: 20px;">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/MainController?action=showCreateCategory">
                    + Thêm Danh mục
                </a>
            </div>

            <div class="table-wrapper">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th style="width: 50px;">#</th>
                            <th>Tên Danh mục</th>
                            <th>Mô tả</th>
                            <th style="text-align: center; width: 150px;">Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty listCategory}">
                                <c:forEach var="c" items="${listCategory}" varStatus="st">
                                    <tr>
                                        <td>${st.count}</td>
                                        <td><strong>${c.name}</strong></td>
                                        <td>${c.description}</td>
                                        <td style="text-align: center; white-space: nowrap;">
                                            <a class="btn-sm btn-view" style="background: #ff9800; color: white; border: none;" href="${pageContext.request.contextPath}/MainController?action=editCategory&id=${c.id}">
                                                Sửa
                                            </a>
                                            <a class="btn-sm btn-cancel" href="${pageContext.request.contextPath}/MainController?action=deleteCategory&id=${c.id}" onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này?');">
                                                Xóa
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="4" style="text-align: center; padding: 30px; color: #777;">
                                        Chưa có danh mục nào.
                                    </td>
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