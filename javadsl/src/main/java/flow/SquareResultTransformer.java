package flow;

import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

@Component
public class SquareResultTransformer implements GenericTransformer<Integer, Integer> {
  public Integer transform(Integer source) {
    return source * source;
  }
}
