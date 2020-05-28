package com.study.cn.spring_jwt.util;

import com.study.cn.spring_jwt.util.entity.User;
import io.jsonwebtoken.Claims;

/**
 * @program: springCloudAll
 * @Date: 2020/5/26 22:03
 * @Author: lzx
 * @Description:
 */
public class Test {

    public static void main(String[] args) {

        User user = new User();
//        user.setId(999);
//        user.setHeadImg("I'm busy");
//        user.setName("Rookie");
//        String token = JwtUtil.geneJsonWebToken(user);
//        System.out.println(token);


        // 下面此 token 字符串是上面的结果生成的，每次不一样，不是写死的
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSb29raWVMaSIsImlkIjo5OTksIm5hbWUiOiJSb29raWUiLCJpbWciOiJJJ20gYnVzeSIsImlhdCI6MTU5MDUwMTg0OCwiZXhwIjoxNTkxMTA2NjQ4fQ.Da1gBYpgraeE40TsHss9fdpbQs_wrfnr8hQIZYRyvI0";
        Claims claims = JwtUtil.checkJWT(token);
        if(claims != null){
            String name = (String)claims.get("name");
            String img = (String)claims.get("img");
            int id =(Integer) claims.get("id");
            System.out.println(name);
            System.out.println(img);
            System.out.println(id);
        }else{
            System.out.println("非法token");
        }
    }
}
