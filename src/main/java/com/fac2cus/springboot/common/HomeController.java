package com.fac2cus.springboot.common;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "common/home";
    }
    @GetMapping("/admin")
    public String admin(){
        return "admin/adminHome";
    }
    @GetMapping("/erp")
    public String erp(){
        return "erp/erpHome";
    }
    @GetMapping("/loginForm")
    public String loginForm(){
        return "common/loginForm/loginForm";
    }
    @GetMapping("/userRegister")
    public String userRegisterForm(){
        return"common/registerForm/userRegisterForm";
    }







    @GetMapping("/adminLoginForm")
    public String adminLoginForm(){
        return "common/loginForm/adminLoginForm";
    }
    @GetMapping("/registerType")
    public String registerType(){
        return "common/registerForm/registerType";
    }

    @GetMapping("/erpRegister")
    public String erpRegisterForm(){
        return "common/registerForm/erpRegisterForm";
    }


}
