<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập | TechShop</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp" />

    <div class="container">
        <div class="box auth-box">
            <h2 style="text-align: center; margin-top: 0;">Đăng nhập</h2>

            <c:if test="${not empty requestScope.error}">
                <div class="msg-error">${requestScope.error}</div>
            </c:if>
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="msg-error">${sessionScope.errorMessage}</div>
                <c:remove var="errorMessage" scope="session"/>
            </c:if>
            <c:if test="${not empty sessionScope.successMessage}">
                <div class="msg-success">${sessionScope.successMessage}</div>
                <c:remove var="successMessage" scope="session"/>
            </c:if>

            <form action="${pageContext.request.contextPath}/MainController" method="post">
                <input type="hidden" name="action" value="login"/>
                
                <div class="form-group">
                    <label>Tài khoản</label>
                    <input type="text" name="username" value="${username}" required/>
                </div>
                <div class="form-group">
                    <label>Mật khẩu</label>
                    <input type="password" name="hashPassword" required/>
                </div>
                
                <button type="submit" class="btn btn-primary" style="width: 100%;">Đăng nhập</button>
            </form>

            <div style="text-align: center; margin-top: 15px; font-size: 14px;">
                Chưa có tài khoản? <a href="${pageContext.request.contextPath}/MainController?action=showRegister" style="color: #d70018; font-weight: bold;">Đăng ký ngay</a>
            </div>
        </div>
    </div>
</body>
</html>