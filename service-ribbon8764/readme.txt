在微服务架构中，业务都会被拆分成一个独立的服务，服务与服务的通讯是基于http restful的。
Spring cloud有两种服务调用方式，一种是ribbon+restTemplate，另一种是feign。

ribbon是一个负载均衡客户端，可以很好的控制htt和tcp的一些行为。
Feign默认集成了ribbon。
ribbon 已经默认实现了这些配置bean：
IClientConfig ribbonClientConfig: DefaultClientConfigImpl
IRule ribbonRule: ZoneAvoidanceRule
IPing ribbonPing: NoOpPing
ServerList ribbonServerList: ConfigurationBasedServerList
ServerListFilter ribbonServerListFilter: ZonePreferenceServerListFilter
ILoadBalancer ribbonLoadBalancer: ZoneAwareLoadBalancer

启动：eureka-server-standalone8761、service-hi8762、service-hi8763、service-ribbon8764

在浏览器上多次访问http://localhost:8764/hi?name=forezp，浏览器交替显示：
hi forezp,i am from port:8762
hi forezp,i am from port:8763
这说明当我们通过调用restTemplate.getForObject(“http://SERVICE-HI/hi?name=“+name,String.class)方法时，
已经做了负载均衡，访问了不同的端口的服务实例。

