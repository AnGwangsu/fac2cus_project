package com.fac2cus.springboot.config;


import com.fac2cus.springboot.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured 어노테이션 활성화, preAuthorize 어노테이션 활성화(여러개를 걸고싶으면)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PrincipalOauth2UserService principaOauth2UserService;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(WebSecurity webSecurity) throws Exception{
        webSecurity.ignoring().antMatchers("/css/**","/fonts/**","/js/**","/img/**");
    }

    @Override
    protected void configure(HttpSecurity http)throws Exception{
       http.csrf().disable();
       http.authorizeRequests()
               .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
               .antMatchers("/erp/**").access("hasRole('ROLE_ERP') or hasRole('ROLE_ADMIN')")
               .anyRequest().permitAll()
               .and()
               .formLogin()
               .loginPage("/loginForm")
               .loginProcessingUrl("/login")// /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
               .defaultSuccessUrl("/")
               .and()
               .oauth2Login()
               .loginPage("/loginForm") //구글로그인 완료된 뒤의 후처리가 필요 ->Tip. 코드x(엑세스토큰 + 사용자프로필정보 o)
               .userInfoEndpoint()                      //1.코드받기 2.엑세스토큰(권한)
               .userService(principaOauth2UserService);//3.사용자프로필 정보를 가져와고 4.그 정보를 토대로 회원가입을 자동으로 진행시키기도 함 (권장x)
                                                        //5.(이메일,전화번호,이름,아이디)기본적인정보 외에 쇼핑몰 -> (주소)  백화점 ->(vip등급,일반등급)  ->추가적인 정보를 입력받고 회원가입 시킴



    }
}
