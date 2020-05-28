#
springcloud config配置中心，统一通过化配置
从配置中心获取配置 通过actuator的端点http://laptop-meicvj3d:3000/actuator/refresh刷新配置
或者整合rabbitmq 通过spring-cloud-starter-bus-amqp  实现一个服务多个节点的刷新，在config-server中可以刷新所有客户端

