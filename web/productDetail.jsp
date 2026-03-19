<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết sản phẩm | TechShop</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css">
    </head>
    <body>
        <jsp:include page="/includes/header.jsp" />

        <div class="container" style="margin-top: 20px; margin-bottom: 40px;">
            <c:if test="${not empty sessionScope.successMessage}">
                <div class="msg-success">${sessionScope.successMessage}</div>
                <c:remove var="successMessage" scope="session"/>
            </c:if>
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="msg-error">${sessionScope.errorMessage}</div>
                <c:remove var="errorMessage" scope="session"/>
            </c:if>

            <div style="margin-bottom: 15px;">
                <a href="${pageContext.request.contextPath}/MainController?action=showShop">← Quay lại cửa hàng</a>
            </div>

            <c:choose>
                <c:when test="${not empty product}">
                    <div style="background: #fff; border: 1px solid #e5e7eb; border-radius: 12px; padding: 24px;">
                        <div style="display: flex; gap: 30px; flex-wrap: wrap;">
                            <div style="flex: 0 0 360px;">
                                <div class="product-image-box" style="min-height: 320px;">
                                    <c:choose>
                                        <c:when test="${not empty product.imageUrl}">
                                            <img src="${pageContext.request.contextPath}/${product.imageUrl}" alt="${product.name}">
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color:#999;">Không có ảnh</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <div style="flex: 1; min-width: 300px;">
                                <h1 style="margin-top: 0; margin-bottom: 10px;">${product.name}</h1>

                                <div style="font-size: 28px; font-weight: bold; color: #e53935; margin-bottom: 20px;">
                                    <fmt:formatNumber value="${product.price}" type="number" pattern="#,##0"/> đ
                                </div>

                                <div style="display: grid; gap: 10px; margin-bottom: 20px;">
                                    <div>
                                        <strong>Danh mục:</strong>
                                        <c:choose>
                                            <c:when test="${not empty categoryName}">
                                                ${categoryName}
                                            </c:when>
                                            <c:otherwise>
                                                Chưa phân loại
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <c:if test="${not empty product.cpu}">
                                        <div><strong>CPU:</strong> ${product.cpu}</div>
                                    </c:if>

                                    <c:if test="${not empty product.ram}">
                                        <div><strong>RAM:</strong> ${product.ram}</div>
                                    </c:if>

                                    <c:if test="${not empty product.storage}">
                                        <div><strong>SSD:</strong> ${product.storage}</div>
                                    </c:if>

                                    <c:if test="${(empty product.cpu or empty product.ram or empty product.storage) and not empty product.description}">
                                        <div><strong>Mô tả:</strong> ${product.description}</div>
                                    </c:if>

                                    <div>
                                        <strong>Tình trạng:</strong>
                                        <c:choose>
                                            <c:when test="${product.stockQuantity > 0}">
                                                Còn hàng (${product.stockQuantity})
                                            </c:when>
                                            <c:otherwise>
                                                Hết hàng
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>

                                <div style="display: flex; gap: 10px; flex-wrap: wrap;">
                                    <c:choose>
                                        <c:when test="${product.stockQuantity > 0}">
                                            <a class="btn btn-primary"
                                               href="${pageContext.request.contextPath}/MainController?action=addToCart&productId=${product.id}&redirect=detail">
                                                Thêm vào giỏ hàng
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn btn-disabled" disabled>Hết hàng</button>
                                        </c:otherwise>
                                    </c:choose>

                                    <a class="btn btn-outline"
                                       href="${pageContext.request.contextPath}/MainController?action=showShop">
                                        Xem thêm sản phẩm
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:when>

                <c:otherwise>
                    <div class="box" style="text-align: center; margin-top: 20px;">
                        Không tìm thấy sản phẩm.
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <jsp:include page="/includes/footer.jsp" />
    </body>
</html>