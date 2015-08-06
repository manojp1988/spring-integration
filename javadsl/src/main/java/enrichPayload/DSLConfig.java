package enrichPayload;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
@IntegrationComponentScan
@ImportResource(value = "classpath:enrichPayload/enrichpayload.xml")
public class DSLConfig {


  @Bean
  public MessageChannel inputChannel() {
    return MessageChannels.direct()
                          .get();
  }

  @Bean
  public IntegrationFlow sayHelloFlow() {
    return IntegrationFlows.from(inputChannel())
                           .transform("new enrichPayload.Hello(payload.name.toUpperCase())")
                           .get();
  }
}
