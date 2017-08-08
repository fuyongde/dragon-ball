package com.jason.frieza.context;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Token interceptor.
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

  private static final String TOKEN_HEADER = "Token";
  /**
   * 防止重复的token缓存，可以考虑添加超时的限制
   */
  private static final Cache<String, String> tokenCache = CacheBuilder.newBuilder().build();
  /**
   * The Logger.
   */
  private final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

  }
}
