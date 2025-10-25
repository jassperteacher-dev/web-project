package com.standard.web_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.standard.web_project.service.MemberService;
import com.standard.web_project.vo.MemberVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

    // 1. @Autowired를 사용하여 Service를 정확하게 주입받습니다.
    @Autowired
    private MemberService memberService;

    // --- 회원가입 관련 ---
    @GetMapping("/joinForm")
    public String showJoinForm() {
        return "joinForm"; 
    }

    @PostMapping("/joinAction")
    public String processJoin(MemberVO memberVO) {
        memberService.registerMember(memberVO);
        return "redirect:/loginForm"; // 가입 성공 후 로그인 폼으로 리다이렉트
    }

    // --- 로그인 & 로그아웃 관련 ---
    @GetMapping("/loginForm")
    public String showLoginForm() {
        return "loginForm";
    }
    
    @GetMapping("/logout")
    public String processLogout(HttpSession session) {
        session.invalidate(); // 세션을 무효화하여 로그아웃 처리
        return "redirect:/loginForm";
    }

    // --- 마이페이지 관련 (단 하나의 메소드만 남깁니다) ---
    @GetMapping("/myPage")
    public String showMyPage(HttpSession session, Model model) {
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        
        if (loginMember == null) {
            // 비로그인 상태라면 로그인 폼으로 보냅니다.
            return "redirect:/loginForm";
        }
        
        // 로그인 상태라면, 모델에 회원 정보를 담아 myPage.jsp로 보냅니다.
        model.addAttribute("member", loginMember);
        return "myPage";
    }

    @PostMapping("/updateAction")
    public String processUpdate(MemberVO memberVO, HttpSession session) {
        memberService.updateMember(memberVO);
        // 세션 정보도 최신으로 갱신해 줍니다.
        MemberVO updatedMember = memberService.getMemberById(memberVO.getUserId());
        session.setAttribute("loginMember", updatedMember);
        return "redirect:/myPage";
    }
}