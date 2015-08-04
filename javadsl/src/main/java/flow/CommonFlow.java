package flow;

import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageChannel;

public class CommonFlow implements IntegrationFlow {

  MessageChannel inputChannel;
  GenericTransformer<String, String> transformer;
  
  
  public GenericTransformer<String, String> getTransformer() {
    return transformer;
  }

  public <T extends GenericTransformer> void setTransformer(T transformer) {
    this.transformer = transformer;
  }

  public CommonFlow(MessageChannel inputChannel) {
    super();
    this.inputChannel = inputChannel;
  }

  public void accept(IntegrationFlowDefinition<?> t) {
    t.channel(inputChannel)
     .transform(transformer);
     
  }
}
