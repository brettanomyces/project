package nz.co.yukich.brett.project.api;

import nz.co.yukich.brett.project.auth.model.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerAdvice {

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public BaseResponse handle(NotFoundException ex) {
    return new BaseResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
  }
}
