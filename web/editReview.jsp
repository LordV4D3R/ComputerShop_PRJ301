<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết Đánh giá | Admin</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body>
    <div class="dashboard-wrapper">
        <jsp:include page="/includes/sidebar_staff.jsp" />

        <div class="main-content">
            <h2 class="page-title">Chi tiết Đánh giá</h2>

            <div class="form-container">
                <div class="form-grid">
                    
                    <c:set var="productName" value="${review.productId}" />
                    <c:forEach var="p" items="${listProduct}">
                        <c:if test="${p.id eq review.productId}">
                            <c:set var="productName" value="${p.id} - ${p.name}" />
                        </c:if>
                    </c:forEach>

                    <div class="form-group full-width">
                        <label>Sản phẩm (Product):</label>
                        <input type="text" value="${productName}" readonly style="background-color: #f8f9fa; color: #333; font-weight: 500; cursor: not-allowed;">
                    </div>

                    <c:set var="reviewerName" value="${review.accountId}" />
                    <c:forEach var="a" items="${listAccount}">
                        <c:if test="${a.id eq review.accountId}">
                            <c:set var="reviewerName" value="${not empty a.fullname ? a.fullname : a.username}" />
                        </c:if>
                    </c:forEach>

                    <div class="form-group">
                        <label>Tài khoản đánh giá:</label>
                        <input type="text" value="${reviewerName}" readonly style="background-color: #f8f9fa; color: #1976d2; font-weight: bold; cursor: not-allowed;">
                    </div>

                    <div class="form-group">
                        <label>Số sao (Rating):</label>
                        <input type="text" value="⭐ ${review.rating} / 5" readonly style="background-color: #f8f9fa; color: #f59e0b; font-weight: bold; cursor: not-allowed;">
                    </div>

                    <div class="form-group full-width">
                        <label>Nội dung (Comment):</label>
                        <textarea rows="5" readonly style="background-color: #f8f9fa; color: #444; resize: none; cursor: not-allowed;">${review.comment}</textarea>
                    </div>

                    <div class="form-group full-width">
                        <label>Ngày đăng (Created At):</label>
                        <input type="text" value="${review.createdAt}" readonly style="background-color: #f8f9fa; color: #666; width: 50%; cursor: not-allowed;">
                    </div>

                    <div class="form-actions">
                        <a class="btn" style="background: #6c757d; color: white; padding: 10px 25px;" href="${pageContext.request.contextPath}/MainController?action=listReview">← Quay lại danh sách</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>