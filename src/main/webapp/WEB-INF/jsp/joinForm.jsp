<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원가입</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <h1 class="form-title">회원가입</h1>
    <form id="joinForm" action="/joinAction" method="post">
        <div class="form-group">
            <label>아이디</label>
            <input type="text" id="userId" name="userId" required>
            <p id="idCheckMessage" style="font-size: 12px;"></p>
        </div>
        <div class="form-group">
            <label>비밀번호</label>
            <input type="password" id="userPw" name="userPw" required>
        </div>
        <div class="form-group">
            <label>비밀번호 확인</label>
            <input type="password" id="userPwCheck" required>
            <p id="pwCheckMessage" style="color: red; font-size: 12px;"></p>
        </div>
        <div class="form-group">
            <label>이메일</label>
            <div style="display: flex; align-items: center;">
                <input type="text" id="emailId" style="width: 45%;">
                <span style="margin: 0 5px;">@</span>
                <input type="text" id="emailDomain" style="width: 45%;">
            </div>
            <select id="emailSelect" style="width: 100%; margin-top: 5px; padding: 8px;">
                <option value="">직접입력</option>
                <option value="naver.com">naver.com</option>
                <option value="gmail.com">gmail.com</option>
                <option value="daum.net">daum.net</option>
            </select>
            <!-- 완성된 이메일 주소를 서버로 보내기 위한 숨겨진 input -->
            <input type="hidden" name="email" id="email">
        </div>
        <div class="form-group">
            <label>이름</label>
            <input type="text" name="userName" required>
        </div>
        <div class="form-group">
            <label>전화번호</label>
            <input type="text" name="phone" placeholder="'-' 없이 숫자만 입력">
        </div>
        <div class="form-group">
            <label>우편번호</label>
            <input type="text" name="postNum" id="postNum" readonly>
            <button type="button" onclick="execDaumPostcode()" style="margin-top: 5px;">주소 검색</button>
        </div>
        <div class="form-group">
            <label>주소</label>
            <input type="text" name="address" id="address" readonly>
        </div>
        <div class="form-group">
            <label>상세주소</label>
            <input type="text" name="detAddr" id="detAddr">
        </div>
        <button type="submit" class="btn">가입하기</button>
    </form>
</div>

<!-- 카카오 주소 검색 API 스크립트 -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!-- jQuery 로드 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- 커스텀 스크립트 로드 -->
<script src="/js/join.js"></script>
</body>
</html>