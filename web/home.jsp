<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Trang chủ | TechShop</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css">
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
        </div>
        <section class="hero">
            <div class="container hero-grid">
                <div class="hero-main">
                    <div class="hero-badge" style="background: #ffc107; color: #333;">Siêu sale công nghệ</div>
                    <h1>Mua laptop, PC, màn hình và phụ kiện giá tốt</h1>
                    <p>Phong cách bán hàng chuẩn e-commerce: dễ xem, dễ mua. Hỗ trợ khách vãng lai thanh toán nhanh chóng.</p>
                    <div class="hero-buttons">
                        <a class="btn" style="background: white; color: #1976d2; font-weight: bold;" href="${pageContext.request.contextPath}/MainController?action=showShop">Mua ngay</a>
                        <a class="btn btn-outline" href="${pageContext.request.contextPath}/MainController?action=showCart">Xem giỏ hàng</a>
                    </div>
                </div>

                <div class="hero-side">
                    <div class="promo-card promo-red">
                        <h3>Laptop Sinh Viên</h3>
                        <p>Giảm đến 15%</p>
                    </div>
                    <div class="promo-card promo-blue">
                        <h3>PC Gaming</h3>
                        <p>Cấu hình cực mạnh</p>
                    </div>
                    <div class="promo-card promo-dark">
                        <h3>Phụ kiện Gear</h3>
                        <p>Chuột, phím, tai nghe...</p>
                    </div>
                </div>
            </div>
        </section>

        <section class="quick-categories">
            <div class="container">
                <h2>Danh mục nổi bật</h2>
                <div class="category-grid">
                    <a class="category-card" href="${pageContext.request.contextPath}/MainController?action=showShop&keyword=Laptop">Laptop</a>
                    <a class="category-card" href="${pageContext.request.contextPath}/MainController?action=showShop&keyword=PC">PC Gaming</a>
                    <a class="category-card" href="${pageContext.request.contextPath}/MainController?action=showShop&keyword=Màn hình">Màn hình</a>
                    <a class="category-card" href="${pageContext.request.contextPath}/MainController?action=showShop&keyword=Bàn phím">Bàn phím</a>
                    <a class="category-card" href="${pageContext.request.contextPath}/MainController?action=showShop&keyword=Chuột">Chuột</a>
                    <a class="category-card" href="${pageContext.request.contextPath}/MainController?action=showShop&keyword=Tai nghe">Tai nghe</a>
                </div>
            </div>
        </section>

        <section class="featured-section">
            <div class="container">
                <div class="section-head">
                    <h2>Sản phẩm nổi bật</h2>
                    <div style="display: flex; gap: 10px;">
                        <c:if test="${not empty sessionScope.LOGIN_USER and sessionScope.LOGIN_USER.role eq 'CUSTOMER'}">
                            <a href="${pageContext.request.contextPath}/MainController?action=listWishlist">Wishlist của tôi</a>
                        </c:if>
                        <a href="${pageContext.request.contextPath}/MainController?action=showShop">Xem tất cả ›</a>
                    </div>
                </div>

                <c:choose>
                    <c:when test="${not empty listProduct}">
                        <div class="product-grid">
                            <c:forEach var="p" items="${listProduct}">
                                <div class="product-card">
                                    <div class="product-image-box">
                                        <c:choose>
                                            <c:when test="${not empty p.imageUrl}">
                                                <img src="${pageContext.request.contextPath}/${p.imageUrl}" alt="${p.name}">
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color:#999;">Không có ảnh</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="product-body">
                                        <div class="product-name">${p.name}</div>
                                        <div class="product-price">
                                            <fmt:formatNumber value="${p.price}" type="number" pattern="#,##0"/> đ
                                        </div>

                                        <div class="product-specs">
                                            <div><strong>CPU:</strong> ${p.cpu}</div>
                                            <div><strong>RAM:</strong> ${p.ram}</div>
                                            <div><strong>SSD:</strong> ${p.storage}</div>
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

                                            <div style="margin-top: 10px;">
                                                <c:choose>
                                                    <c:when test="${wishlistProductMap[p.id]}">
                                                        <a class="btn btn-outline" style="width: 100%; display: inline-block; text-align: center;"
                                                           href="${pageContext.request.contextPath}/MainController?action=deleteWishlist&productId=${p.id}&redirect=home">
                                                            Bỏ yêu thích
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a class="btn btn-outline" style="width: 100%; display: inline-block; text-align: center;"
                                                           href="${pageContext.request.contextPath}/MainController?action=insertWishlist&productId=${p.id}&redirect=home">
                                                            Yêu thích
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
                        <div class="box" style="text-align: center; margin-top: 20px; padding: 40px; color: #666;">
                            <em>Hiện tại chưa có sản phẩm nổi bật nào được cấu hình ở Trang chủ. Vui lòng vào cửa hàng để xem toàn bộ.</em><br><br>
                            <a href="${pageContext.request.contextPath}/MainController?action=showShop" class="btn btn-primary">Vào cửa hàng</a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>

        <jsp:include page="/includes/footer.jsp" />

    </body>
</html>