package aspects;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by manojperiathambi on 11/26/15.
 */
public class MainApp {

    public static void main(String... ga){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AspectJAutoProxyConfig.class);


        Person userService= ctx.getBean(Person.class);

        System.out.println( userService.sayHello("Manoj"));
    }
}
