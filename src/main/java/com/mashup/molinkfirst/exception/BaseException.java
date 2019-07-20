package com.mashup.molinkfirst.exception;

import lombok.Getter;
import lombok.Setter;

/* 비즈니스 예외처리는 모두 BaseException을 상속받아 구현 */
@SuppressWarnings("serial")
@Getter
@Setter
public class BaseException extends RuntimeException{
  private int statusCode;

  public BaseException(int statusCode) {
    this(statusCode, null);
  }

  public BaseException(int statusCode, String debugMessage) {
    this(statusCode, debugMessage, null);
  }

  public BaseException(int statusCode, String debugMessage, Throwable cause) {
    super(debugMessage, cause);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}