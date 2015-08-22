package Classpathscan;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

class MyAnnotComponentProvider extends ClassPathScanningCandidateComponentProvider {
  
  public MyAnnotComponentProvider() {
      super(false);

      addIncludeFilter(new AnnotationTypeFilter(Myannotation.class, false));
  }

  @Override
  protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
      return beanDefinition.getMetadata().isInterface();
  }
}