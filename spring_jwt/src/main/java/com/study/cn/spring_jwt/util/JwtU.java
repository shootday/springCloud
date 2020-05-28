package com.study.cn.spring_jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: springCloudAll
 * @Date: 2020/5/26 23:04
 * @Author: lzx
 * @Description:
 *
 *
 * Token的刷新
 *    为了使客户端能够获取到新的Token，对上文的例子进行改造，大概思路如下：
 *
 * 用户登录成功的时候，一次性给他两个Token，分别为AccessToken和RefreshToken，AccessToken用于正常请求，
 * 也就是上例中原有的Token，RefreshToken作为刷新AccessToken的凭证。
 * AccessToken的有效期较短，例如一小时，短一点安全一些。RefreshToken有效期可以设置长一些，例如一天、一周等。
 * 当AccessToken即将过期的时候，例如提前5分钟，客户端利用RefreshToken请求指定的API获取新的AccessToken并更新本地存储中的AccessToken。
 */
public class JwtU {

    private String secret = "a1g2y47dg3dj59fjhhsd7cnewy73j";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    // 过期时间
    public static final long EXPIRE = 1000 * 60 * 60 * 24 * 7;  //过期时间，毫秒，一周


    /**
     * 初始化生成token的参数
     * @param userId
     * @return String
     */
    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>(1);
        claims.put("sub", userId);
        return generateToken(claims);
    }

    /**
     * 生成token
     * @param claims
     * @return String
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();

    }


    //判断token是否可以刷新
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
            final Date iat = claims.getIssuedAt();
            final Date exp = claims.getExpiration();
            if (iat.before(lastPasswordReset) || exp.before(new Date())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    ///刷新token
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
            refreshedToken = this.generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    //校验token
    public String verifyToken(String token) {
        String result = "";
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
            result = "true";
        } catch (Exception e) {
            result = "false";
        }
        return result;
    }

}
