package com.example.demo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import sun.security.provider.MD5;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Set;

/**
 * @AUTHOR：wanderlustLee
 * @DATE:Create in 9:51 2018-04-19
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    UserDetailsService customUserService() { // 注册UserDetailsService 的bean
        return new CustomUserDetailsService();
    }

    public class LoginSuccessHandle implements AuthenticationSuccessHandler {
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response, Authentication authentication) throws IOException,ServletException {

            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            String path = request.getContextPath() ;
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
            if (roles.contains("ROLE_ADMIN")){
                response.sendRedirect(basePath+"adminHome");
                return;
            }
            response.sendRedirect(basePath+"home");
        }
}
    @Override
    //重写configure(HttpSecurity http)的方法，这里面来自定义自己的拦截方法和业务逻辑
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/js/**", "/css/**", "/images/*", "/fonts/**", "/**/*.png", "/**/*.jpg").permitAll()
                .antMatchers("/", "/login", "/signin","/seeShare","/userregister").permitAll()
                .antMatchers("/adminHome").access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/home").successHandler(new LoginSuccessHandle())
                .permitAll()
                .and()
                .rememberMe().rememberMeParameter("remember-me") //其实默认就是remember-me，这里可以指定更换
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .csrf().disable();

    }
    private static Md5PasswordEncoder md5encoder = new Md5PasswordEncoder();

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(new PasswordEncoder(){

            @Override
            public String encode(CharSequence rawPassword) {
                return md5encoder.encodePassword(rawPassword.toString(), "hongxf");
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return md5encoder.isPasswordValid(encodedPassword, rawPassword.toString(), "hongxf");
            }}); //user Details Service验证
    }
}
//class MD5Util {
//
//    private static final String SALT = "tamboo";
//
//    public static String encode(String password) {
//        password = password + SALT;
//        MessageDigest md5 = null;
//        try {
//            md5 = MessageDigest.getInstance("MD5");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        char[] charArray = password.toCharArray();
//        byte[] byteArray = new byte[charArray.length];
//
//        for (int i = 0; i < charArray.length; i++)
//            byteArray[i] = (byte) charArray[i];
//        byte[] md5Bytes = md5.digest(byteArray);
//        StringBuffer hexValue = new StringBuffer();
//        for (int i = 0; i < md5Bytes.length; i++) {
//            int val = ((int) md5Bytes[i]) & 0xff;
//            if (val < 16) {
//                hexValue.append("0");
//            }
//
//            hexValue.append(Integer.toHexString(val));
//        }
//        return hexValue.toString();
//    }
//}
