<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thanh toán QR | TechShop</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
    <style>
        .qr-grid { display: grid; grid-template-columns: 1fr 1.2fr; gap: 30px; margin-top: 30px; align-items: start; }
        .qr-wrap { text-align: center; padding: 30px; background: #fff; border-radius: 12px; border: 1px solid #e0e0e0; box-shadow: 0 4px 20px rgba(0,0,0,0.05); }
        .qr-image { max-width: 100%; width: 320px; border-radius: 8px; margin-bottom: 15px; border: 1px solid #eee; padding: 10px; }
        
        .info-box { background: #fff; border-radius: 12px; padding: 30px; border: 1px solid #e0e0e0; box-shadow: 0 4px 20px rgba(0,0,0,0.05); }
        .info-table { width: 100%; border-collapse: collapse; }
        .info-table th, .info-table td { padding: 16px 10px; border-bottom: 1px dashed #eee; text-align: left; vertical-align: middle; }
        .info-table th { width: 180px; color: #666; font-weight: normal; }
        .info-table td { font-weight: 600; color: #222; }
        
        /* Hiệu ứng nổi bật cho Giá tiền và Nội dung CK */
        .highlight-price { color: #d32f2f; font-size: 24px; font-weight: bold; }
        .highlight-content { background: #fdf2f3; padding: 6px 12px; border-radius: 6px; border: 1px dashed #d32f2f; color: #d32f2f; display: inline-block; font-family: monospace; font-size: 16px; }
        
        .action-row { display: flex; gap: 15px; margin-top: 25px; }
        
        /* Style cho phần Loading và Lỗi QR của bạn */
        .qr-status { margin-bottom: 15px; color: #666; font-size: 14px; font-style: italic; }
        .qr-loading { display: inline-block; padding: 12px 20px; border-radius: 8px; background: #e3f2fd; color: #1976d2; margin-bottom: 15px; font-weight: 500; border: 1px solid #bbdefb; }
        .qr-fallback { display: none; margin-top: 15px; padding: 20px; border: 1px dashed #d32f2f; border-radius: 10px; background: #fff8f8; color: #d32f2f; text-align: center; }
        .qr-fallback p { margin: 8px 0; color: #333; }
        .qr-retry-btn { margin-top: 15px; padding: 10px 20px; }
        .hidden { display: none !important; }

        @media (max-width: 768px) { .qr-grid { grid-template-columns: 1fr; } }
    </style>
</head>
<body>
    <jsp:include page="/includes/header.jsp" />

    <div class="container qr-grid">
        <div class="qr-wrap">
            <h2 style="margin-top: 0; color: #1976d2; font-size: 22px;">Quét mã để thanh toán</h2>
            
            <div id="qrLoadingBox" class="qr-loading">
                ⏳ Đang tải mã QR, vui lòng chờ...
            </div>

            <div id="qrStatus" class="qr-status">
                Hệ thống đang kết nối để lấy ảnh QR.
            </div>

            <img id="vietqrImg" src="${qrImageUrl}" alt="QR thanh toán" class="qr-image hidden">

            <div id="qrFallback" class="qr-fallback">
                <p><strong style="color: #d32f2f;">⚠️ Không tải được ảnh QR từ VietQR.</strong></p>
                <p style="font-size: 14px;">Bạn có thể bấm tải lại mã hoặc dùng thông tin bên phải để chuyển khoản thủ công.</p>
                <button type="button" class="btn btn-primary qr-retry-btn" onclick="reloadQrManually()">
                    🔄 Tải lại mã QR
                </button>
            </div>
            
            <div style="color: #666; font-size: 14px; line-height: 1.6; margin-top: 15px;">
                Mở ứng dụng ngân hàng và quét mã QR.<br>Đơn hàng sẽ được duyệt sau khi nhận tiền.
            </div>
        </div>

        <div class="info-box">
            <h2 style="margin-top: 0; border-bottom: 2px solid #f4f6f8; padding-bottom: 15px;">Chi tiết chuyển khoản</h2>

            <table class="info-table">
                <tr>
                    <th>Mã đơn hàng</th>
                    <td>#${order.id}</td>
                </tr>
                <tr>
                    <th>Ngân hàng nhận</th>
                    <td>${vietQrBankId}</td>
                </tr>
                <tr>
                    <th>Số tài khoản</th>
                    <td style="font-size: 18px;">${vietQrAccountNumber}</td>
                </tr>
                <tr>
                    <th>Chủ tài khoản</th>
                    <td>${vietQrAccountName}</td>
                </tr>
                <tr>
                    <th>Số tiền cần chuyển</th>
                    <td class="highlight-price"><fmt:formatNumber value="${order.totalPrice}" type="number" pattern="#,##0"/> đ</td>
                </tr>
                <tr>
                    <th>Nội dung chuyển khoản</th>
                    <td><span class="highlight-content">${transferContent}</span></td>
                </tr>
            </table>

            <div style="background: #fff3cd; color: #856404; padding: 12px; border-radius: 6px; margin-top: 20px; font-size: 14px; border-left: 4px solid #ffeeba;">
                <strong>⚠️ Lưu ý quan trọng:</strong> Vui lòng nhập chính xác <b>Nội dung chuyển khoản</b> và <b>Số tiền</b> để hệ thống nhận diện đơn hàng nhanh nhất.
            </div>

            <div class="action-row">
                <a class="btn btn-primary" style="flex: 1; padding: 14px;" href="${pageContext.request.contextPath}/MainController?action=showShop">Tiếp tục mua sắm</a>
                <c:if test="${sessionScope.LOGIN_USER != null && sessionScope.LOGIN_USER.role == 'CUSTOMER'}">
                    <a class="btn" style="background: #e0e0e0; color: #333; flex: 1; padding: 14px;" href="${pageContext.request.contextPath}/MainController?action=listMyOrder">Xem đơn hàng của tôi</a>
                </c:if>
            </div>
        </div>
    </div>

    <jsp:include page="/includes/footer.jsp" />

    <script>
        var qrImg = document.getElementById("vietqrImg");
        var qrLoadingBox = document.getElementById("qrLoadingBox");
        var qrStatus = document.getElementById("qrStatus");
        var qrFallback = document.getElementById("qrFallback");

        var retryCount = 0;
        var maxRetry = 2;
        var originalQrSrc = qrImg.src;

        function hideLoading() {
            qrLoadingBox.classList.add("hidden");
        }

        function showImage() {
            qrImg.classList.remove("hidden");
        }

        function hideImage() {
            qrImg.classList.add("hidden");
        }

        function showFallback() {
            qrFallback.style.display = "block";
        }

        function hideFallback() {
            qrFallback.style.display = "none";
        }

        function updateStatus(message) {
            qrStatus.textContent = message;
        }

        function buildRetryUrl() {
            var separator = originalQrSrc.indexOf("?") >= 0 ? "&" : "?";
            return originalQrSrc + separator + "_retry=" + new Date().getTime();
        }

        function reloadQrManually() {
            retryCount = 0;
            hideFallback();
            hideImage();
            qrLoadingBox.classList.remove("hidden");
            updateStatus("Đang tải lại mã QR...");
            qrImg.src = buildRetryUrl();
        }

        qrImg.onload = function () {
            hideLoading();
            hideFallback();
            showImage();
            updateStatus("✅ Mã QR đã sẵn sàng. Bạn có thể quét để thanh toán.");
            qrStatus.style.color = "#2e7d32"; // Đổi màu xanh lá khi thành công
        };

        qrImg.onerror = function () {
            if (retryCount < maxRetry) {
                retryCount++;
                updateStatus("Tải QR chậm, đang thử lại lần " + retryCount + "...");
                setTimeout(function () {
                    qrImg.src = buildRetryUrl();
                }, 1200);
                return;
            }

            hideLoading();
            hideImage();
            showFallback();
            updateStatus("❌ Không tải được mã QR. Bạn có thể bấm 'Tải lại mã QR'.");
            qrStatus.style.color = "#d32f2f"; // Đổi màu đỏ khi lỗi
        };
    </script>
</body>
</html>