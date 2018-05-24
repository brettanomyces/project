package nz.co.yukich.brett.project.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.yukich.brett.project.auth.model.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JsonLogoutSuccessHandler implements LogoutSuccessHandler {

  private ObjectMapper objectMapper;

  public JsonLogoutSuccessHandler(@Autowired ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    BaseResponse json = new BaseResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
    response.getWriter().write(objectMapper.writeValueAsString(json));
  }
}
