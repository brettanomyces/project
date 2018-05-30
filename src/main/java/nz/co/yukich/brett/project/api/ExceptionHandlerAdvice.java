package nz.co.yukich.brett.project.api;

import nz.co.yukich.brett.project.Environment;
import nz.co.yukich.brett.project.model.BaseResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ExceptionHandlerAdvice {

  private Log logger = LogFactory.getLog(ExceptionHandlerAdvice.class);

  private Environment env;

  public ExceptionHandlerAdvice(@Value("${application.env}") Environment env) {
    this.env = env;
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public BaseResponse handle(NotFoundException ex) {
    return new BaseResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public BaseResponse handle(BadRequestException ex) {
    return new BaseResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public BaseResponse handle(Exception ex) {
    logger.warn("An unexpected exception occurred: " + ex.getMessage());
    return new BaseResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        Environment.DEV.equals(this.env) ? ex.getMessage() : HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
    );
  }
}
