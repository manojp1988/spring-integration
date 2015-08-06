package enrichPayload;

import org.springframework.stereotype.Component;

@Component
public class Hello {

  private String name;

  public Hello(){}
  
  public Hello(String name) {
    super();
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  
}
