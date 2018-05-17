package nz.co.yukich.brett.project.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.yukich.brett.project.auth.model.LoginRequest;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    ObjectMapper mapper = new ObjectMapper();
    try {
      LoginRequest loginRequest = mapper.readValue(request.getReader(), LoginRequest.class);
      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
      return this.getAuthenticationManager().authenticate(token);
    } catch (IOException e) {
      throw new InternalAuthenticationServiceException(e.getMessage());
    }
  }
}
