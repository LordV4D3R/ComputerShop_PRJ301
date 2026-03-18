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

                    <form action="${pageContext.request.contextPath}/MainController?action=updateProduct" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="id" value="${product.id}"/>
                        <input type="hidden" name="oldImageUrl" value="${product.imageUrl}"/>

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
                                <label>Hình ảnh sản phẩm (Chọn file để đổi ảnh mới):</label>
                                <div style="display: flex; gap: 20px; align-items: flex-start; padding: 15px; border: 1px dashed #ccc; border-radius: 8px; background: #fafafa;">
                                    <div style="flex: 1;">
                                        <input type="file" name="imageFile" id="imageFile" accept="image/png, image/jpeg" onchange="previewImage(event)">
                                        <small style="color: #666; display: block;">Bỏ trống nếu muốn giữ nguyên ảnh hiện tại.</small>
                                    </div>
                                    <div style="width: 120px; height: 120px; background: #fff; border: 1px solid #ddd; border-radius: 8px; display: flex; align-items: center; justify-content: center; overflow: hidden;">
                                        <c:choose>
                                            <c:when test="${not empty product.imageUrl}">
                                                <img id="imgPreview" src="${pageContext.request.contextPath}/${product.imageUrl}" style="max-width: 100%; max-height: 100%;">
                                            </c:when>
                                            <c:otherwise>
                                                <img id="imgPreview" src="" style="max-width: 100%; max-height: 100%; display: none;">
                                                <span id="previewText" style="color: #999; font-size: 12px;">Chưa có ảnh</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
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