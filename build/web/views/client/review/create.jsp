<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Review</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <style>
        select { width: 100%; padding: 10px 12px; border: 1px solid #dce1e4; border-radius: 6px; }
    </style>
</head>
<body>

    <h2 class="page-title">Thêm Review</h2>

    <div class="form-container">
        <c:if test="${not empty error}">
            <p class="text-danger">${error}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="insertReview">

            <div class="form-grid">
                <div class="form-group">
                    <label>Product:</label>
                    <select name="productId" required>
                        <option value="">-- Chọn Product --</option>
                        <c:forEach var="p" items="${listProduct}">
                            <option value="${p.id}">${p.id} - ${p.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Account:</label>
                    <select name="accountId" required>
                        <option value="">-- Chọn Account --</option>
                        <c:forEach var="a" items="${listAccount}">
                            <option value="${a.id}">${a.id} - ${a.username}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Rating (1-5):</label>
                    <input type="number" name="rating" min="1" max="5" required>
                </div>

                <div class="form-group full-width">
                    <label>Comment:</label>
                    <textarea name="comment" rows="4"></textarea>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-add">Lưu</button>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/MainController?action=listReview">Hủy</a>
                </div>
            </div>
        </form>
    </div>

</body>
</html>