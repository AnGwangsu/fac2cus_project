package com.fac2cus.springboot.user;

import com.fac2cus.springboot.config.auth.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MemberController {
    @Autowired
    private MemberRepository repository;
    @Autowired
    private BCryptPasswordEncoder pwEncode;

    @PostMapping("/userRegister")
    public String userRegister(Member user, MemberAddr addr){
        user.setAddress("("+addr.getPostcode()+")"+addr.getRoadAddress()+addr.getDetailAddress());
        String hashPw = pwEncode.encode(user.getPassword());
        user.setPassword(hashPw);
        user.setRole("ROLE_USER");
        repository.save(user);  //service없이 바로 repository에 접근해서 insert 가능
        System.out.println(user);
        return "redirect:/loginForm";
    }

    @GetMapping("/test/login")
    public @ResponseBody String loginTest(Authentication authentication
            ,@AuthenticationPrincipal PrincipalDetails userDetails){ // DI(의존성 주입)
        System.out.println("/test/login =======");
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        System.out.println("authentication:"+principalDetails.getUser());

        System.out.println("userDetails:"+userDetails.getUser());
        return "세션 정보 확인하기";
    }
    @GetMapping("/test/oauth/login")
    public @ResponseBody String loginOAuthTest(Authentication authentication
            ,@AuthenticationPrincipal OAuth2User oauth){ // DI(의존성 주입)
        System.out.println("/test/oauth/login =======");
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        System.out.println("authentication:"+oAuth2User.getAttributes());
        System.out.println("oauth2User:"+oauth.getAttributes());
        return "OAuth 세션 정보 확인하기";
    }
}
