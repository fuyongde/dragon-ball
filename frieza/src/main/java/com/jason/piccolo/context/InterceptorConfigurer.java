package com.jason.piccolo.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfigurer extends WebMvcConfigurerAdapter {

  private TokenInterceptor tokenInterceptor;

  @Autowired
  public void setTokenInterceptor(TokenInterceptor tokenInterceptor) {
    this.tokenInterceptor = tokenInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    String[] pathPatterns = {
        "/**"
    };

    String[] excludePathPatterns = {
        "/index"
    };

    registry.addInterceptor(tokenInterceptor)
        .addPathPatterns(pathPatterns)
        .excludePathPatterns(excludePathPatterns);
    super.addInterceptors(registry);
  }
}
