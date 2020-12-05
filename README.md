# MClientFramework
## 日志功能
1. 在主启动类上加入注解 **@MClient** ,接口函数上加入注解 **@MLogFunction**
2. 自定义配置文件 **applicationContext.xml** 可见项目中的示例
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
  其中**myTag**可随意命名，具体配置参数的含义可见**aop-logger.xsd**。
  ##代理功能
  使用**MObjectProxy**创建代理对象，再调用`transform(String methodName,MResponse mResponse)`方法
  * 第一个参数是请求的目标函数名称；
  * 第二个参数是所需传递数据，要求key为目标函数定义时的参数名称，value为参数值。