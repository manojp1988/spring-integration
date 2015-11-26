package conditionalLoading;

import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by manojperiathambi on 11/23/15.
 */

@Configuration
@Conditional(ConditionalConfigB.Condition.class)
@Import(value = {BConfig.class})
public class ConditionalConfigB {


    static class Condition implements ConfigurationCondition {

        public ConfigurationPhase getConfigurationPhase() {
            return ConfigurationPhase.PARSE_CONFIGURATION;
        }

        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return false;
        }
    }
}

