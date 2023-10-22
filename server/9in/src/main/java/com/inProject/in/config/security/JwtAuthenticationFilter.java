package com.inProject.in.config.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class JwtAuthenticationFilter extends OncePerRequestFilter {  //jwt토큰으로 인증하고 SecurityContextHolder에 추가하는 필터를 설정
    private final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);

        String ipAddresses = request.getHeader("x-forwarded-for");
        String ipAddress = new String();

        if(!Objects.isNull(ipAddresses)){
            ipAddress = Arrays.stream(ipAddresses.split(","))  // 최초 IP
                    .findFirst()                                    //로드밸런서, 프록시를 거치면 그 ip들이 열거됨. 가장 최초의 클라이언트 ip는 이렇게 조회.
                    .orElse("");
        }
        else{
            ipAddress = request.getRemoteAddr();
        }

        log.info("ip : " + ipAddress + " " + ipAddresses);
        log.info("JwtAuthenticationFilter doFilterInternal ==> token 값 추출 : " + token);  //request에서 토큰 추출.

//        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
//        log.info("JwtAuthenticationFilter doFilterInternal ==> refresh token 값 추출 : " + refreshToken);

        if(token != null && jwtTokenProvider.validateToken(token)){
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);   //Authentication객체를 생성해 SecurityContextHolder에 추가
            log.info("JwtAuthenticationFilter doFilterInternal ==> token 값 유효성 체크 성공");
        }

        filterChain.doFilter(request, response);  //서블릿을 실행하는 메서드. 이 메서드를 기준으로 앞서 작성된 코드는 서블릿이 실행되기 전에 실행됨.

    }
}
