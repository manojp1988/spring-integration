### Welcome to my Github Spring-integration-java-dsl repository!
   
   This repository contains samples, questions&answers posted to spring-integration authors. Thanks to @garyrussell and @artembilan for their support.
   
**Why dsl than xml?**

Java dsl is better in a way that we don't need to write complex xml integration tags, more writing to define each components. Java dsl is more reable, more resuable.

Reference: [Spring-Integration-Java-DSL-Reference] (https://github.com/spring-projects/spring-integration-java-dsl/wiki/Spring-Integration-Java-DSL-Reference).

####Sample Flow:
         
A sample outbound gateway can have  transformer which transform the canonical object to request object, header enricher which enriches header values, and call the outbound gateway and then transform the response object to canonical response object. This can be code in below dsl way. 

*Gateway:*
```java
  @MessagingGateway
  public interface HelloGateway {
    @Gateway(requestChannel = "helloRequestChannel")
    String sayHello(String payload);
  }
 ```
 *Channel definition:*
 ```java
 @Bean
 public MessageChannel helloRequestChannel(){
   return MessageChannels.direct().get();
 }
 ```
 *Integration flow:*
 ```java
  @Bean
  public IntegrationFlow sayHelloFlow(){
   return IntegrationFlow.from("helloRequestChannel")
                         .transform(requestTransformer)
                         .enrichHeaders(headerEnricher)
                         .handle(httpOutboundGateway)
                         .transform(responseTransformer)
                         .get();
   }
```
*How to invoke the gateway*
   1. Invoke through simple java invocation.
   2. Put message into the channel.

```java
  sayHello("dsl"); // Java method invocation.
```
```xml
<!-- below enricher puts payload into the channel we defined above and sets into property as usual. -->
  <int:enricher input-channel="HELLO_ENRCH_CHNL"
		request-channel="helloRequestChannel">
		<int:property name="name" expression="payload.name" />
	</int:enricher>
```
Working example represents above can be found [here](https://github.com/manojp1988/spring-integration/tree/master/javadsl/src/main/java/enrichPayload).

If you see above, you can find the difference how dsl improves readability. Instead of we defining channels, spring generates channels for us. And you can see them if you enable logging.

All channels are created based on the flow name we define. If you see from above example, spring generates sayHelloFlow.channel#0, sayHelloFlow.channel#1 etc. It is not that tough if you get yours hands on java dsl. 


*Advice*
```java
 @Aspect
  public static class GatewayAdvice {
    @Before("execution(* advice.AdviceExample.HelloGateway.sayHello(*))")
    public void beforeAdvice() {
      System.out.println("Before advice called...");
    }
```

 
