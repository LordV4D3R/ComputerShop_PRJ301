<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Category</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
</head>
<body>

<h2 class="page-title">Danh sách Category</h2>

<c:if test="${not empty error}">
    <p class="text-danger">${error}</p>
</c:if>

<div style="max-width: 1000px; margin: 0 auto;">
    <a class="btn btn-add" href="${pageContext.request.contextPath}/MainController?action=showCreateCategory">
        + Thêm Category
    </a>

    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Tên</th>
                    <th>Mô tả</th>
                    <th style="text-align: center;">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="c" items="${listCategory}" varStatus="st">
                    <tr>
                        <td>${st.count}</td>
                        <td><strong>${c.name}</strong></td>
                        <td>${c.description}</td>
                        <td style="text-align: center;">
                            <a class="btn btn-edit"
                               href="${pageContext.request.contextPath}/MainController?action=editCategory&id=${c.id}">
                                Sửa
                            </a>
                            <a class="btn btn-delete"
                               href="${pageContext.request.contextPath}/MainController?action=deleteCategory&id=${c.id}"
                               onclick="return confirm('Bạn có chắc chắn muốn xóa category này không?');">
                                Xóa
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>