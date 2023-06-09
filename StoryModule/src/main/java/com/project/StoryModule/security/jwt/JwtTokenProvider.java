package com.project.StoryModule.security.jwt;

import com.project.StoryModule.dto.UserResponseDto;
import com.project.StoryModule.repository.UserRepository;
import com.project.StoryModule.security.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtTokenProvider {

    private final String AUTHORITIES_KEY = "auth";
    private final String BEARER_TYPE = "Bearer";
    private final String SECRET_KEY;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;              // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;    // 7일

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    public JwtTokenProvider(@Value("${app.auth.token.secret-key}")String secretKey) {
        this.SECRET_KEY = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Key getSignKey(String SECRET_KEY){
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // token 생성
    public UserResponseDto.TokenInfo generateToken(Authentication authentication){
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(getSignKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(getSignKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();

        return UserResponseDto.TokenInfo.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    public String getEmail(String token){
        return parseClaims(token).getSubject();
    }

    // Access Token을 복호화하여 토큰에 들어있는 정보 꺼내는 메소드
    public Authentication getAuthentication(String accessToken) {
        // Token 복호화
        Claims claims = parseClaims(accessToken);

        if(claims.get(AUTHORITIES_KEY) == null){
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // claim에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = 
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체 만들어서 Authentication 리턴
        UserDetails principal = new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 유효성, 만료시간 검사
    public Boolean validateToken(String token) {
        log.info("============================================= Validate Token =============================================");
        try {
            Jwts.parserBuilder().setSigningKey(getSignKey(SECRET_KEY)).build().parseClaimsJws(token);
            log.info("============================================= Success ==============================================");
            return true;
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            log.info("유효하지 않은 JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT Token 입니다.", e);
        } catch (IllegalStateException e) {
            log.info("JWT claims String is empty", e);
        }
        log.info("============================================= FAIL =============================================");
        return false;
    }

    // Access Token 만료시 갱신때 사용할 정보를 얻기 위해 Claim 리턴
    private Claims parseClaims(String accessToken) {
        try {
            log.debug("pasreClaims", "Claim 리턴");
            return Jwts.parserBuilder().setSigningKey(getSignKey(SECRET_KEY)).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) { // 만료된 경우
            log.debug("만료된 토큰입니다.", e.getClaims());
            return e.getClaims();
        }
    }
}
