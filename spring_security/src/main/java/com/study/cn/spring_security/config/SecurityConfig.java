package com.study.cn.spring_security.config;

import com.study.cn.spring_security.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * @program: springCloudAll
 * @Date: 2020/5/12 20:43
 * @Author: lzx
 * @Description:
 */
@Configuration
@EnableWebSecurity
//开启Security注解支持
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    private UserServiceImpl userService;


    @Override
    public void configure(WebSecurity web) throws Exception {
        //静态资源无需认证就可以访问
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/img/**");
    }

    /**
     * loginPage(String loginPage) : 登录 页面而并不是接口，对于前后分离模式需要我们进行改造 默认为 /login。
     * loginProcessingUrl(String loginProcessingUrl) 实际表单向后台提交用户信息的 Action，再由过滤器UsernamePasswordAuthenticationFilter 拦截处理，该 Action 其实不会处理任何逻辑。
     * usernameParameter(String usernameParameter) 用来自定义用户参数名，默认 username 。
     * passwordParameter(String passwordParameter) 用来自定义用户密码名，默认 password
     * failureUrl(String authenticationFailureUrl) 登录失败后会重定向到此路径， 一般前后分离不会使用它。
     * failureForwardUrl(String forwardUrl) 登录失败会转发到此， 一般前后分离用到它。 可定义一个 Controller （控制器）来处理返回值,但是要注意 RequestMethod。
     * defaultSuccessUrl(String defaultSuccessUrl, boolean alwaysUse) 默认登陆成功后跳转到此 ，如果 alwaysUse 为 true 只要进行认证流程而且成功，会一直跳转到此。一般推荐默认值 false
     * successForwardUrl(String forwardUrl) 效果等同于上面 defaultSuccessUrl 的 alwaysUse 为 true 但是要注意 RequestMethod。
     * successHandler(AuthenticationSuccessHandler successHandler) 自定义认证成功处理器，可替代上面所有的 success 方式
     * failureHandler(AuthenticationFailureHandler authenticationFailureHandler) 自定义失败成功处理器，可替代上面所有的 failure 方式
     * permitAll(boolean permitAll) form 表单登录是否放开  给没登录的用户可以访问这个地址的权限
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);

        http
                //验证码filter
                .addFilterBefore(new CodeFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                //  .antMatchers("/img/**").permitAll()//这个无需登录
                .antMatchers("/kaptcha").permitAll()
                //此ip无需登录访问前面的地址  ip白名单
//                .antMatchers("/hi1").hasIpAddress("127.0.0.1")
                //此地址需要角色  会跟注解冲突
//                .antMatchers("/admin/**").hasRole("admin")
//                .antMatchers("/user/**").hasRole("user")
//                //或者的关系
//                .antMatchers("/au/**").hasAnyRole("admin", "user")
                //所有请求都需要验证  不能写在其他规则前面
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                //  .usernameParameter("username")
                //  .passwordParameter("password")
                //该配置使用post请求
                //.failureForwardUrl("/error2")
                // .failureUrl("/error.html")
                .permitAll()
                //配置true每次登录都跳首页，不配置之的话访问哪个登陆后就跳哪个
                // .defaultSuccessUrl("/", true)
                //该配置使用post请求
                .successForwardUrl("/success")
                //登陆成功处理器   加了处理器那么 defaultSuccessUrl  successForwardUrl配置则无效
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        //可用来初始化资源
                        System.out.println("登录成功");
                    }
                })
                //登录失败处理器  加了处理器那么 failureForwardUrl  failureUrl配置则无效
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        System.out.println("登录失败");
                        e.printStackTrace();
                        //httpServletRequest.getRequestDispatcher("/error").forward(httpServletRequest,httpServletResponse);
                        //  httpServletResponse.sendRedirect("");
                    }
                })
                .and()
                .logout().addLogoutHandler(new LogoutHandler() {
            @Override
            public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
                //可用来清理各种资源
                System.out.println("退出成功");
            }
        })
                .and()
                //自定义权限拒绝处理类  没定义会默认跳到 error.html
                //跳到contoller
                .exceptionHandling().accessDeniedPage("/403")
                //自定处理类
//                .accessDeniedHandler(new AccessDeniedHandler() {
//                    @Override
//                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
//                        System.out.println("没有权限");
//                    }
//                })

//                .and()
//                //记住我
//                .rememberMe()
//                .and()
//                .sessionManagement()
//                //最大登录用户
//                .maximumSessions(1)
//                //禁止其他终端登录
//                .maxSessionsPreventsLogin(true)
//                .and()
                .and()
                .csrf()
                .disable()
        // 默认开启csrf  那么需要在的登录的表单内提交下发的token  <input  th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
        //.csrfTokenRepository(new HttpSessionCsrfTokenRepository())
        ;
    }

    //及时清理过期session
//    @Bean
//    HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }

    /**
     * Security的基于内存的认证.
     * <p>
     * 首先是Security重写configureGlobal(AuthenticationManagerBuilder auth)方法,(基于内存配置用户的角色的时候是不需要ROLE_前缀的)
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("admin").password("123").roles("admin")
//                .and()
//                .withUser("user2").password("123").roles("user")
//                .and();

        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
                .withUser("admin").password(passwordEncoder().encode("123")).roles("admin")
                .and()
                .withUser("user").password(passwordEncoder().encode("123")).roles("user")
                .and()
                .withUser("guest").password(passwordEncoder().encode("123")).roles("guest")
                .and()
                .withUser("all").password(passwordEncoder().encode("123")).roles("admin", "user");


//        String password = passwordEncoder().encode("123456");
//        System.out.println("{password:}" + password);


        //数据库用户密码
        //     JdbcUserDetailsManager manager = auth.jdbcAuthentication().dataSource(dataSource).getUserDetailsService();
//        User user = new User("lzx", new BCryptPasswordEncoder().encode("123"), true, true, true, true, Collections.singletonList(new SimpleGrantedAuthority("role")));
//        manager.createUser(User.withUsername("admin")
//                .password(new BCryptPasswordEncoder().encode("123"))
//                .roles("admin", "role")
//                .build());
//
//        manager.createUser(User.withUsername("qqq")
//                .password("123")
//                .roles("pinming", "role")
//                .build());
//
//        if (manager.userExists("lzx")) {
//            System.out.println("已注册");
//        } else {
//            manager.createUser(user);
//        }


        //   自己实现 获取用户
        // auth.userDetailsService(userService);


        //校验器
        //不要直接使用 new UserAuthenticationProvider()传入 会使 @Autowired注入无法成功，转而只能通过构造器注入
        //  auth.authenticationProvider(userAuthenticationProvider());


    }


//    @Override //重写的话需要自己重新实现
//    protected UserDetailsService userDetailsService() {
//        return userService;
//    }


    /**
     * 注册自定义校验器
     *
     * @return
     */
    @Bean
    UserAuthenticationProvider userAuthenticationProvider() {
        return new UserAuthenticationProvider();
    }

    //    @Bean  //bean注入能够直接用于登录
//    //@Override  //重写的话需要自己重新实现
//    protected UserDetailsService userDetailsService() {
//        //内存的用户密码
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        User user = new User("lzx", new BCryptPasswordEncoder().encode("123"), true, true, true, true, Collections.singletonList(new SimpleGrantedAuthority("role")));
//        manager.createUser(user);
//        manager.createUser(User.withUsername("1112")
//                .password(new BCryptPasswordEncoder().encode("123"))
//                .roles("admin")
//                .build());
//
//
////        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
////        User user = new User("lzx", new BCryptPasswordEncoder().encode("123"), true, true, true, true, Collections.singletonList(new SimpleGrantedAuthority("role")));
////        manager.createUser(User.withUsername("admin")
////                .password(new BCryptPasswordEncoder().encode("123"))
////                .roles("admin", "role")
////                .build());
////        manager.createUser(User.withUsername("qqq")
////                .password("123")
////                .roles("pinming", "role")
////                .build());
////        if (manager.userExists("lzx")) {
////            System.out.println("已注册22");
////        } else {
////            manager.createUser(user);
////        }
//
//        return manager;
//    }


    //权限继承

    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl impl = new RoleHierarchyImpl();
        impl.setHierarchy("ROLE_admin > ROLE_user");
        return impl;
    }

    /**
     * 密码加密方式
     *
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }


}
