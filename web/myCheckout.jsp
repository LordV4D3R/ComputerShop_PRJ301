<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thanh toán | TechShop</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
        <style>
            .checkout-grid {
                display: grid;
                grid-template-columns: 3fr 2fr;
                gap: 20px;
                margin-top: 30px;
            }
            @media (max-width: 768px) {
                .checkout-grid {
                    grid-template-columns: 1fr;
                }
            }
        </style>
    </head>
    <body>
        <jsp:include page="/includes/header.jsp" />

        <div class="container checkout-grid">
            <div class="box">
                <h2 style="margin-top: 0; border-bottom: 2px solid #f4f6f8; padding-bottom: 10px;">Thông tin nhận hàng</h2>

                <c:if test="${not empty error}">
                    <div class="msg-error">${error}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/MainController" method="post">
                    <input type="hidden" name="action" value="placeOrderFromCart">

                    <div class="form-group">
                        <label>Họ và tên</label>
                        <input type="text" name="fullname" value="${checkoutFullname}" required>
                    </div>
                    <div class="form-group">
                        <label>Số điện thoại</label>
                        <input type="text" name="phoneNumber" value="${checkoutPhoneNumber}" required>
                    </div>
                    <div class="form-group">
                        <label>Email <span style="color:red;">*</span></label>
                        <input type="email" name="email" value="${checkoutEmail}" required>
                    </div>
                    <div class="form-group">
                        <label>Địa chỉ nhận hàng</label>
                        <textarea name="address" rows="3" required>${checkoutAddress}</textarea>
                    </div>

                    <div class="msg-success" style="background: #f8f9fa; border-left: 4px solid #1976d2; color: #333;">
                        Bạn đang thanh toán bằng tài khoản khách hàng đã đăng nhập. Sau khi đặt hàng, hệ thống sẽ chuyển sang trang QR để bạn thanh toán.
                    </div>

                    <button type="submit" class="btn btn-primary" style="width: 100%; font-size: 16px; padding: 12px;">ĐẶT HÀNG & XEM QR</button>
                </form>
            </div>

            <div class="box">
                <h2 style="margin-top: 0; border-bottom: 2px solid #f4f6f8; padding-bottom: 10px;">Đơn hàng của bạn</h2>
                <c:if test="${not empty cartItems}">
                    <table class="data-table" style="box-shadow: none;">
                        <tbody>
                            <c:forEach var="item" items="${cartItems}">
                                <tr>
                                    <td style="padding: 10px 0;">
                                        <strong>${item.product.name}</strong><br>
                                        <span style="color:#666;">SL: ${item.quantity}</span>
                                    </td>
                                    <td style="padding: 10px 0; text-align: right; font-weight: bold;">
                                        <fmt:formatNumber value="${item.subtotal}" type="number" pattern="#,##0"/> đ
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <h3 style="display: flex; justify-content: space-between; border-top: 1px solid #ddd; padding-top: 15px; margin-bottom: 0;">
                        <span>Tổng cộng:</span>
                        <span style="color: #d70018;"><fmt:formatNumber value="${cartTotal}" type="number" pattern="#,##0"/> đ</span>
                    </h3>
                </c:if>
            </div>
        </div>

        <jsp:include page="/includes/footer.jsp" />
    </body>
</html>