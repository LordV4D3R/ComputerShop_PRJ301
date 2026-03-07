<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Review</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
</head>
<body>

    <h2 class="page-title">Danh sách Review</h2>

    <c:if test="${not empty error}">
        <p class="text-danger">${error}</p>
    </c:if>

    <div style="margin-bottom: 15px;">
        <a class="btn btn-add" href="${pageContext.request.contextPath}/MainController?action=showCreateReview">
            + Thêm Review
        </a>
    </div>

    <div class="table-container" style="overflow-x: auto;">
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>ID</th>
                    <th>Product ID</th>
                    <th>Account ID</th>
                    <th>Rating</th>
                    <th>Comment</th>
                    <th>Created At</th>
                    <th style="text-align: center;">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty listReview}">
                        <c:forEach var="r" items="${listReview}" varStatus="st">
                            <tr>
                                <td>${st.count}</td>
                                <td>${r.id}</td>
                                <td><strong>${r.productId}</strong></td>
                                <td><strong>${r.accountId}</strong></td>
                                <td>
                                    <span style="color: #f39c12; font-weight: bold;">${r.rating} / 5</span>
                                </td>
                                <td>${r.comment}</td>
                                <td>${r.createdAt}</td>
                                <td style="text-align: center; white-space: nowrap;">
                                    <a class="btn btn-edit" href="${pageContext.request.contextPath}/MainController?action=editReview&id=${r.id}">
                                        Edit
                                    </a>
                                    <a class="btn btn-delete" href="${pageContext.request.contextPath}/MainController?action=deleteReview&id=${r.id}" onclick="return confirm('Xóa review này?');">
                                        Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="8" style="text-align:center; padding: 20px;">Chưa có review nào.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>

</body>
</html>