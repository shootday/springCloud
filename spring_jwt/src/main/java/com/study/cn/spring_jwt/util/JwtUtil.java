package com.study.cn.spring_jwt.util;

import com.study.cn.spring_jwt.util.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @program: springCloudAll
 * @Date: 2020/5/26 22:00
 * @Author: lzx
 * @Description:
 */
public class JwtUtil {

    // 主题
    public static final String SUBJECT = "RookieLi";

    // 秘钥
    public static final String SECRETKEY = "Rookie666";

    // 过期时间
    public static final long EXPIRE = 1000 * 60 * 60 * 24 * 7;  //过期时间，毫秒，一周

    // 生成 JWT
    public static String geneJsonWebToken(User user) {

        if (user == null ||
                    user.getId() == null ||
                user.getName() == null ||
                user.getHeadImg() == null) {

            return null;
        }

        String token = Jwts.builder()
                .setSubject(SUBJECT)
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("img", user.getHeadImg())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRETKEY).compact();

        return token;
    }


    // 校验 JWT
    public static Claims checkJWT(String token) {

        try {
            final Claims claims = Jwts.parser().setSigningKey(SECRETKEY).
                    parseClaimsJws(token).getBody();
            return claims;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public static boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

}
