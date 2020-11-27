package com.fac2cus.springboot.config.auth;

import com.fac2cus.springboot.user.Member;
import com.fac2cus.springboot.user.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정에서 loginProcessingUrl("/login")
// /login 요청이 오면 자동으로 UserDetailsService타입으로 IoC되어 있는 loadUserByUsername함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;

    //시큐리티 session Authentication(내부 UserDetails)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("MemberUseranme:"+username);
        Member user = memberRepository.findByUsername(username); //카멜표기법때문에 _는 허락되지 않는다.
        if(user !=null){
            return new PrincipalDetails(user); //UserDetails 리턴
        }
        return null;
    }
}
