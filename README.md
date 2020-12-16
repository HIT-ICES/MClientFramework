# MClientFramework

## Description
The framework is used to build adaptive microservices and has the following functions
* Log Function  
  Using Spring Aop, the interface can provide runtime information with log when the interface is called
* Proxy Function  
  Provide a proxy method so that developers can call the interface methods in the proxy object in a unified way

## How to Use

### Add the dependency to your maven pom.xml

### Log Function
1. Add annotations **@MClient** on application class
   ```java
   @MClient
   @SpringBootApplication
   public class ApplicationMain {
        public static void main(String[] args){
            SpringApplication.run(ApplicationMain.class, args);
         }
   }
   ```
2. Add annotations **@MLogFunction** ton required methods
   ```java
    @Slf4j
    @RestController
    public class MController extends MObject {
        @MLogFunction
        @GetMapping("/test")
        public String test(@RequestHeader HttpHeaders httpHeaders){
            return "hello world";
        }
    }
    ```
3. Configure **applicationContext.xml**  in your application 
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:myTag="http://www.wangteng.com/mclient/core/mLogFuncation"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
       http://www.wangteng.com/mclient/core/mLogFuncation
       http://www.wangteng.com/mclient/core/mLogFuncation.xsd">
    <myTag:aop-log id="test" logPath="D:/MClientFramework/MClient/info.log" logExtra="Accept,Connection" logBug="true"></myTag:aop-log>
    <myTag:aop-log id="MController" logPath="D:/MClientFramework/MClient/info.log" logExtra="Connection" logBug="true"></myTag:aop-log>
</beans>
```
The meaning of specific configuration parameters can be seen in **aop-logger.xsd**

### Proxy Function
1. Use **MObjectProxy** to create a proxy object,and the object that need to be proxied need to inherit from the abstract class `MObject`
2. Use`transform(String methodName,MResponse mResponse)`
* The first parameter `methodName` is the name of the requested target function;
* The second parameter `mResponse` is  data, and the key is the parameter name when the target function is defined, and the value is the parameter value.