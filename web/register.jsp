<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f8;
            margin: 0;
        }

        .auth-box {
            width: 480px;
            margin: 40px auto;
            background: #fff;
            padding: 24px;
            border-radius: 10px;
            box-shadow: 0 4px 18px rgba(0,0,0,0.08);
        }

        .auth-box h2 {
            margin-top: 0;
            text-align: center;
        }

        .form-group {
            margin-bottom: 14px;
        }

        .form-group label {
            display: block;
            margin-bottom: 6px;
            font-weight: bold;
        }

        .form-group input {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
        }

        .btn {
            width: 100%;
            padding: 10px;
            border: none;
            background: #2e7d32;
            color: white;
            cursor: pointer;
            border-radius: 6px;
        }

        .link-row {
            margin-top: 12px;
            text-align: center;
        }

        .error {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="auth-box">
        <h2>Đăng ký tài khoản</h2>

        <c:if test="${not empty requestScope.error}">
            <div class="error">${requestScope.error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="register"/>

            <div class="form-group">
                <label>Username</label>
                <input type="text" name="username" value="${username}" required/>
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" name="hashPassword" required/>
            </div>

            <div class="form-group">
                <label>Confirm Password</label>
                <input type="password" name="confirmPassword" required/>
            </div>

            <div class="form-group">
                <label>Họ tên</label>
                <input type="text" name="fullname" value="${fullname}"/>
            </div>

            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" value="${email}"/>
            </div>

            <div class="form-group">
                <label>Số điện thoại</label>
                <input type="text" name="phoneNumber" value="${phoneNumber}"/>
            </div>

            <div class="form-group">
                <label>Địa chỉ</label>
                <input type="text" name="address" value="${address}"/>
            </div>

            <button type="submit" class="btn">Đăng ký</button>
        </form>

        <div class="link-row">
            Đã có tài khoản?
            <a href="${pageContext.request.contextPath}/MainController?action=showLogin">Đăng nhập</a>
        </div>
    </div>
</body>
</html>