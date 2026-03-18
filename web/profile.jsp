<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thông tin cá nhân | TechShop</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
    <style>
        .profile-container { display: grid; grid-template-columns: 1fr 2fr; gap: 30px; max-width: 1000px; }
        @media (max-width: 768px) { .profile-container { grid-template-columns: 1fr; } }
        .avatar-box { background: #fff; padding: 30px; border-radius: 10px; border: 1px solid #e0e0e0; text-align: center; height: fit-content; }
        .avatar-preview { width: 180px; height: 180px; border-radius: 50%; object-fit: cover; border: 4px solid #f4f6f8; box-shadow: 0 4px 10px rgba(0,0,0,0.1); margin-bottom: 20px; }
        .file-upload-btn { position: relative; overflow: hidden; display: inline-block; margin-top: 10px; }
        .file-upload-btn input[type=file] { font-size: 100px; position: absolute; left: 0; top: 0; opacity: 0; cursor: pointer; }
    </style>
</head>
<body>
    <div class="dashboard-wrapper">
        
        <c:choose>
            <c:when test="${sessionScope.LOGIN_USER.role eq 'CUSTOMER'}">
                <jsp:include page="/includes/sidebar_user.jsp" />
            </c:when>
            <c:otherwise>
                <jsp:include page="/includes/sidebar_staff.jsp" />
            </c:otherwise>
        </c:choose>

        <div class="main-content">
            <h2 class="page-title">Hồ sơ cá nhân</h2>

            <c:if test="${not empty sessionScope.successMessage}">
                <div style="background: #d4edda; color: #155724; padding: 12px; border-radius: 6px; margin-bottom: 16px;">${sessionScope.successMessage}</div>
                <c:remove var="successMessage" scope="session"/>
            </c:if>

            <c:if test="${not empty error}">
                <div style="background: #ffebee; color: #c62828; padding: 12px; border-radius: 6px; margin-bottom: 16px;">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/MainController?action=updateProfile" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="updateProfile">

                <div class="profile-container">
                    <div class="avatar-box">
                        <c:choose>
                            <c:when test="${not empty sessionScope.LOGIN_USER.imageUrl}">
                                <img id="imgPreview" class="avatar-preview" src="${pageContext.request.contextPath}/${sessionScope.LOGIN_USER.imageUrl}" alt="Avatar">
                            </c:when>
                            <c:otherwise>
                                <img id="imgPreview" class="avatar-preview" src="${pageContext.request.contextPath}/assets/images/default-avatar.png" alt="Avatar">
                            </c:otherwise>
                        </c:choose>
                        
                        <div style="color: #666; font-size: 13px;">Dung lượng file tối đa 2MB.</div>
                        <div style="color: #666; font-size: 13px; margin-bottom: 15px;">Định dạng: .JPEG, .PNG</div>
                        
                        <div class="btn btn-primary file-upload-btn">
                            <span>📷 Chọn ảnh mới</span>
                            <input type="file" name="avatarFile" id="avatarFile" accept="image/png, image/jpeg" onchange="previewImage(event)">
                        </div>
                    </div>

                    <div class="form-container" style="max-width: 100%;">
                        <div class="form-grid">
                            <div class="form-group">
                                <label>Tên đăng nhập (Username):</label>
                                <input type="text" value="${sessionScope.LOGIN_USER.username}" disabled style="background: #f5f5f5; color: #777;">
                            </div>

                            <div class="form-group">
                                <label>Đổi mật khẩu mới:</label>
                                <input type="password" name="newPassword" placeholder="Bỏ trống nếu không muốn đổi">
                            </div>

                            <div class="form-group">
                                <label>Họ và tên:</label>
                                <input type="text" name="fullname" value="${sessionScope.LOGIN_USER.fullname}">
                            </div>

                            <div class="form-group">
                                <label>Email:</label>
                                <input type="email" name="email" value="${sessionScope.LOGIN_USER.email}">
                            </div>

                            <div class="form-group">
                                <label>Số điện thoại:</label>
                                <input type="text" name="phoneNumber" value="${sessionScope.LOGIN_USER.phoneNumber}">
                            </div>

                            <div class="form-group full-width">
                                <label>Địa chỉ:</label>
                                <textarea name="address" rows="3">${sessionScope.LOGIN_USER.address}</textarea>
                            </div>

                            <div class="form-actions">
                                <button type="submit" class="btn btn-primary" style="padding: 12px 30px;">Lưu thay đổi</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <script>
        function previewImage(event) {
            var input = event.target;
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById('imgPreview').src = e.target.result;
                }
                reader.readAsDataURL(input.files[0]);
            }
        }
    </script>
</body>
</html>