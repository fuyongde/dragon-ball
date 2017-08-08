package com.jason.frieza.handler;

import com.jason.frieza.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 全局异常处理
 * Created by fuyongde on 2016/12/24.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * 处理ServiceException
   */
  @ExceptionHandler(value = {ServiceException.class})
  public final ResponseEntity<?> handleException(ServiceException ex, NativeWebRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    ErrorResult errorResult = new ErrorResult();
    errorResult.setTimestamp(LocalDateTime.now());
    errorResult.setStatus(ex.getErrorCode());
    errorResult.setException(ServiceException.class.getName());
    errorResult.setMessage(ex.getMessage());
    errorResult.setPath(request.getContextPath());

    if (request.getNativeRequest() instanceof HttpServletRequest) {
      HttpServletRequest req = (HttpServletRequest) request.getNativeRequest();
      errorResult.setPath(req.getServletPath());
      logger.error("Access Error : {}, Error Message", req.getRequestURI(), ex.getMessage());
    }

    return handleExceptionInternal(ex, errorResult, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

}
