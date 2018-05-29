package nz.co.yukich.brett.project.model;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
