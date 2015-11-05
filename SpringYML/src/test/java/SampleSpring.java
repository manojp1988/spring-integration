import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Properties;

/**
 * Created by manojperiathambi on 11/4/15.
 */
@ContextConfiguration
@DirtiesContext
public class SampleSpring extends AbstractTestNGSpringContextTests {

    @Autowired
    Properties yamlProperties;

    @Test
    public void test() {
        System.out.println(":" + yamlProperties.get("connection.username"));
    }

    @Configuration
    public static class Config {

        @Bean
        public Properties yamlProperties() {
            YamlPropertiesFactoryBean propertiesFactoryBean = new YamlPropertiesFactoryBean();
            propertiesFactoryBean.setResources(new ClassPathResource("application.yml"));
            return propertiesFactoryBean.getObject();
        }

    }
}
