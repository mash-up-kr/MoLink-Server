package com.mashup.molinkfirst.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException{
  public BadRequestException(String exceptionMessage) {
    super(HttpStatus.BAD_REQUEST.value(), exceptionMessage);
  }
}
