package aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by manojperiathambi on 11/26/15.
 */

@Aspect
@Component
public class AnnotationAspect {

    @Before("@annotation(a) && args(name, ..)")
    public void advice( Auditable a, String name) {
        System.out.println(name+"Before advice" + a.action());
    }
}
