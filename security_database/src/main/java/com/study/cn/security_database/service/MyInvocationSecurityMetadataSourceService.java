package com.study.cn.security_database.service;

import com.study.cn.security_database.dao.SysPermissionDao;
import com.study.cn.security_database.entity.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: springCloudAll
 * @Date: 2020/5/21 16:22
 * @Author: lzx
 * @Description: 自定义的资源（url）权限（角色）数据获取类
 * <p>
 * <p>
 * 1、你可以初始化话容器的时候就加载资源和权限列表，这个在实现FilterInvocationSecurityMetadataSource接口的类里，
 * 定一个全局的静态map，以url为key，所需权限（集合）为value，装进这个静态map。
 * <p>
 * 2、登陆。登陆的action在spring配置文件里配好。这个action会直接到继承UsernamePasswordAuthenticationFilter类的
 * 一个重写的attemptAuthentication方法里，把你输入username和password（加入配了MD5加密security3会自动帮你加密）
 * 和数据比对，如果存在这个username和password，进入第3步（这是后没有退出这个attemptAuthentication方法）。
 * 没有这个username和password的话直接抛异常和终止此次登陆。
 * <p>
 * 3、如果username和password存在，会进入到实现UserDetailsService接口的类里的一个重写的loadUserByUsername方法，
 * 这个方法里会把该username所有拥有的权限设为安全权限然后返回一个UserDetails类型的结果。这是返回到第二步中的attemptAuthentication中，
 * 把这这个认证了得安全实体有所拥有的权限以Authentication类型返回。共第五步调用。
 * <p>
 * <p>
 * <p>
 * 4、登陆认证成功后会有一个action（这个action在spring配置文件里配置），
 * 这个action是第一个url权限认证（假设这个action需要权限认证），
 * 这个请求会进入到实现了FilterInvocationSecurityMetadataSource接口的类里的一个重写的getAttributes方法，
 * 将第1步中的资源权限列表map中get出来，返回Collection<ConfigAttribute>类型的结果，共第5部调用。
 * <p>
 * <p>
 * 5，第4步执行完之后会进入实现类AccessDecisionManager接口的类的一个重写decide方法，
 * 在这个方法中会将第3步中返回的Authentication和第4步中返回的Collection<ConfigAttribute>进行比对。比对成功则可以访问，比对不成功会跳转一个页面（这个页面也是在spring配置文件中配置）。
 * <p>
 * <p>
 * 至此，已经完成了登陆的验证并成功执行一个action。
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements
        FilterInvocationSecurityMetadataSource {

    @Autowired
    private SysPermissionDao permissionDao;

    private HashMap<String, Collection<ConfigAttribute>> map = null;

    /**
     * 加载权限表中所有权限
     */

    public void loadResourceDefine() {
        System.out
                .println(">>>>>>>>>>loadResourceDefine()---successfully<<<<<<<<<<");
        if (map == null) {
            map = new HashMap<>();
        }
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<SysPermission> permissions = permissionDao.findAll();
        for (SysPermission permission : permissions) {
            array = new ArrayList<>();
            cfg = new SecurityConfig(permission.getName());
            //此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
            array.add(cfg);
            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
            map.put(permission.getUrl(), array);
        }

    }


    private RequestMatcher requestMatcher;
    private String matcher = "ant";


    /**
     * 获取用户请求的某个具体的资源（url）所需要的权限（角色）集合
     *
     * @param object 包含了用户请求的request信息
     */
    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。

    //因为我不想每一次来了请求，都先要匹配一下权限表中的信息是不是包含此url，
    // 我准备直接拦截，不管请求的url 是什么都直接拦截，然后在MyAccessDecisionManager的decide 方法中做拦截还是放行的决策。
    //所以此方法的返回值不能返回 null 此处我就随便返回一下。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        Collection<ConfigAttribute> co = new ArrayList<>();
        co.add(new SecurityConfig("null"));
        return co;


//
//        if (map == null) {
//            loadResourceDefine();
//        }
//
//        //object 中包含用户请求的request 信息
//        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
//        String requestUrl = ((FilterInvocation) object).getRequestUrl();
//        System.out.println("requestUrl is " + requestUrl);
//        AntPathRequestMatcher matcher;
//        String resUrl;
//        /**
//         * 遍历每个资源（url），如果与用户请求的资源（url）匹配，则返回该资源（url）所需要的权限（角色）集合，
//         * 如果全都不匹配，则表示用户请求的资源（url)不需要权限（角色）即可访问
//         */
//        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
//            resUrl = iter.next();
//            matcher = new AntPathRequestMatcher(resUrl);
//            if (matcher.matches(request)) {
//                return map.get(resUrl);
//            }
//        }
//        return null;


//        HttpServletRequest request = ((FilterInvocation) object).getRequest();
//        String requestUrl = ((FilterInvocation) object).getRequestUrl();
//        System.out.println("requestUrl is " + requestUrl);
//        if (map == null) {
//            loadResourceDefine();
//        }
//        Set<String> urlMatch = map.keySet();
//        for (String url : urlMatch) {
//            if (matcher.toLowerCase().equals("ant")) {
//                requestMatcher = new AntPathRequestMatcher(url);
//            }
//            if (matcher.toLowerCase().equals("regex")) {
//                requestMatcher = new RegexRequestMatcher(url,
//                        request.getMethod(), true);
//            }
//            if (requestMatcher.matches(request)) {
//                return map.get(url);
//            }
//        }
//        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
