package conditionalLoading;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by manojperiathambi on 11/23/15.
 */
@Configuration
public class BConfig {

    @Bean
    public Person manoj(){
        Person p = new Person();
        p.setName("jeeva");
        return p;
    }
}
