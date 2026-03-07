<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Thêm mới Sản phẩm</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
</head>
<body>

    <h2 class="page-title">Thêm mới Sản phẩm</h2>

    <div class="form-container">
        <form action="${pageContext.request.contextPath}/MainController" method="POST">
            <input type="hidden" name="action" value="insertProduct">

            <div class="form-grid">
                <div class="form-group full-width">
                    <label>Tên sản phẩm:</label>
                    <input type="text" name="name" required>
                </div>

                <div class="form-group">
                    <label>Mã Danh mục (Category ID):</label>
                    <input type="text" name="categoryId" required>
                </div>

                <div class="form-group">
                    <label>Thương hiệu (Brand):</label>
                    <input type="text" name="brand">
                </div>

                <div class="form-group">
                    <label>CPU:</label>
                    <input type="text" name="cpu">
                </div>

                <div class="form-group">
                    <label>RAM:</label>
                    <input type="text" name="ram">
                </div>

                <div class="form-group">
                    <label>Ổ cứng (Storage):</label>
                    <input type="text" name="storage">
                </div>

                <div class="form-group">
                    <label>Giá bán (Price):</label>
                    <input type="number" name="price" step="0.01" required>
                </div>

                <div class="form-group">
                    <label>Số lượng kho (Stock):</label>
                    <input type="number" name="stockQuantity" required>
                </div>

                <div class="form-group full-width">
                    <label>URL Hình ảnh:</label>
                    <input type="text" name="imageUrl" placeholder="Ví dụ: assets/images/products/laptop1.jpg">
                </div>

                <div class="form-group full-width">
                    <label>Mô tả chi tiết:</label>
                    <textarea name="description" rows="4"></textarea>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-add">Lưu Sản Phẩm</button>
                    <a href="${pageContext.request.contextPath}/MainController?action=listProduct" class="btn btn-secondary">Hủy</a>
                </div>
            </div>
        </form>
    </div>

</body>
</html>