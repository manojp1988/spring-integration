### Welcome to my Github Spring-integration-java-dsl repository!
   
   This repository contains samples, questions&answers posted to spring-integration authors. Thanks to @garyrussell and @artembilan for their support.
   
**Why dsl than xml?**

Java dsl is better in a way that we don't need to write complex xml integration tags, more writing to define each components. Java dsl is more reable, more resuable.

Reference: [Spring-Integration-Java-DSL-Reference] (https://github.com/spring-projects/spring-integration-java-dsl/wiki/Spring-Integration-Java-DSL-Reference).

####Sample Flow:
         
A sample outbound gateway can have  transformer which transform the canonical object to request object, header enricher which enriches header values, and call the outbound gateway and then transform the response object to canonical response object. This can be code in below dsl way. 

```java
  @MessagingGateway
  public interface HelloGateway {
    @Gateway(requestChannel = "helloRequestChannel")
    String sayHello(String payload);
  }
```


 
