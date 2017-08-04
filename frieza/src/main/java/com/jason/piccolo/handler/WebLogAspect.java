package com.jason.piccolo.handler;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志拦截
 * Created by fuyongde on 2017/4/12.
 */
@Aspect
@Component
public class WebLogAspect {

  private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

  private final ThreadLocal<Long> startTime = new ThreadLocal<>();

  @Pointcut(value = "execution(public * com.jason.piccolo.rest..*.*(..))")
  public void webLog() {
  }

  @Before("webLog()")
  public void doBefore(JoinPoint joinPoint) throws Throwable {
    startTime.set(System.currentTimeMillis());

    // 接收到请求，记录请求内容
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    // 记录下请求内容
    logger.info("=============================start====================================");
    logger.info("URL : {}", request.getRequestURL().toString());
    logger.info("HTTP_METHOD : {}", request.getMethod());
    logger.info("IP : {}", request.getRemoteAddr());
    logger.info("CLASS_METHOD : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    logger.info("ARGS : {}", Arrays.toString(joinPoint.getArgs()));

  }

  @AfterReturning(returning = "result", pointcut = "webLog()")
  public void doAfterReturning(Object result) throws Throwable {
    // 处理完请求，返回内容
    logger.info("RESPONSE : {}", result);
    logger.info("SPEND TIME : {}ms", (System.currentTimeMillis() - startTime.get()));
    logger.info("=============================end======================================");
  }
}
