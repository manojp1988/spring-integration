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

All channels are created based on the flow name we define. If you see from above example, spring generates sayHelloFlow.channel#0, sayHelloFlow.channel#1 etc. 

####Examples:
1.	[Flow reuse.](https://github.com/manojp1988/spring-integration/tree/master/javadsl/src/main/java/flow) This is something we wanted to achieve using dsl. With xml, we had to same kind of flow redundantly in many places. 
2.	[Advice for gateways.](https://github.com/manojp1988/spring-integration/tree/master/javadsl/src/main/java/advice) We can ran aop on the gateways. This is not specific to dsl though.
3.	[Global channel interceptors/](https://github.com/manojp1988/spring-integration/tree/master/javadsl/src/main/java/interceptors).
4.	[Re-route error channel to caller.](https://github.com/manojp1988/spring-integration/tree/master/javadsl/src/main/java/ErrorChannel).

####Questions:
1.	[How to put back errorchannel message into reply channel?](http://stackoverflow.com/questions/31791042/how-to-put-back-errorchannel-message-into-reply-channel)
2.	[How to carry over header info to errorChannel?](http://stackoverflow.com/questions/31778650/how-to-carry-over-header-info-to-errorchannel)
3.	[How to use AOP on spring integration gateways?](http://stackoverflow.com/questions/31707343/how-to-use-aop-on-spring-integration-gateways)
4.	[How to import spring Java dsl config into XML configuration?](http://stackoverflow.com/questions/31666328/how-to-import-spring-java-dsl-config-into-xml-configuration)
5.	[What is the difference between grouping config via @import and @ContextConfiguration.?](http://stackoverflow.com/questions/31631890/what-is-the-difference-between-grouping-config-via-import-and-contextconfigura)
6.	[How to create custom component and add it to flow in spring java dsl?](http://stackoverflow.com/questions/31626497/how-to-create-custom-component-and-add-it-to-flow-in-spring-java-dsl)
7.	[Do i need to do declare a channel info in dsl always?](http://stackoverflow.com/questions/31617449/do-i-need-to-do-declare-a-channel-info-in-dsl-always)
8.	[How to do split-aggregate in java dsl by invoking another flow?](http://stackoverflow.com/questions/31622120/how-to-do-split-aggregate-in-java-dsl-by-invoking-another-flow)
9.	[How to do channel interceptor based on pattern using JAVA DSL in Spring Integration?](http://stackoverflow.com/questions/31573744/how-to-do-channel-interceptor-based-on-pattern-using-java-dsl-in-spring-integrat)



 
