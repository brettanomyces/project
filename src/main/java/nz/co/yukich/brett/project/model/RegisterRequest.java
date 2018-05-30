package nz.co.yukich.brett.project.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterRequest {

  @NotNull
  @Size(min = 4, message = "Username must be at least 6 characters")
  private String username;

  @NotNull
  @Size(min = 8, message = "Password must be at least 8 characters")
  private String password;

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
