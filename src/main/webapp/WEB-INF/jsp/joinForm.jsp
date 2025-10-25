<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원가입</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <h1>회원가입</h1>
    <form action="/joinAction" method="post">
        <div class="form-group"><label>아이디</label><input type="text" name="userId" required></div>
        <div class="form-group"><label>비밀번호</label><input type="password" name="userPw" required></div>
        <div class="form-group"><label>이름</label><input type="text" name="userName" required></div>
        <button type="submit" class="btn">가입하기</button>
    </form>
</div>
</body>
</html>