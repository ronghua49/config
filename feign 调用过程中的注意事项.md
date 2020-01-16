## feign 调用过程中的注意事项

##### eureka 服务端的pom 依赖

```java
<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
		</dependency>
```

#####    服务端和消费端等客户端的依赖 和断路器配合使用时

```Java
<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-openfeign</artifactId>
		</dependency>
```

##### 客户端yml配置

```java
eureka:
  client:
    serviceUrl:
      defaultZone: http://@localhost:7000/eureka/
feign:
  hystrix:
    enabled: true
  client:
    config:
      default:
        connectTimeout: 50000
        readTimeout: 50000
        loggerLevel: full
ribbon:
  eureka:
    enabled: true #关闭负载均衡
  ReadTimeout: 120000 #处理超时时间 默认5秒
  ConnectTimeout: 120000 #连接超时时间 默认2秒
hystrix:
  metrics:
    enabled: true
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 120000 #断路切换超时

```

##### 注意事项

1. feigin 调用方服务启动类注解

   ```java
   @SpringBootApplication(scanBasePackages = "com.sly.seata")
   @EnableDiscoveryClient
   @EnableFeignClients(basePackages = "com.sly.seata")
   public class BusinessApplication {
   	public static void main(String[] args) {
   		SpringApplication.run(BusinessApplication.class, args);
   	}
   
   }
   ```

2. feigin 接口注解和失败回调（注意value值 代表去调用的微服务名称，需要保证该微服务中有定义接口方法，回调方法需要加@Component 注解）

   ```java
   @FeignClient(value = "seata-springcloud-account",fallback = BusinesshystrixImpl.class)
   public interface BusinessFeignService {
   
       @RequestMapping(method = RequestMethod.POST, value = "/account/insert")
       String purchase(@RequestParam("accountId") String accountId,
                                    @RequestParam("orderId") String orderId, @RequestParam("storageId") String storageId);
   
       @GetMapping("/test")
       String getTest(@RequestParam String name);
   }
   
   ```

   