package flow;

import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

@Component
public class WelcomeTransformer implements GenericTransformer<String, String> {
  public String transform(String source) {
    return "Welcome ".concat(source);
  }
}
