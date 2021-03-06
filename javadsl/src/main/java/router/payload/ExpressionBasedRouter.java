package router.payload;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.config.EnableMessageHistory;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.RouterSpec;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.support.Consumer;
import org.springframework.integration.router.ExpressionEvaluatingRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class ExpressionBasedRouter {

  @Autowired
  RouterGateway route;

  @Test
  public void splitTest() {
    System.out.println(route.route("WELCOME")
                            .toString());
  }

  @MessagingGateway
  public interface RouterGateway {
    @Gateway(requestChannel = "testChannel")
    String route(String payload);
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
                             .route("payload", new Consumer<RouterSpec<ExpressionEvaluatingRouter>>() {
                               public void accept(RouterSpec<ExpressionEvaluatingRouter> route) {
                                 route.channelMapping("HELLO", "helloChannel");
                                 route.channelMapping("WELCOME", "welcomeChannel");
                                 route.resolutionRequired(false);
                               }
                             })
                             .channel("defaultOutputChannel")
                             .get();
    }

    @Bean
    public IntegrationFlow helloFlow() {
      return IntegrationFlows.from("helloChannel")
                             .transform(" new java.lang.String('Hello ').concat(payload)")
                             .get();
    }

    @Bean
    public IntegrationFlow welcomeFlow() {
      return IntegrationFlows.from("welcomeChannel")
                             .transform("new java.lang.String('Welcome ').concat(payload)")
                             .get();
    }
    
    @Bean
    public IntegrationFlow defaultOutput() {
      return IntegrationFlows.from("defaultOutputChannel")
                             .transform("payload")
                             .get();
    }
  }
}
