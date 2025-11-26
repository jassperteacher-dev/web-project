package com.standard.web_project.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.standard.web_project.vo.MemberVO;

@Mapper
public interface MemberMapper {

    @Insert("INSERT INTO member (user_id, user_pw, user_name, email, phone, post_num, address, det_addr) " +
            "VALUES (#{userId}, #{userPw}, #{userName}, #{email}, #{phone}, #{postNum}, #{address}, #{detAddr})")
    void insertMember(MemberVO memberVO);

    // 아이디로 회원 정보 조회 (로그인, 정보 수정 시 사용)
    @Select("SELECT * FROM member WHERE user_id = #{userId}")
    MemberVO getMemberById(String userId);

    // 회원 정보 수정 (비밀번호는 선택적으로 수정)
    @Update({
        "<script>",
        "UPDATE member SET user_name = #{userName}",
        "<if test='userPw != null and userPw != \"\"'>",
        ", user_pw = #{userPw}",
        "</if>",
        "WHERE user_id = #{userId}",
        "</script>"
    })
    void updateMember(MemberVO memberVO);

    // 아이디 중복 확인을 위한 로직
    @Select("SELECT COUNT(*) FROM member WHERE user_id = #{userId}")
    int countByUserId(String userId);
}