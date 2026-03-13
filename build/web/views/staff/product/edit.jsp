<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cập nhật Sản phẩm | Staff</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
    </head>
    <body>
        <div class="dashboard-wrapper">

            <jsp:include page="/includes/sidebar_staff.jsp" />

            <div class="main-content">
                <h2 class="page-title">Cập nhật Sản phẩm: <span style="color: #1976d2;">${product.name}</span></h2>

                <div class="form-container">
                    <c:if test="${not empty error}">
                        <div style="color: red; margin-bottom: 15px;">${error}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/MainController" method="post">
                        <input type="hidden" name="action" value="updateProduct"/>
                        <input type="hidden" name="id" value="${product.id}"/>

                        <div class="form-grid">
                            <div class="form-group full-width">
                                <label>Tên sản phẩm: <span style="color:red;">*</span></label>
                                <input type="text" name="name" value="${product.name}" required />
                            </div>

                            <div class="form-group">
                                <label>Danh mục: <span style="color:red;">*</span></label>
                                <select name="categoryId" required>
                                    <option value="">-- Chọn danh mục --</option>
                                    <c:forEach var="c" items="${listCategory}">
                                        <option value="${c.id}" <c:if test="${product.categoryId eq c.id}">selected="selected"</c:if>>
                                            ${c.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>Thương hiệu (Brand):</label>
                                <input type="text" name="brand" value="${product.brand}" />
                            </div>

                            <div class="form-group">
                                <label>CPU:</label>
                                <input type="text" name="cpu" value="${product.cpu}" />
                            </div>

                            <div class="form-group">
                                <label>RAM:</label>
                                <input type="text" name="ram" value="${product.ram}" />
                            </div>

                            <div class="form-group">
                                <label>Ổ cứng (Storage):</label>
                                <input type="text" name="storage" value="${product.storage}" />
                            </div>

                            <div class="form-group">
                                <label>Giá bán (Price): <span style="color:red;">*</span></label>
                                <input type="number" name="price" step="0.01" value="${product.price}" required />
                            </div>

                            <div class="form-group">
                                <label>Số lượng kho (Stock): <span style="color:red;">*</span></label>
                                <input type="number" name="stockQuantity" value="${product.stockQuantity}" required />
                            </div>

                            <div class="form-group full-width">
                                <label>URL Hình ảnh:</label>
                                <input type="text" name="imageUrl" value="${product.imageUrl}" />
                            </div>

                            <div class="form-group full-width">
                                <label>Mô tả chi tiết:</label>
                                <textarea name="description" rows="4">${product.description}</textarea>
                            </div>

                            <div class="form-actions">
                                <button type="submit" class="btn btn-primary" style="padding: 10px 25px;">Cập nhật</button>
                                <a href="${pageContext.request.contextPath}/MainController?action=listProduct" class="btn" style="background: #e0e0e0; color: #333; padding: 10px 25px;">Hủy</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>