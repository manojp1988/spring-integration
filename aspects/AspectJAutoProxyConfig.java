package aspects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by manojperiathambi on 11/26/15.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan("aspects")
public class AspectJAutoProxyConfig {


}