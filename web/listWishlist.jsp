<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Wishlist của tôi</title>
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

        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <h2 class="page-title" style="margin: 0;">Wishlist của tôi</h2>
            <a class="btn btn-outline" href="${pageContext.request.contextPath}/MainController?action=showShop">
                Tiếp tục mua sắm
            </a>
        </div>

        <c:choose>
            <c:when test="${not empty listWishlist}">
                <div class="product-grid">
                    <c:forEach var="w" items="${listWishlist}">
                        <c:set var="p" value="${productMap[w.productId]}"/>

                        <div class="product-card">
                            <div class="product-image-box">
                                <c:choose>
                                    <c:when test="${not empty p and not empty p.imageUrl}">
                                        <img src="${pageContext.request.contextPath}/${p.imageUrl}" alt="${p.name}">
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color:#999;">Không có ảnh</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <div class="product-body">
                                <c:choose>
                                    <c:when test="${not empty p}">
                                        <div class="product-name">${p.name}</div>
                                        <div class="product-price">
                                            <fmt:formatNumber value="${p.price}" type="number" pattern="#,##0"/> đ
                                        </div>

                                        <div class="product-specs">
                                            <div><strong>CPU:</strong> ${p.cpu}</div>
                                            <div><strong>RAM:</strong> ${p.ram}</div>
                                            <div><strong>SSD:</strong> ${p.storage}</div>
                                            <div><strong>Đã thêm lúc:</strong> ${w.createdAt}</div>
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
                                                <a class="btn btn-outline" style="width: 100%; display: inline-block; text-align: center;"
                                                   href="${pageContext.request.contextPath}/MainController?action=deleteWishlist&id=${w.id}&redirect=wishlist"
                                                   onclick="return confirm('Xóa sản phẩm này khỏi wishlist?');">
                                                    Xóa khỏi wishlist
                                                </a>
                                            </div>
                                        </div>
                                    </c:when>

                                    <c:otherwise>
                                        <div class="product-name">Sản phẩm không còn tồn tại</div>
                                        <div class="product-specs">
                                            <div>Wishlist này đang trỏ tới sản phẩm đã bị xóa hoặc ẩn.</div>
                                            <div><strong>Đã thêm lúc:</strong> ${w.createdAt}</div>
                                        </div>

                                        <div style="margin-top: auto;">
                                            <a class="btn btn-outline" style="width: 100%; display: inline-block; text-align: center;"
                                               href="${pageContext.request.contextPath}/MainController?action=deleteWishlist&id=${w.id}&redirect=wishlist"
                                               onclick="return confirm('Xóa mục này khỏi wishlist?');">
                                                Xóa khỏi wishlist
                                            </a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>

            <c:otherwise>
                <div class="box" style="text-align: center; padding: 40px;">
                    <p>Bạn chưa có sản phẩm nào trong wishlist.</p>
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/MainController?action=showShop">
                        Đi đến cửa hàng
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <jsp:include page="/includes/footer.jsp" />

</body>
</html>