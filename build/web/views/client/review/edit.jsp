<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật Review</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <style>
        select { width: 100%; padding: 10px 12px; border: 1px solid #dce1e4; border-radius: 6px; }
    </style>
</head>
<body>

    <h2 class="page-title">Cập nhật Review</h2>

    <div class="form-container">
        <c:if test="${not empty error}">
            <p class="text-danger">${error}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="updateReview">
            <input type="hidden" name="id" value="${review.id}">

            <div class="form-grid">
                <div class="form-group">
                    <label>Product:</label>
                    <select name="productId" required>
                        <option value="">-- Chọn Product --</option>
                        <c:forEach var="p" items="${listProduct}">
                            <option value="${p.id}" <c:if test="${p.id eq review.productId}">selected</c:if>>
                                ${p.id} - ${p.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Account:</label>
                    <select name="accountId" required>
                        <option value="">-- Chọn Account --</option>
                        <c:forEach var="a" items="${listAccount}">
                            <option value="${a.id}" <c:if test="${a.id eq review.accountId}">selected</c:if>>
                                ${a.id} - ${a.username}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Rating (1-5):</label>
                    <input type="number" name="rating" min="1" max="5" value="${review.rating}" required>
                </div>

                <div class="form-group full-width">
                    <label>Comment:</label>
                    <textarea name="comment" rows="4">${review.comment}</textarea>
                </div>

                <div class="form-group full-width">
                    <label>Created At:</label>
                    <input type="text" value="${review.createdAt}" readonly style="background-color: #f8f9fa; cursor: not-allowed;">
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-add">Cập nhật</button>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/MainController?action=listReview">Hủy</a>
                </div>
            </div>
        </form>
    </div>

</body>
</html>