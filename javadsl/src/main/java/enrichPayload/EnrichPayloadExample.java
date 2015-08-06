package enrichPayload;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration(classes=DSLConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@EnableIntegration
@IntegrationComponentScan
public class EnrichPayloadExample {

  /** The test gateway. */
  @Autowired
  private SayWelcomeService sayWelcomeService;

  /**
   * Test it.
   */
  @Test
  public void testIt() {
    System.out.println(sayWelcomeService.sayWelcome(new Hello("Foo")).getName());
  }

}
