java -jar eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1
java -jar eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2


服务注册中心Eureka Server，是一个实例，当成千上万个服务向它注册的时候，
它的负载是非常高的，这在生产环境上是不太合适的，可以将Eureka Server集群化。

Eureka通过运行多个实例，使其更具有高可用性。
事实上，这是它默认的属性，你需要做的就是给对等的实例一个合法的关联serviceurl。