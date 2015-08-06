package splitter_aggregator;

import java.util.List;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class Splitter_AggregatorExample {

  @Autowired SplitGateway split;
  
  @Test
  public void splitTest(){
    System.out.println(split.split(new String[]{"a", "b"}).toString());
  }

  @MessagingGateway
  public interface SplitGateway {
    @Gateway(requestChannel = "testChannel")
    List<String> split(String[] payload);
  }

  @Configuration
  @EnableIntegration
  @IntegrationComponentScan
  @EnableMessageHistory
  public static class ContextConfiguration {

    @Bean
    public IntegrationFlow splitFlow() {
      return IntegrationFlows.from("testChannel")
                             .split()
                             .transform("payload.toUpperCase()")
                             .aggregate()
                             .get();
    }
    
    
  }
}
