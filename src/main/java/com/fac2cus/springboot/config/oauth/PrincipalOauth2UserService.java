package com.fac2cus.springboot.config.oauth;

import com.fac2cus.springboot.config.auth.PrincipalDetails;
import com.fac2cus.springboot.config.oauth.provider.GoogleUserInfo;
import com.fac2cus.springboot.config.oauth.provider.NaverUserInfo;
import com.fac2cus.springboot.config.oauth.provider.OAuth2UserInfo;
import com.fac2cus.springboot.user.Member;
import com.fac2cus.springboot.user.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private BCryptPasswordEncoder pwEncoder;
    @Autowired
    private MemberRepository repository;
    //구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration:"+userRequest.getClientRegistration());//registrationId로 어떤OAuth로 로그인 했는지 확인가능.
        System.out.println("getAccessToken:"+userRequest.getAccessToken());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        //구글 로그인 버튼 클릭 -> 구글로그인 창 -> 로그인을 완료 ->code를 리턴(OAuth-Client라이브러리) ->AccessToken 요청
        //userRequest정보 -> 회원프로필 받아야함(loadUser함수) -> 구글로부터 회원프로필 받아준다.
        System.out.println("getAttributes:"+oAuth2User.getAttributes()); //로그인한 정보를 출력


        //강제 회원가입
        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes()); //다형성을통해 소셜타입 구현
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
        }else{
            System.out.println("우리는 구글과 네이버만 지원해요 ㅎㅎㅎ");
        }


        String provider = oAuth2UserInfo.getProvider();//google ,naver
        String providerId = oAuth2UserInfo.getProviderId(); //naver는 null이 들어감
        String username = provider+"_"+providerId;//google_clientId
        String password = pwEncoder.encode("getinthere");
        String email = oAuth2UserInfo.getName();
        String name = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        Member user = repository.findByUsername(username);
        if(user == null){
            System.out.println("구글로그인이 최초입니다.");
            user= Member.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .name(name)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            repository.save(user);
        }else{
            System.out.println("구글 로그인을 이미 한 적이 있습니다. 당신은 자동회원가입이 되어 있습니다.");
        }
        return new PrincipalDetails(user,oAuth2User.getAttributes()); //OAtuh2User 리턴
    }
}
