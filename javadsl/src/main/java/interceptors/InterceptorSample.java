package interceptors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.channel.interceptor.WireTap;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.config.EnableMessageHistory;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class InterceptorSample {

  @Autowired
  private TestGateway testGateway;

  @Test
  public void testIt() {
    System.out.println(this.testGateway.testIt("foo"));
  }


  @MessagingGateway
  public interface TestGateway {

    @Gateway(requestChannel = "testChannel")
    String testIt(String payload);

  }

  @Configuration
  @EnableIntegration
  @IntegrationComponentScan
  @EnableMessageHistory
  public static class ContextConfiguration {
    LoggingHandler logger = new LoggingHandler(LoggingHandler.Level.INFO.name());

    @Bean
    public IntegrationFlow testFlow() {
      return IntegrationFlows.from("testChannel")
                             .transform("payload.toUpperCase()")
                             .channel("testChannel")
                             .transform("payload.concat(' Manoj')")
                             .channel("testChannel")
                             .handle(logger)
                             .get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {                                       // 12
      return Pollers.fixedDelay(1000).get();
    }

    @Bean
    public PollableChannel intercepted() {
      return new QueueChannel();
    }

    @Bean
    @GlobalChannelInterceptor(patterns = "*Ch*")
    public WireTap wireTap() {
      return new WireTap(intercepted());
    }

    @Bean
    public IntegrationFlow interceptedFlow() {
      return IntegrationFlows.from(intercepted())
                      .transform("'Intercepted payload is '.concat(payload)")
                             .handle(logger)
                             .get();

    }

  }

}
