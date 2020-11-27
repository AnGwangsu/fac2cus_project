package com.fac2cus.springboot.admin;

import com.fac2cus.springboot.config.auth.PrincipalDetails;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Secured("ROLE_ADMIN")
    @GetMapping("/home")
    public String adminHome(Model model, @AuthenticationPrincipal PrincipalDetails userDetails){
        model.addAttribute("info",userDetails.getUser());
        return "admin/adminHome";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/userInfo")
    public @ResponseBody String info(Model model){

        return "admin/user/userInfo";
    }
}
