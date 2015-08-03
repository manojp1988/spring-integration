package ErrorChannel;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.config.EnableMessageHistory;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.support.GenericHandler;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class ErrorChannelExample {

  /** The test gateway. */
  @Autowired
  private TestGateway testGateway;
  
  /**
   * Test it.
   */
  @Test
  public void testIt() {
    System.out.println(this.testGateway.testIt("foo", "12344"));
  }

  @MessagingGateway(errorChannel = "sampleErrorChannel")
  public interface TestGateway {
    @Gateway(requestChannel = "testChannel")
    String testIt(String payload, @Header("identifier") String identifier);

  }


  @Configuration
  @EnableIntegration
  @IntegrationComponentScan
  @EnableMessageHistory
  public static class ContextConfiguration {



    @Bean
    public IntegrationFlow testFlow() {
      return IntegrationFlows.from("testChannel")
                             .handle(new GenericHandler() {

                               public Object handle(Object payload, Map headers) {
                                // System.out.println(headers);
                                 return payload;
                               }
                             })
                             .transform(new AbstractPayloadTransformer<String, String>() {

                               @Override
                               protected String transformPayload(String payload) throws Exception {
                                 if (true)
                                   throw new Exception("Sample error message");
                                 return payload;
                               }
                             })
                             .get();
    }

    @Bean
    public MessageChannel sampleErrorChannel() {
      return MessageChannels.direct()
                            .get();
    }

    @Bean
    public IntegrationFlow errorFlow() {
      return IntegrationFlows.from("sampleErrorChannel")
                             .handle(new GenericHandler<MessagingException>() {

                               public Object handle(MessagingException payload, Map headers) {
                                 System.out.println(payload.getCause());
                                 System.out.println(payload.getFailedMessage().getHeaders());
                                 return payload;
                               }
                             })
                             .channel("nullChannel")
                             .get();
    }
  }
}
