package flow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.config.EnableMessageHistory;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;
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
public class ReusableFlowExample {


  @Autowired
  private TestGateway testGateway;

  @Test
  public void testIt() {
    System.out.println(testGateway.helloTest("Manoj"));
    System.out.println(testGateway.welcomeTest("Manoj"));
    System.out.println(testGateway.squareOf(2));
  }

  @MessagingGateway
  public interface TestGateway {


    @Gateway(requestChannel = "helloChannel")
    String helloTest(String payload);


    @Gateway(requestChannel = "welcomeChannel")
    String welcomeTest(String payload);

    @Gateway(requestChannel = "squareResultChannel")
    Integer squareOf(Integer payload);
    
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
    public MessageChannel squareResultChannel() {
      return MessageChannels.direct()
                            .get();
    }

    @Bean
    public CommonFlow helloFlow() {
      CommonFlow testFlowImpl = new CommonFlow(helloChannel());
      testFlowImpl.setTransformer(new HelloTransformer());
      return testFlowImpl;
    }

    @Bean
    public CommonFlow welcomeFlow() {
      CommonFlow testFlowImpl = new CommonFlow(welcomeChannel());
      testFlowImpl.setTransformer(new WelcomeTransformer());
      return testFlowImpl;
    }

    @Bean
    public CommonFlow squareResultFlow() {
      CommonFlow testFlowImpl = new CommonFlow(squareResultChannel());
      testFlowImpl.setTransformer(new SquareResultTransformer());
      return testFlowImpl;
    }
    
  }

}
