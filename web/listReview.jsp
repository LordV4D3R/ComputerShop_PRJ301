<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Đánh giá | Admin</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body>
    <div class="dashboard-wrapper">
        <jsp:include page="/includes/sidebar_staff.jsp" />

        <div class="main-content">
            <h2 class="page-title">Danh sách Đánh giá (Review)</h2>

            <c:if test="${not empty error}">
                <div style="background: #ffebee; color: #c62828; padding: 12px; border-radius: 6px; margin-bottom: 16px;">${error}</div>
            </c:if>

            <div class="table-wrapper" style="overflow-x: auto;">
                <table class="data-table" style="min-width: 1000px;">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Mã SP (Product ID)</th>
                            <th>Người đăng (Account ID)</th>
                            <th style="text-align: center;">Số sao</th>
                            <th>Nội dung (Comment)</th>
                            <th>Ngày đăng</th>
                            <th style="text-align: center;">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty listReview}">
                                <c:forEach var="r" items="${listReview}" varStatus="st">
                                    <tr>
                                        <td>${st.count}</td>
                                        <td><strong>${r.productId}</strong></td>
                                        <td><span style="color: #666;">${r.accountId}</span></td>
                                        <td style="text-align: center;">
                                            <span style="color: #f59e0b; font-weight: bold; font-size: 15px;">⭐ ${r.rating}</span>
                                        </td>
                                        <td style="max-width: 300px;">
                                            <div class="text-truncate-2">${r.comment}</div>
                                        </td>
                                        <td style="font-size: 13px;">${r.createdAt}</td>
                                        <td style="text-align: center; white-space: nowrap;">
                                            
                                            <a class="btn-sm btn-view" style="background: #1976d2; color: white; border: none;" href="${pageContext.request.contextPath}/MainController?action=editReview&id=${r.id}">Xem chi tiết</a>
                                            
                                            <c:if test="${sessionScope.LOGIN_USER.role eq 'ADMIN'}">
                                                <a class="btn-sm btn-cancel" href="${pageContext.request.contextPath}/MainController?action=deleteReview&id=${r.id}" onclick="return confirm('Hành động này không thể hoàn tác. Bạn chắc chắn xóa bài đánh giá này?');">Xóa</a>
                                            </c:if>
                                            
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="7" style="text-align:center; padding: 30px; color: #777;">Chưa có bài đánh giá nào.</td>
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