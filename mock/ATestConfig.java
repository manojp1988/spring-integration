package com.dummy;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by manojperiathambi on 11/22/15.
 */

@Configuration
public class ATestConfig {

    
    @Bean
    public B b(){
        B mock = Mockito.mock(B.class);
        Mockito.when(mock.getSomething()).thenReturn("ABC");
        System.out.println(mock.getSomething());
        return mock;
    }
}
