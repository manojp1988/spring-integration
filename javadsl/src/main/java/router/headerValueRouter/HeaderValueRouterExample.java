package router.headerValueRouter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.Payload;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.config.EnableMessageHistory;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class HeaderValueRouterExample {

  @Autowired
  RouterGateway route;

  @Test
  public void splitTest() {
    route.route("WELCOME", "sdsd");
  }

  @MessagingGateway
  public interface RouterGateway {
    @Gateway(requestChannel = "testChannel")
    String route(@Payload String payload, @Header("enabled") String isEnabled);
  }

  @Configuration
  @EnableIntegration
  @IntegrationComponentScan
  @EnableMessageHistory
  public static class ContextConfiguration {

    @Bean
    public MessageChannel helloChannel() {
      return MessageChannels.direct()
                            .get();
    }

    @Bean
    public MessageChannel welcomeChannel() {
      return MessageChannels.direct()
                            .get();
    }

    @Bean
    public MessageChannel defaultOutputChannel() {
      return MessageChannels.direct()
                            .get();
    }

    @Bean
    public IntegrationFlow routerFlow() {
      return IntegrationFlows.from("testChannel")
                             .route(headerRouter())
                             .get();
    }

    @Bean
    public HeaderValueRouter headerRouter() {
      HeaderValueRouter router = new HeaderValueRouter("enabled");
      router.setResolutionRequired(false);
      router.setChannelMapping("true", "helloChannel");
      router.setChannelMapping("false", "welcomeChannel");
      router.setDefaultOutputChannel(defaultOutputChannel());
      return router;
    }

    LoggingHandler loggingHandler = new LoggingHandler("INFO");

    @Bean
    public IntegrationFlow helloFlow() {
      return IntegrationFlows.from("helloChannel")
                             .transform(" new java.lang.String('Hello ').concat(payload)")
                             .handle(loggingHandler)
                             .get();
    }

    @Bean
    public IntegrationFlow welcomeFlow() {
      return IntegrationFlows.from("welcomeChannel")
                             .transform("new java.lang.String('Welcome ').concat(payload)")
                             .handle(loggingHandler)
                             .get();
    }

    @Bean
    public IntegrationFlow defaultOutput() {
      return IntegrationFlows.from("defaultOutputChannel")
                             .transform("payload")
                             .handle(loggingHandler)
                             .get();
    }
  }
}
