package advice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.config.EnableMessageHistory;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// TODO: Auto-generated Javadoc
/**
 * The Class AdviceExample.
 */
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class AdviceExample {
  
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
    @CustomAnnotation
    String testIt(String payload);

  }

  /**
   * The Class ContextConfiguration.
   */
  @Configuration
  @EnableIntegration
  @IntegrationComponentScan
  @EnableMessageHistory
  @EnableAspectJAutoProxy
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
     * Gtwy advice.
     *
     * @return the gateway advice
     */
    @Bean
    public GatewayAdvice gtwyAdvice() {
      return new GatewayAdvice();
    }

  }

  /**
   * The Interface CustomAnnotation.
   */
  @Retention(value = RetentionPolicy.RUNTIME)
  @Target(value = ElementType.METHOD)
  @Inherited
  public @interface CustomAnnotation {

  }

  /**
   * The Class GatewayAdvice.
   */
  @Aspect
  public static class GatewayAdvice {

    /**
     * Before advice.
     */
    // working.
    @Before("execution(* advice.AdviceExample.TestGateway.testIt(*))")
    public void beforeAdvice() {
      System.out.println("Before advice called...");
    }


    /**
     * Before annotation advice.
     */
    // Not working.
    @Before("@annotation(advice.AdviceExample.CustomAnnotation)")
    public void beforeAnnotationAdvice() {
      System.out.println("Before annotation advice called...");
    }
  }

}
