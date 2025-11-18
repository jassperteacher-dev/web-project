package com.standard.web_project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.standard.web_project.service.MemberService;
import com.standard.web_project.service.UserDetailsServiceImpl;
import com.standard.web_project.vo.MemberVO;

import jakarta.servlet.http.HttpSession; // UserDetailsService 구현체

@Controller
public class MemberController {

    // 1. @Autowired를 사용하여 Service, UserDetailsService 주입
    @Autowired private MemberService memberService;
    @Autowired private UserDetailsServiceImpl userDetailsService;

    // --- 회원가입 관련 ---
    @GetMapping("/joinForm")
    public String showJoinForm() {
        return "joinForm"; 
    }

    @PostMapping("/joinAction")
    @ResponseBody
    public Map<String, Object> processJoin(MemberVO memberVO) {
        // --- 디버깅 코드 ---
        System.out.println("Controller가 받은 데이터: " + memberVO.toString());

        Map<String, Object> response = new HashMap<>();

         try {
            // 1. 회원가입 처리
            memberService.registerMember(memberVO);

            // 2. 회원가입 후 자동 로그인 처리 (두 번째 메소드의 로직을 가져옴)
            UserDetails userDetails = userDetailsService.loadUserByUsername(memberVO.getUserId());
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 3. 성공 응답 구성
            response.put("result", "success");
            response.put("message", memberVO.getUserName() + "님, 회원가입을 축하합니다! 자동으로 로그인되었습니다.");
            response.put("redirectUrl", "/myPage"); // JS가 이동할 경로
            
        } catch (Exception e) {
            // 4. 실패 응답 구성
            response.put("result", "fail");
            response.put("message", "회원가입 중 오류가 발생했습니다. (예: 아이디 중복)");
            e.printStackTrace();
        }
        
        // 5. 최종 응답(JSON) 리턴
        return response;
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

    // 아이디 중복 확인을 위한 로직
    @GetMapping("/checkId")
    @ResponseBody // 데이터를 직접 응답
    public Map<String, Boolean> checkIdDuplication(@RequestParam("userId") String userId) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("isDuplicated", memberService.isUserIdDuplicated(userId));
        return response;
    }
}