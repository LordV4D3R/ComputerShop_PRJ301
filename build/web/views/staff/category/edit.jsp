<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật Danh mục | Staff</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body>
    <div class="dashboard-wrapper">
        
        <jsp:include page="/includes/sidebar_staff.jsp" />

        <div class="main-content">
            <h2 class="page-title">Cập nhật Danh mục: <span style="color: #1976d2;">${category.name}</span></h2>

            <div class="form-container" style="max-width: 600px;">
                <c:if test="${not empty error}">
                    <div style="color: red; margin-bottom: 15px;">${error}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/MainController" method="POST">
                    <input type="hidden" name="action" value="updateCategory">
                    <input type="hidden" name="id" value="${category.id}">

                    <div class="form-grid" style="grid-template-columns: 1fr;">
                        <div class="form-group full-width">
                            <label>Tên Danh mục: <span style="color:red;">*</span></label>
                            <input type="text" name="name" value="${category.name}" required>
                        </div>

                        <div class="form-group full-width">
                            <label>Mô tả:</label>
                            <textarea name="description" rows="5">${category.description}</textarea>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary" style="padding: 10px 25px;">Cập nhật</button>
                            <a class="btn" style="background: #e0e0e0; color: #333; padding: 10px 25px;" href="${pageContext.request.contextPath}/MainController?action=listCategory">Hủy</a>
                        </div>
                    </div>
                </form>
            </div>

        </div>
    </div>
</body>
</html>