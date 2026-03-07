<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta charset="UTF-8">
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
    <title>Thêm Category</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">    
    </head>
    
<body>

<h2 class="page-title">Thêm Category</h2>

<div class="form-container">
    <form action="${pageContext.request.contextPath}/MainController" method="POST">
        <input type="hidden" name="action" value="insertCategory">

        <div class="form-group">
            <label>Tên Category:</label>
            <input type="text" name="name" required>
        </div>

        <div class="form-group">
            <label>Mô tả:</label>
            <textarea name="description" rows="4"></textarea>
        </div>

        <button type="submit" class="btn btn-add">Lưu</button>
        <a class="btn btn-edit" href="${pageContext.request.contextPath}/MainController?action=listCategory">Hủy</a>
    </form>
</div>

</body>
</html>