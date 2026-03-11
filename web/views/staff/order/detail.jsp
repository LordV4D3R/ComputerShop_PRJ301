<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết đơn hàng</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/assets/css/main.css">

    <style>
        .detail-wrapper {
            width: 95%;
            max-width: 1200px;
            margin: 30px auto;
        }

        .detail-box {
            background: #fff;
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 20px;
        }

        .detail-title {
            margin-top: 0;
            margin-bottom: 16px;
            font-size: 24px;
            color: #222;
        }

        .info-grid {
            display: grid;
            grid-template-columns: repeat(2, minmax(250px, 1fr));
            gap: 12px 24px;
        }

        .info-item strong {
            color: #333;
        }

        .badge-pending {
            display: inline-block;
            padding: 5px 10px;
            border-radius: 20px;
            background: #fff3cd;
            color: #856404;
            font-weight: bold;
            font-size: 13px;
        }

        .badge-approved {
            display: inline-block;
            padding: 5px 10px;
            border-radius: 20px;
            background: #d4edda;
            color: #155724;
            font-weight: bold;
            font-size: 13px;
        }

        .badge-cancelled {
            display: inline-block;
            padding: 5px 10px;
            border-radius: 20px;
            background: #f8d7da;
            color: #721c24;
            font-weight: bold;
            font-size: 13px;
        }

        .action-row {
            margin-top: 16px;
        }

        .btn-back,
        .btn-approve,
        .btn-cancel-order {
            display: inline-block;
            padding: 10px 14px;
            border-radius: 6px;
            color: white;
            text-decoration: none;
            font-weight: bold;
            margin-right: 8px;
        }

        .btn-back {
            background: #6c757d;
        }

        .btn-approve {
            background: #2e7d32;
        }

        .btn-cancel-order {
            background: #d32f2f;
        }

        .item-table {
            width: 100%;
            border-collapse: collapse;
            background: #fff;
        }

        .item-table th,
        .item-table td {
            border: 1px solid #ddd;
            padding: 12px;
            vertical-align: middle;
            text-align: center;
        }

        .item-table th {
            background: #f5f5f5;
        }

        .product-cell {
            text-align: left;
        }

        .product-name {
            font-weight: bold;
            margin-bottom: 4px;
        }

        .product-id {
            color: #777;
            font-size: 13px;
        }
    </style>
</head>
<body>

    <div class="detail-wrapper">

        <div class="detail-box">
            <h2 class="detail-title">Chi tiết đơn hàng</h2>

            <div class="info-grid">
                <div class="info-item"><strong>Mã đơn:</strong> ${order.id}</div>
                <div class="info-item">
                    <strong>Trạng thái:</strong>
                    <c:choose>
                        <c:when test="${order.status eq 'PENDING'}">
                            <span class="badge-pending">PENDING</span>
                        </c:when>
                        <c:when test="${order.status eq 'APPROVED'}">
                            <span class="badge-approved">APPROVED</span>
                        </c:when>
                        <c:when test="${order.status eq 'CANCELLED'}">
                            <span class="badge-cancelled">CANCELLED</span>
                        </c:when>
                        <c:otherwise>
                            ${order.status}
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="info-item"><strong>Khách hàng:</strong> ${order.fullname}</div>
                <div class="info-item"><strong>Số điện thoại:</strong> ${order.phoneNumber}</div>
                <div class="info-item"><strong>Email:</strong> ${order.email}</div>
                <div class="info-item"><strong>Tổng tiền:</strong> <fmt:formatNumber value="${order.totalPrice}" type="number" pattern="#,##0"/> đ</div>
                <div class="info-item" style="grid-column: 1 / -1;"><strong>Địa chỉ:</strong> ${order.address}</div>
            </div>

            <div class="action-row">
                <a class="btn-back"
                   href="${pageContext.request.contextPath}/MainController?action=listOrder">
                    Quay lại
                </a>

                <c:if test="${order.status eq 'PENDING'}">
                    <a class="btn-approve"
                       href="${pageContext.request.contextPath}/MainController?action=approveOrder&id=${order.id}"
                       onclick="return confirm('Duyệt đơn hàng này?');">
                        Approve
                    </a>

                    <a class="btn-cancel-order"
                       href="${pageContext.request.contextPath}/MainController?action=cancelOrder&id=${order.id}"
                       onclick="return confirm('Hủy đơn hàng này?');">
                        Cancel
                    </a>
                </c:if>
            </div>
        </div>

        <div class="detail-box">
            <h2 class="detail-title">Danh sách sản phẩm trong đơn</h2>

            <table class="item-table">
                <thead>
                    <tr>
                        <th>Ảnh</th>
                        <th>Sản phẩm</th>
                        <th>Đơn giá</th>
                        <th>Số lượng</th>
                        <th>Thành tiền</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty orderItemViews}">
                            <c:forEach var="item" items="${orderItemViews}">
                                <tr>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty item.imageUrl}">
                                                <img src="${pageContext.request.contextPath}/${item.imageUrl}"
                                                     alt="${item.productName}" width="80">
                                            </c:when>
                                            <c:otherwise>
                                                Không có ảnh
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td class="product-cell">
                                        <div class="product-name">${item.productName}</div>
                                        <div class="product-id">Product ID: ${item.productId}</div>
                                    </td>

                                    <td>
                                        <fmt:formatNumber value="${item.price}" type="number" pattern="#,##0"/> đ
                                    </td>

                                    <td>${item.quantity}</td>

                                    <td>
                                        <fmt:formatNumber value="${item.subtotal}" type="number" pattern="#,##0"/> đ
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>

                        <c:otherwise>
                            <tr>
                                <td colspan="5" style="text-align:center; padding:30px; color:#777;">
                                    Đơn hàng này chưa có sản phẩm.
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

    </div>

</body>
</html>