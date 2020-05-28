#
公共的api的调用者，通过openFeign调用服务，以及ribbon的超时机制，hystrix的熔断降级
整合了Sleuth与zipkin 链路追踪  zipkin需要单独启动一个jar包
以及admin健康检查

整合config配置中心，从配置中心获取配置 通过actuator的端点http://laptop-meicvj3d:3000/actuator/refresh刷新配置
或者整合rabbitmq 通过spring-cloud-starter-bus-amqp  实现一个服务多个节点的刷新

