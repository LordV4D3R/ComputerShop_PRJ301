<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Sản phẩm</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/assets/css/main.css">
</head>
<body>

    <h2 class="page-title">Danh sách Sản phẩm</h2>

    <c:if test="${not empty error}">
        <p class="text-danger">${error}</p>
    </c:if>

    <div style="margin-bottom: 15px;">
        <a class="btn btn-add"
           href="${pageContext.request.contextPath}/MainController?action=showCreateProduct">
            + Thêm Sản phẩm
        </a>
    </div>

    <div class="table-container" style="overflow-x: auto;">
        <table style="min-width: 1200px;"> <thead>
                <tr>
                    <th>#</th>
                    <th>ID</th>
                    <th>Tên sản phẩm</th>
                    <th>Category ID</th>
                    <th>Brand</th>
                    <th>CPU</th>
                    <th>RAM</th>
                    <th>Storage</th>
                    <th>Price</th>
                    <th>Stock</th>
                    <th>Image</th>
                    <th>Mô tả</th>
                    <th style="text-align: center;">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty listProduct}">
                        <c:forEach var="p" items="${listProduct}" varStatus="st">
                            <tr>
                                <td>${st.count}</td>
                                <td>${p.id}</td>
                                <td><strong>${p.name}</strong></td>
                                <td>${p.categoryId}</td>
                                <td>${p.brand}</td>
                                <td>${p.cpu}</td>
                                <td>${p.ram}</td>
                                <td>${p.storage}</td>
                                <td>${p.price}</td>
                                <td>${p.stockQuantity}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty p.imageUrl}">
                                            <img src="${pageContext.request.contextPath}/${p.imageUrl}"
                                                 alt="${p.name}"
                                                 width="80" style="border-radius: 4px;">
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color: #999; font-style: italic;">Không có ảnh</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${p.description}</td>
                                <td style="text-align: center; white-space: nowrap;">
                                    <a class="btn btn-edit"
                                       href="${pageContext.request.contextPath}/MainController?action=editProduct&id=${p.id}">
                                        Edit
                                    </a>
                                    <a class="btn btn-delete"
                                       href="${pageContext.request.contextPath}/MainController?action=deleteProduct&id=${p.id}"
                                       onclick="return confirm('Xóa sản phẩm này?');">
                                        Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="13" style="text-align: center; padding: 30px; color: #777;">
                                Chưa có sản phẩm nào.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>

</body>
</html>