package com.mashup.molinkfirst.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Already Exists")
public class AlreadyExistsException extends BaseException {

  public AlreadyExistsException() {
    super(HttpStatus.BAD_REQUEST.value(), "Already Exists");
  }

  public AlreadyExistsException(String message) {
    super(HttpStatus.BAD_REQUEST.value(), message);
  }
}