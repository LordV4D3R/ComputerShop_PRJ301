<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Wishlist</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
</head>
<body>

    <h2 class="page-title">Danh sách Wishlist</h2>

    <c:if test="${not empty error}">
        <p class="text-danger">${error}</p>
    </c:if>

    <div style="margin-bottom: 15px;">
        <a class="btn btn-add" href="${pageContext.request.contextPath}/MainController?action=showCreateWishlist">
            + Thêm Wishlist
        </a>
    </div>

    <div class="table-container" style="overflow-x: auto;">
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>ID</th>
                    <th>Account ID</th>
                    <th>Product ID</th>
                    <th>Created At</th>
                    <th style="text-align: center;">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty listWishlist}">
                        <c:forEach var="w" items="${listWishlist}" varStatus="st">
                            <tr>
                                <td>${st.count}</td>
                                <td>${w.id}</td>
                                <td><strong>${w.accountId}</strong></td>
                                <td><strong>${w.productId}</strong></td>
                                <td>${w.createdAt}</td>
                                <td style="text-align: center; white-space: nowrap;">
                                    <a class="btn btn-edit" href="${pageContext.request.contextPath}/MainController?action=editWishlist&id=${w.id}">
                                        Edit
                                    </a>
                                    <a class="btn btn-delete" href="${pageContext.request.contextPath}/MainController?action=deleteWishlist&id=${w.id}" onclick="return confirm('Xóa wishlist này?');">
                                        Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6" style="text-align:center; padding: 20px;">Chưa có wishlist nào.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>

</body>
</html>