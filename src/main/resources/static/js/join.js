// 문서가 준비되면 실행
$(document).ready(function(){
    // 아이디 입력란에서 포커스가 벗어났을 때 이벤트 처리
    $('#userId').on('blur', function(){
        let userId = $(this).val();

        if (userId === "") {
            $('#idCheckMessage').text('');
            return;
        }

        // AJAX를 통해 서버에 아이디 중복 확인 요청
        $.ajax({
            url: '/checkId', // Controller의 주소
            type: 'GET',
            data: { userId: userId },
            success: function(response) {
                if (response.isDuplicated) {
                    $('#idCheckMessage').text('이미 사용 중인 아이디입니다.');
                    $('#idCheckMessage').css('color', 'red');
                } else {
                    $('#idCheckMessage').text('사용 가능한 아이디입니다.');
                    $('#idCheckMessage').css('color', 'green');
                }
            },
            error: function() {
                alert('아이디 중복 확인 중 오류가 발생했습니다.');
            }
        });
    });
    
    // 비밀번호와 비밀번호 확인 입력 필드에 대한 keyup 이벤트 핸들러
    $('#userPw, #userPwCheck').on('keyup', function () {
        let pw = $('#userPw').val();
        let pwCheck = $('#userPwCheck').val();
        let messageElem = $('#pwCheckMessage');

        if (pw === "" || pwCheck === "") {
            messageElem.text('');
        } else if (pw === pwCheck) {
            messageElem.text('비밀번호가 일치합니다.');
            messageElem.css('color', 'green');
        } else {
            messageElem.text('비밀번호가 일치하지 않습니다.');
            messageElem.css('color', 'red');
        }
    });

    // 이메일 도메인 선택 select 박스에 대한 change 이벤트 핸들러
    $('#emailSelect').on('change', function(){
        let selectedDomain = $(this).val();
        
        if(selectedDomain === "") { // '직접입력'을 선택한 경우
            $('#emailDomain').val(''); // 도메인 입력 칸을 비우고
            $('#emailDomain').prop('readonly', false); // 읽기 전용 속성 해제
            $('#emailDomain').focus(); // 커서를 도메인 입력 칸으로 이동
        } else { // 특정 도메인을 선택한 경우
            $('#emailDomain').val(selectedDomain); // 선택한 도메인으로 값을 채우고
            $('#emailDomain').prop('readonly', true); // 사용자가 수정할 수 없도록 읽기 전용으로 설정
        }
    });

    // 폼 제출 이벤트 핸들러
    $('#joinForm').on('submit', function(e){
        // 1. 폼의 기본 제출 동작(페이지 새로고침) 차단
        e.preventDefault(); 

        // 2. 비밀번호 일치 여부 최종 확인
        if ($('#userPw').val() !== $('#userPwCheck').val()) {
            alert('비밀번호가 일치하지 않습니다. 다시 확인해주세요.');
            return; // 함수 종료
        }
        
        // 3. 이메일 합치기 로직 추가 ---
        let emailId = $('#emailId').val();
        let emailDomain = $('#emailDomain').val();
        
        if (emailId && emailDomain) { // 아이디와 도메인이 모두 입력되었을 때만
            let fullEmail = emailId + '@' + emailDomain;
            $('#email').val(fullEmail); // 숨겨진 'email' input에 최종 이메일 주소 저장
        }
        
        // 4. 폼 데이터를 직렬화(serialize)하여 AJAX로 보낼 준비
        let formData = $(this).serialize();

        // 5. AJAX를 통해 서버에 회원가입 요청
        $.ajax({
            url: '/joinAction', // Controller의 @PostMapping 주소
            type: 'POST',
            data: formData,
            success: function(response) {
                // 5. 서버로부터 성공 응답을 받으면
                if(response.result === 'success') {
                    alert(response.message); // 성공 메시지 출력
                    location.href = response.redirectUrl; // 지정된 페이지로 이동
                } else {
                    alert(response.message); // 실패 메시지 출력
                }
            },
            error: function() {
                alert('회원가입 처리 중 오류가 발생했습니다.');
            }
        });
    });
});

// 카카오 주소 검색 API 함수
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            let addr = ''; // 주소 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 도로명 주소
                addr = data.roadAddress;
            } else { // 지번 주소
                addr = data.jibunAddress;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postNum').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detAddr").focus();
        }
    }).open();
}