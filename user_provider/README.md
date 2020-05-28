#
公共的api的实现者，服务的提供方
整合了Sleuth与zipkin 链路追踪 zipkin需要单独启动一个jar包
以及admin健康检查


#### 降级
用户发起请求到客户端，客户端调用服务端不通或者超时，在通过重试机制向其他服务器发起请求，还是不通，
直接返回错误提示或者页面，再或者重试按钮或者联系方式，或者写入mq等等。。。

#### 限流
这样一个场景，provider是一个核心服务，给N个consumer提供服务，突然某个consumer抽风，流量飙升，
占用了provider大部分机器时间，导致其他可能更重要的consumer不能被正常服务。

所以，provider端，需要根据consumer的重要程度，以及平时的QPS大小，来给每个consumer设置一个流量上线，
同一时间内只会给A consumer提供N个线程支持，超过限制则等待或者直接拒绝。

资源隔离
provider可以对consumer来的流量进行限流，防止provider被拖垮。

同样，consumer 也需要对调用provider的线程资源进行隔离。 这样可以确保调用某个provider逻辑不会耗光整个consumer的线程池资源。


#### 熔断

如果检查出来频繁超时，当达到一个阈值的时候，就把consumer调用provider的请求，直接短路掉，不实际调用，而是直接返回一个mock的值。
当在某一个时间点在重新去试试，成功的话阈值计数清空。

