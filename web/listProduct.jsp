<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Sản phẩm | Staff</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body>
    <div class="dashboard-wrapper">
        
        <jsp:include page="/includes/sidebar_staff.jsp" />

        <div class="main-content">
            <h2 class="page-title">Quản lý Sản phẩm</h2>

            <c:if test="${not empty error}">
                <div class="msg-error" style="color: #c62828; margin-bottom: 15px;">${error}</div>
            </c:if>

            <div style="margin-bottom: 20px;">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/MainController?action=showCreateProduct">
                    + Thêm Sản phẩm
                </a>
            </div>

            <div class="table-wrapper" style="overflow-x: auto;">
                <table class="data-table" style="min-width: 1200px;">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Mã SP</th>
                            <th>Tên sản phẩm</th>
                            <th>Thương hiệu</th>
                            <th>CPU</th>
                            <th>RAM</th>
                            <th>Storage</th>
                            <th>Giá bán</th>
                            <th>Kho</th>
                            <th>Hình ảnh</th>
                            <th style="text-align: center;">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty listProduct}">
                                <c:forEach var="p" items="${listProduct}" varStatus="st">
                                    <tr>
                                        <td>${st.count}</td>
                                        <td><strong>${p.id}</strong></td>
                                        <td><strong>${p.name}</strong><br><span style="color:#777; font-size: 12px;">Cat: ${p.categoryId}</span></td>
                                        <td>${p.brand}</td>
                                        <td style="font-size: 13px;">${p.cpu}</td>
                                        <td style="font-size: 13px;">${p.ram}</td>
                                        <td style="font-size: 13px;">${p.storage}</td>
                                        <td style="color: #d70018; font-weight: bold; white-space: nowrap;">
                                            <fmt:formatNumber value="${p.price}" type="number" pattern="#,##0"/> đ
                                        </td>
                                        <td>${p.stockQuantity}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty p.imageUrl}">
                                                    <img src="${pageContext.request.contextPath}/${p.imageUrl}" alt="${p.name}" width="60" style="border-radius: 4px; object-fit: contain;">
                                                </c:when>
                                                <c:otherwise>
                                                    <span style="color: #999; font-style: italic; font-size: 12px;">No Image</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td style="text-align: center; white-space: nowrap;">
                                            <a class="btn-sm btn-view" style="background: #ff9800; color: white; border: none;" href="${pageContext.request.contextPath}/MainController?action=editProduct&id=${p.id}">Sửa</a>
                                            <a class="btn-sm btn-cancel" href="${pageContext.request.contextPath}/MainController?action=deleteProduct&id=${p.id}" onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?');">Xóa</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="11" style="text-align: center; padding: 30px; color: #777;">
                                        Chưa có sản phẩm nào.
                                    </td>
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