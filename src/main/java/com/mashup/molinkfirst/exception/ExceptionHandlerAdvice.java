package com.mashup.molinkfirst.exception;

import com.mashup.molinkfirst.model.ErrorModel;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerAdvice {
  @ExceptionHandler(BaseException.class)
  public @ResponseBody
  ErrorModel handleException(BaseException e){
    return new ErrorModel(e.getStatusCode(), e.getMessage());
  }
}