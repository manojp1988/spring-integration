package conditionalLoading;

import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by manojperiathambi on 11/23/15.
 */

@Configuration
@Conditional(ConditionalConfigA.Condition.class)
@Import(value = {AConfig.class})
public class ConditionalConfigA {


    static class Condition implements ConfigurationCondition {

        public ConfigurationPhase getConfigurationPhase() {
            return  ConfigurationPhase.PARSE_CONFIGURATION;
        }

        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return true;
        }
    }
}

