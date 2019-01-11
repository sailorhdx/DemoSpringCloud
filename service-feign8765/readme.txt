Feign是一个声明式的伪Http客户端，它使得写Http客户端变得更简单。
使用Feign，只需要创建一个接口并注解。
它具有可插拔的注解特性，可使用Feign 注解和JAX-RS注解。
Feign支持可插拔的编码器和解码器。
Feign默认集成了Ribbon，并和Eureka结合，默认实现了负载均衡的效果。
简而言之：
Feign 采用的是基于接口的注解
Feign 整合了ribbon，具有负载均衡的能力
整合了Hystrix，具有熔断的能力

启动：eureka-server-standalone8761、service-hi8762、service-hi8763、service-feign8765

在浏览器上多次访问http://localhost:8765/hi?name=forezp,浏览器交替显示：
hi forezp,i am from port:8762
hi forezp,i am from port:8763
已经做了负载均衡，访问了不同的端口的服务实例。