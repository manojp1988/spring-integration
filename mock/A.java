package com.dummy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by manojperiathambi on 11/22/15.
 */
@Configuration
@ComponentScan("com.dummy")
public class A {

    @Autowired
    B b;

    @Bean
    public SomeClass someBean(){

        SomeClass clas = new SomeClass();
        clas.setSomething(b.getSomething());
        System.out.println(b.getSomething());
        return clas;
    }
}


