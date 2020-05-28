package com.study.cn.spring_jwt.util;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: springCloudAll
 * @Date: 2020/5/26 22:15
 * @Author: lzx
 * @Description:
 */
public class TokenUtil {

    private final static Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    private final static String myApiKeySecret = "dadadadad";  //密钥


    /**
     * iss(Issuser)：代表这个JWT的签发主体；
     * sub(Subject)：代表这个JWT的主体，即它的所有人；
     * aud(Audience)：代表这个JWT的接收对象；
     * exp(Expiration time)：是一个时间戳，代表这个JWT的过期时间；
     * nbf(Not Before)：是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的；
     * iat(Issued at)：是一个时间戳，代表这个JWT的签发时间；
     * jti(JWT ID)：是JWT的唯一标识。
     */

    /**
     * 创建JSON WEB TOKEN
     *
     * @param id
     * @param userName
     * @param userPower
     * @param ttlMillis  过期时间  毫秒  到时间自动过期
     * @return
     */
    public static String createJWT(String id, String userName, String userPower, long ttlMillis) {

        //设置签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //设置密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(myApiKeySecret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //设置JWT claims
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setId(id)
                .setIssuedAt(now)
                .setAudience("iot")
                .setIssuer("Jerry")  //设置发行者，自定义
                .claim("userName", userName)
                .claim("userPower", userPower)
                .signWith(signatureAlgorithm, signingKey);

        //设置超时时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //生成JWT
        return builder.compact();

    }

    /**
     * 解析JWT，并验证用户权限
     *
     * @param jwt
     */
    public static Boolean parseJWT(String jwt) throws ParseException {

        if (jwt == null) {
            logger.error("----------Token不能为空------------");
            return false;
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(myApiKeySecret))
                    .parseClaimsJws(jwt).getBody();

            //将超时时间格式化为时间戳time
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String timeFormat = sdf.format(claims.getExpiration());
            System.out.println(timeFormat + "-------token过期时间");
            Date date = sdf.parse(timeFormat);
            long time = date.getTime();
            long currentTime = System.currentTimeMillis();


            return ("Jerry").equals(claims.getIssuer()) &&
                    ("iot").equals(claims.getAudience()) &&
                    (time > currentTime) &&
                    claims.get("userName") != null;

        } catch (ExpiredJwtException ex) {
            logger.error("----------Token过期------------");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("----------Token格式有误------------");
        }
        return false;
    }

    /**
     * 获取jwt中的userName
     *
     * @param jwt
     * @return
     */
    public static String getUserName(String jwt) {

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(myApiKeySecret))
                .parseClaimsJws(jwt).getBody();

        return claims.get("userName").toString();
    }


    public static void main(String[] args) throws Exception {
        String jwt = createJWT("userid", "username", "userp", 600000);
        System.out.println(jwt);

        Boolean parseJWT = parseJWT(jwt);
        System.out.println(parseJWT);

        System.out.println("---------------------");

//        Boolean parseJWT2 = parseJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ1c2VyaWQiLCJpYXQiOjE1OTA1MDQ3NjAsImF1ZCI6ImlvdCIsImlzcyI6IkplcnJ5IiwidXNlck5hbWUiOiJ1c2VybmFtZSIsInVzZXJQb3dlciI6InVzZXJwIiwiZXhwIjoxNTkwNTA0ODAwfQ.X9sFnMmBjfFRKaMaPmiodYVWnW_ynwzo2t-fRCRwLXU");
//        System.out.println(parseJWT2);
//        if(TokenUtil.parseJWT(jwt)){
//            //此处为验证通过执行的代码
//        }else{
//            //此处为验证不通过执行的代码
//        }
    }

}
