package com.sky.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Map;


/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-03-28  9:04
 */
@Component
public class WtUtils {
    @Value("${wt.secret}")
    private String secret;
    @Value("${wt.expire}")
    private Long expire ;

    /**
     * 生成密钥
     * @return
     */
    private Key getKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    /**
     * 生成token
     * @param claims 载荷信息
     * @return
     */
    public String generateToken(Map<String,Object> claims){
        long now = System.currentTimeMillis();
        return io.jsonwebtoken.Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new java.util.Date(now))
                .setExpiration(new java.util.Date(now+expire))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

   /**
     * 解析token
     * @param token
     * @return
     */
    public Claims parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    /**
     * 验证token是否和合法
     * @param token
     * @return
     */
    public boolean validateToken(String token){
        try{
            parseToken(token);
            return true;
        }catch (ExpiredJwtException e){
            throw new RuntimeException("token过期");
        }catch (UnsupportedJwtException | MalformedJwtException | SecurityException | IllegalArgumentException e){
            throw new RuntimeException("token无效");
        }
    }

    /**
     * 从token中获取用户id
     * @param token
     * @return
     */
    public Long getUserId(String token){
        return parseToken(token).get("userId",Long.class);
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public String getUserName(String token){
        return parseToken(token).get("username",String.class);
    }
}
