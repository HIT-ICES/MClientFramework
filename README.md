# MClientFramework

## Description
The framework is used to build adaptive microservices and has the following functions  .
### Log Function
  Using Spring Aop, the interface can provide runtime information with log when the interface is called.The information is as follows:  
  * Basic Information:request and response ip and port,request method name.
  * Exception information:the exception message thrown by the method.
  * request header information:Request header parameters.
### Proxy Function  
  Provide a proxy method so that developers can call the interface methods in the proxy object in a unified way.you can use this to composite or split your interface  .
### Service Information
   Provide information and dependencies of the internal controller of the service  .
## How to Use

### Log Function
1. Add annotations **@MClient** on application class.
   ```java
   @MClient
   @SpringBootApplication
   public class ApplicationMain {
        public static void main(String[] args){
            SpringApplication.run(ApplicationMain.class, args);
         }
   }
   ```
2. Add annotations **@MLogFunction** to the required method.
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
3. Configure **applicationContext.xml**  in your application .
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
    `mytag` can be changed  
The meaning of specific configuration parameters can be seen in **aop-logger.xsd**.
    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <xsd:schema xmlns="http://www.wangteng.com/mclient/core/mLogFuncation"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                targetNamespace="http://www.wangteng.com/mclient/core/mLogFuncation"
                elementFormDefault="qualified">
        <xsd:element name="aop-log">
            <xsd:complexType>
                <!--  Controller name or interface name.You can choose to configure all interfaces in the Controller or configure one interface         -->
                <xsd:attribute name="id" type="xsd:string" use="required"></xsd:attribute>
                <!--   Log file path         -->
                <xsd:attribute name="logPath" type="xsd:string" use="required"></xsd:attribute>
                <!--   Basic Information         -->
                <xsd:attribute name="logInfo" type="xsd:boolean" default="true"></xsd:attribute>
                <!--   Exception information         -->
                <xsd:attribute name="logBug" type="xsd:boolean" default="false"></xsd:attribute>
                <!--   request header information         -->
                <xsd:attribute name="logExtra" type="xsd:string"></xsd:attribute>
            </xsd:complexType>
        </xsd:element>
    </xsd:schema>
    ```

### Proxy Function
1. Use **MObjectProxy** to create a proxy object,and the object that need to be proxied need to inherit from the abstract class `MObject`.
    ```
    MController mController = new MController();
    MObjectProxy mControllerProxy = new MObjectProxy();
    mController = (MController)mControllerProxy.getInstance(mController);
    mController.transform("test",new MResponse());
    ```
   Another way is to use **@MFunctionType**, which is like **@Autowired**, which can automatically create proxy objects during initialization.
   ```java
   @RestController
   public class DemoController1 extends MObject {

      @MFunctionType
      public DemoController2 demoController2;

      @MFunctionType
      public DemoController3 demoController3;
      
      @GetMapping("/test")
      public String test1(){
         System.out.println(demoController2.transform("test2",new MResponse()));
         System.out.println(demoController3.transform("test3",new MResponse()));
         return "hello demo1";
      }
   }
   ```
2. Use`transform(String methodName,MResponse mResponse)`.
* The first parameter `methodName` is the name of the requested target function;
* The second parameter `mResponse` is  data, and the key is the parameter name when the target function is defined, and the value is the parameter value.
### Service Information
1. Give each controller an ID during initialization, which indicates how many controllers are in the service, the dependencies between controllers, and the Api interface within each controller.
2. We provide some additional interfaces.
   * **/mclient/ismclient**:Whether the service is combined with a programming framework.
   * **/mclient/getMObjectIdList**:List of Controllers in the service.
   * **/mclient/info**:Service Information.