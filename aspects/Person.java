package aspects;

import org.springframework.stereotype.Component;

/**
 * Created by manojperiathambi on 11/26/15.
 */
@Component
public class Person {

@Auditable(
        action = "sdfs", method = "dfsd"
)
    public String sayHello(String name){
        return name+"Hello !";
    }

}
