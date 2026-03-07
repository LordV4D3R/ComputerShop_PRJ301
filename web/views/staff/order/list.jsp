<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Order</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
</head>
<body>

    <h2 class="page-title">Danh sách Order</h2>

    <c:if test="${not empty error}">
        <p class="text-danger">${error}</p>
    </c:if>

    <div style="margin-bottom: 15px;">
        <a class="btn btn-add" href="${pageContext.request.contextPath}/MainController?action=showCreateOrder">
            + Thêm Order
        </a>
    </div>

    <div class="table-container" style="overflow-x: auto;">
        <table style="min-width: 1000px;">
            <thead>
                <tr>
                    <th>#</th>
                    <th>ID</th>
                    <th>Họ tên</th>
                    <th>SĐT</th>
                    <th>Email</th>
                    <th>Địa chỉ</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
                    <th>Ngày tạo</th>
                    <th style="text-align: center;">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty listOrder}">
                        <c:forEach var="o" items="${listOrder}" varStatus="st">
                            <tr>
                                <td>${st.count}</td>
                                <td>${o.id}</td>
                                <td><strong>${o.fullname}</strong></td>
                                <td>${o.phoneNumber}</td>
                                <td>${o.email}</td>
                                <td>${o.address}</td>
                                <td><strong style="color: #e74c3c;">${o.totalPrice}</strong></td>
                                <td>
                                    <span style="padding: 4px 8px; border-radius: 4px; font-size: 0.85rem; color: #fff; background-color: ${o.status == 'Paid' ? '#2ecc71' : (o.status == 'Pending' ? '#f39c12' : '#e74c3c')}">
                                        ${o.status}
                                    </span>
                                </td>
                                <td>${o.createdDate}</td>
                                <td style="text-align: center; white-space: nowrap;">
                                    <a class="btn btn-edit" href="${pageContext.request.contextPath}/MainController?action=editOrder&id=${o.id}">
                                        Edit
                                    </a>
                                    <a class="btn btn-delete" href="${pageContext.request.contextPath}/MainController?action=deleteOrder&id=${o.id}" onclick="return confirm('Xóa order này?');">
                                        Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="10" style="text-align: center; padding: 20px;">
                                Chưa có order nào.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>

</body>
</html>