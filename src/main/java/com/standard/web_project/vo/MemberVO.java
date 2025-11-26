package com.standard.web_project.vo;
import lombok.Data;
@Data
public class MemberVO {
    private String userId;
    private String userPw;
    private String email;
    private String userName;
    private String phone;
    private String postNum;
    private String address;
    private String detAddr; // 상세주소
}