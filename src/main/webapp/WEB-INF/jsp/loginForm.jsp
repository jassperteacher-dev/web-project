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
        
        
        <%-- URL 파라미터에 'error'가 있으면 에러 메시지 표시 --%>
        <% if (request.getParameter("error") != null) { %>
            <p class="error-message">아이디 또는 비밀번호가 일치하지 않습니다.</p>
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