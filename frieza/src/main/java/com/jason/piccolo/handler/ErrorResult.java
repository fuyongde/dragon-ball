package com.jason.piccolo.handler;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 错误返回结果
 * Created by fuyongde on 2016/12/24.
 */
public class ErrorResult<T> implements Serializable {

  /**
   * timestamp : 1491271552469
   * status : 500
   * error : Internal Server Error
   * exception : java.lang.NullPointerException
   * message : No message available
   * path : /black/api/test/testNull
   */

  private LocalDateTime timestamp;
  private int status;
  private String exception;
  private T message;
  private String path;

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getException() {
    return exception;
  }

  public void setException(String exception) {
    this.exception = exception;
  }

  public T getMessage() {
    return message;
  }

  public void setMessage(T message) {
    this.message = message;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}

