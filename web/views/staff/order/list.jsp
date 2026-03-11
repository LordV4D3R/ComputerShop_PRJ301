<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách đơn hàng</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/assets/css/main.css">

    <style>
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

        .message-success {
            background: #e8f5e9;
            color: #2e7d32;
            border: 1px solid #c8e6c9;
            padding: 12px 16px;
            border-radius: 6px;
            margin-bottom: 16px;
        }

        .message-error {
            background: #ffebee;
            color: #c62828;
            border: 1px solid #ffcdd2;
            padding: 12px 16px;
            border-radius: 6px;
            margin-bottom: 16px;
        }

        .btn-detail {
            display: inline-block;
            padding: 8px 12px;
            background: #1976d2;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            margin-right: 6px;
        }

        .btn-approve {
            display: inline-block;
            padding: 8px 12px;
            background: #2e7d32;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            margin-right: 6px;
        }

        .btn-cancel-order {
            display: inline-block;
            padding: 8px 12px;
            background: #d32f2f;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
        }
    </style>
</head>
<body>

    <h2 class="page-title">Danh sách đơn hàng</h2>

    <c:if test="${not empty sessionScope.successMessage}">
        <div class="message-success">
            ${sessionScope.successMessage}
        </div>
        <c:remove var="successMessage" scope="session"/>
    </c:if>

    <c:if test="${not empty sessionScope.errorMessage}">
        <div class="message-error">
            ${sessionScope.errorMessage}
        </div>
        <c:remove var="errorMessage" scope="session"/>
    </c:if>

    <div class="table-container" style="overflow-x: auto;">
        <table style="min-width: 1200px;">
            <thead>
                <tr>
                    <th>#</th>
                    <th>ID</th>
                    <th>Khách hàng</th>
                    <th>Số điện thoại</th>
                    <th>Email</th>
                    <th>Địa chỉ</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
                    <th style="text-align:center;">Nghiệp vụ staff</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty listOrder}">
                        <c:forEach var="o" items="${listOrder}" varStatus="st">
                            <tr>
                                <td>${st.count}</td>
                                <td>${o.id}</td>
                                <td>${o.fullname}</td>
                                <td>${o.phoneNumber}</td>
                                <td>${o.email}</td>
                                <td>${o.address}</td>
                                <td>
                                    <fmt:formatNumber value="${o.totalPrice}" type="number" pattern="#,##0"/> đ
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${o.status eq 'PENDING'}">
                                            <span class="badge-pending">PENDING</span>
                                        </c:when>
                                        <c:when test="${o.status eq 'APPROVED'}">
                                            <span class="badge-approved">APPROVED</span>
                                        </c:when>
                                        <c:when test="${o.status eq 'CANCELLED'}">
                                            <span class="badge-cancelled">CANCELLED</span>
                                        </c:when>
                                        <c:otherwise>
                                            ${o.status}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td style="text-align:center; white-space: nowrap;">
                                    <a class="btn-detail"
                                       href="${pageContext.request.contextPath}/MainController?action=showOrderDetail&id=${o.id}">
                                        Chi tiết
                                    </a>

                                    <c:choose>
                                        <c:when test="${o.status eq 'PENDING'}">
                                            <a class="btn-approve"
                                               href="${pageContext.request.contextPath}/MainController?action=approveOrder&id=${o.id}"
                                               onclick="return confirm('Duyệt đơn hàng này?');">
                                                Approve
                                            </a>

                                            <a class="btn-cancel-order"
                                               href="${pageContext.request.contextPath}/MainController?action=cancelOrder&id=${o.id}"
                                               onclick="return confirm('Hủy đơn hàng này?');">
                                                Cancel
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color:#777;">Đã xử lý</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>

                    <c:otherwise>
                        <tr>
                            <td colspan="9" style="text-align:center; padding:30px; color:#777;">
                                Chưa có đơn hàng nào.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>

</body>
</html>