package com.standard.web_project.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.standard.web_project.mapper.MemberMapper;
import com.standard.web_project.vo.MemberVO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 디버깅 아이디 확인
        System.out.println("=== 로그인 시도: " + username + " ===");

        // 1. username(userId) DB에서 회원 정보 조회
        MemberVO member = memberMapper.getMemberById(username);

        // 2. 만약 회원이 존재하지 않으면, 예외 발생
        if (member == null) {
            // 디버깅
            System.out.println("=== 로그인 실패: DB에 해당 아이디가 없음 ===");
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // 디버깅 아이디 비번 조회
        System.out.println("=== DB 조회 성공: " + member.toString() + " ===");
        System.out.println("=== DB 비밀번호(암호화됨): " + member.getUserPw() + " ===");

        // 3. 조회된 회원 정보를 Spring Security가 이해할 수 있는 UserDetails 객체로 변환하여 리턴
        return new User(member.getUserId(), member.getUserPw(), Collections.emptyList());
    }
}