# 说明
开发环境使用idea基于maven多模块    
使用springcloud Hoxton.SR3    
以及springboot 2.2.xxx    
注意cloud的版本对应boot的版本有要求，具体查看官网  

#如果使用yml配置文件一定注意空格！！！！ 上级跟下级之间两个空格

##   模块说明  ----------

springcloud_common模块为通用模块  
springcloud_eureka模块为eurake注册中心  
springcloud_provider模块为eurake的生产者  
springcloud_consumer模块为eurake的消费者  消费者可通过rest--》ribbon负载调用生产者  


user_api  公告的api 用来管理接口 相当于接口文档
user_provider模块为eurake的生产者 公共api的实现者  整合链路追踪以及admin
user_consumer模块为eurake的消费者  公共api的调用者 通过feign调用以及使用hystix熔断 整合链路追踪以及admin

springcloud_zuul模块为网关 所有请求可通过网关调用，或者请求过滤

springcloud_admin模块为健康管理 可追踪到所有服务的状态

springcloud_config模块为配置中心服务 通过git实现各个客户端服务的配置文件热部署

spring_exception模块为统一异常处理  ErrorController  @ControllerAdvice

spring_security模块为权限管理  security的使用  

spring_database模块为基于数据库rbac的权限管理 

spring_jwt模块为jwt的使用 

oauth2_1模块为security整合oauth




