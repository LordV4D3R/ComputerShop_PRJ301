<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cửa hàng | TechShop</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    </head>
    <body>
        <jsp:include page="/includes/header.jsp" />

        <div class="container" style="margin-top: 20px;">
            <c:if test="${not empty sessionScope.successMessage}">
                <div class="msg-success">${sessionScope.successMessage}</div>
                <c:remove var="successMessage" scope="session"/>
            </c:if>
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="msg-error">${sessionScope.errorMessage}</div>
                <c:remove var="errorMessage" scope="session"/>
            </c:if>

            <h2 style="margin-bottom: 5px;">Sản phẩm nổi bật</h2>
            <c:if test="${not empty sessionScope.LOGIN_USER and sessionScope.LOGIN_USER.role eq 'CUSTOMER'}">
                <div style="margin-bottom: 15px;">
                    <a class="btn btn-outline" href="${pageContext.request.contextPath}/MainController?action=listWishlist">
                        Wishlist của tôi
                    </a>
                </div>
            </c:if>
            <c:choose>
                <c:when test="${not empty listProduct}">
                    <div class="product-grid">
                        <c:forEach var="p" items="${listProduct}">
                            <div class="product-card">
                                <div class="product-image-box">
                                    <a href="${pageContext.request.contextPath}/MainController?action=showProductDetail&id=${p.id}">
                                        <c:choose>
                                            <c:when test="${not empty p.imageUrl}">
                                                <img src="${pageContext.request.contextPath}/${p.imageUrl}" alt="${p.name}">
                                            </c:when>
                                            <c:otherwise><span style="color:#999;">No Image</span></c:otherwise>
                                        </c:choose>
                                    </a>
                                </div>
                                <div class="product-body">
                                    <div class="product-name">
                                        <a href="${pageContext.request.contextPath}/MainController?action=showProductDetail&id=${p.id}" style="text-decoration: none; color: inherit;">
                                            ${p.name}
                                        </a>
                                    </div>
                                    <div class="product-price">
                                        <fmt:formatNumber value="${p.price}" type="number" pattern="#,##0"/> đ
                                    </div>
                                    <div class="rating-stars">
                                        ⭐ <fmt:formatNumber value="${ratingMap[p.id]}" pattern="0.0"/>
                                        <span class="rating-count">(${countMap[p.id]} đánh giá)</span>
                                    </div>
                                    <div class="product-specs">
                                        <c:if test="${not empty p.cpu}">
                                            <div><strong>CPU:</strong> ${p.cpu}</div>
                                        </c:if>

                                        <c:if test="${not empty p.ram}">
                                            <div><strong>RAM:</strong> ${p.ram}</div>
                                        </c:if>

                                        <c:if test="${not empty p.storage}">
                                            <div><strong>SSD:</strong> ${p.storage}</div>
                                        </c:if>

                                        <c:if test="${empty p.cpu and empty p.ram and empty p.storage and not empty p.description}">
                                            <div class="text-truncate-2"><strong>Mô tả:</strong> ${p.description}</div>
                                        </c:if>
                                    </div>

                                    <div style="margin-top: auto;">
                                        <c:choose>
                                            <c:when test="${p.stockQuantity > 0}">
                                                <a class="btn btn-primary" style="width: 100%;" href="${pageContext.request.contextPath}/MainController?action=addToCart&productId=${p.id}">
                                                    Mua ngay
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <button class="btn btn-disabled" style="width: 100%;" disabled>Hết hàng</button>
                                            </c:otherwise>
                                        </c:choose>

                                        <div style="margin-top: 10px; text-align: right;">
                                            <c:choose>
                                                <c:when test="${wishlistProductMap[p.id]}">
                                                    <a class="wishlist-heart active" title="Bỏ yêu thích"
                                                       href="${pageContext.request.contextPath}/MainController?action=deleteWishlist&productId=${p.id}&redirect=shop">
                                                        ♥️
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a class="wishlist-heart" title="Thêm vào yêu thích"
                                                       href="${pageContext.request.contextPath}/MainController?action=insertWishlist&productId=${p.id}&redirect=shop">
                                                        ♡
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="box" style="text-align: center; margin-top: 20px;">Không tìm thấy sản phẩm.</div>
                </c:otherwise>
            </c:choose>
        </div>

        <jsp:include page="/includes/footer.jsp" />
    </body>
</html>