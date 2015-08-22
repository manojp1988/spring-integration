package Classpathscan;

import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;

public class SampleScan {
  public static void main(String[] args) {
    MyAnnotComponentProvider provider = new MyAnnotComponentProvider();
    Set<BeanDefinition> beans = provider.findCandidateComponents("Classpathscan");
    for (BeanDefinition bd : beans) {
        System.out.println(bd.getBeanClassName());
    }
  }
}
