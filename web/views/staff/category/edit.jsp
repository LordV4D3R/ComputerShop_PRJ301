<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật Category</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
</head>
<body>

<h2 class="page-title">Cập nhật Category</h2>

<div class="form-container">
    <c:if test="${not empty error}">
        <p class="text-danger">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/MainController" method="POST">
        <input type="hidden" name="action" value="updateCategory">
        <input type="hidden" name="id" value="${category.id}">

        <div class="form-group">
            <label>Tên Category:</label>
            <input type="text" name="name" value="${category.name}" required>
        </div>

        <div class="form-group">
            <label>Mô tả:</label>
            <textarea name="description" rows="4">${category.description}</textarea>
        </div>

        <button type="submit" class="btn btn-add">Cập nhật</button>
        <a class="btn btn-edit" href="${pageContext.request.contextPath}/MainController?action=listCategory">Hủy</a>
    </form>
</div>

</body>
</html>