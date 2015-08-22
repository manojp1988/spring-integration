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

// TODO: Auto-generated Javadoc
/**
 * The Class InterceptorSample.
 */
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class InterceptorSample {

  
  /** The test gateway. */
  @Autowired
  private TestGateway testGateway;

  /**
   * Test it.
   */  
  @Test
  public void testIt() {
    System.out.println(this.testGateway.testIt("foo"));
  }


  /**
   * The Interface TestGateway.
   */
  @MessagingGateway
  public interface TestGateway {

    /**
     * Test it.
     *
     * @param payload the payload
     * @return the string
     */
    @Gateway(requestChannel = "testChannel")
    String testIt(String payload);

  }

  /**
   * The Class ContextConfiguration.
   */
  @Configuration
  @EnableIntegration
  @IntegrationComponentScan
  @EnableMessageHistory
  public static class ContextConfiguration {
    
    /** The logger. */
    LoggingHandler logger = new LoggingHandler(LoggingHandler.Level.INFO.name());

    /**
     * Test flow.
     *
     * @return the integration flow
     */
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

    /**
     * Poller.
     *
     * @return the poller metadata
     */
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {                                       // 12
      return Pollers.fixedDelay(1000).get();
    }

    /**
     * Intercepted.
     *
     * @return the pollable channel
     */
    @Bean
    public PollableChannel intercepted() {
      return new QueueChannel();
    }

    /**
     * Wire tap.
     *
     * @return the wire tap
     */
    @Bean
    @GlobalChannelInterceptor(patterns = "*Ch*")
    public WireTap wireTap() {
      return new WireTap(intercepted());
    }

    /**
     * Intercepted flow.
     *
     * @return the integration flow
     */
    @Bean
    public IntegrationFlow interceptedFlow() {
      return IntegrationFlows.from(intercepted())
                      .transform("'Intercepted payload is '.concat(payload)")
                             .handle(logger)
                             .get();

    }

  }

}
