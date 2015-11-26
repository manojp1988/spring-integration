package com.dummy;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by manojperiathambi on 11/22/15.
 */
@Component("b")
@Profile("!Test")
public class B {
    String something;

    public String getSomething() {
        return something;
    }

    public void setSomething(String something) {
        this.something = something;
    }
}
