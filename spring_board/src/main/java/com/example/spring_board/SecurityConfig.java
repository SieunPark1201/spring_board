package com.example.spring_board;

import com.example.spring_board.author.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity는 spring security를 customizing할 수 있는 기능을 활성화시켜준다
@EnableWebSecurity
public class SecurityConfig {

//    스프링에서 Bean(싱글톤)을 만드는 방법 두 가지
//       * 싱글톤: 그때그때 객체를 만드는 게 아니라 스프링이 기동되었을 때 객체를 하나만 만들고 돌려쓰는 것-메모리 절약을 위해
//    방법 1. @Component 방식
//      개발자가 직접 컨트롤이 가능한 내부 클래스에서 사용

//    방법 2. @Configuration + @Bean 방식
//      개발자가 직접 컨트롤할 수 없는 외부 라이브러리 적용 시 사용


    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()   //보안공격에 대한 설정은 별도로 하지 않겠다는 뜻
                .authorizeRequests()
                .antMatchers("/author/login","/", "/authors/new")//login authenticated에서 제외할 대상 url 지정
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/author/login")
                    .loginProcessingUrl("/doLogin") //스프링에서 지정해둔 doLogin 그대로 활용(doLogout도 마찬가지)
                    .usernameParameter("email")    //스프링에서 사용했던 변수 그대로 사용하여 명시해줘야 함
                    .passwordParameter("password")
//                로그인 성공시 이후 로직 LoginSuccessHandler에서 구현
                    .successHandler(new LoginSuccessHandler())
                .and()
                .logout()
                    .logoutUrl("/doLogout")
                .and().build();
    }
}
