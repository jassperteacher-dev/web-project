<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>로그인</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <div class="container">
        <h1 class="form-title">로그인</h1>
        
        
        <% if (request.getAttribute("error") != null) { %>
            <p class="error-message">${error}</p>
        <% } %>

        <form action="/loginAction" method="post">
            <div class="form-group">
                <label>아이디</label>
                <input type="text" name="userId" required>
            </div>
            <div class="form-group">
                <label>비밀번호</label>
                <input type="password" name="userPw" required>
            </div>
            <button type="submit" class="btn">로그인</button>
        </form>
        
        <div class="link-group">
            <a href="/joinForm">회원가입</a>
        </div>
    </div>
</body>
</html>