package nz.co.yukich.brett.project.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.yukich.brett.project.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationProcessingFilterImpl extends UsernamePasswordAuthenticationFilter {

  private ObjectMapper objectMapper;

  public AuthenticationProcessingFilterImpl(@Autowired ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);
      request.setAttribute(getUsernameParameter(), loginRequest.getUsername());
      request.setAttribute(getPasswordParameter(), loginRequest.getPassword());
    } catch (IOException e) {
      request.setAttribute(getUsernameParameter(), null);
      request.setAttribute(getPasswordParameter(), null);
    }
    return super.attemptAuthentication(request, response);
  }

  @Override
  protected String obtainUsername(HttpServletRequest request) {
    return (String) request.getAttribute(getUsernameParameter());
  }

  @Override
  protected String obtainPassword(HttpServletRequest request) {
    return (String) request.getAttribute(getPasswordParameter());
  }
}
