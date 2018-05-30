package nz.co.yukich.brett.project.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.yukich.brett.project.model.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

  private ObjectMapper objectMapper;

  public AuthenticationSuccessHandlerImpl(@Autowired ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    BaseResponse json = new BaseResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
    response.getWriter().write(objectMapper.writeValueAsString(json));
  }
}