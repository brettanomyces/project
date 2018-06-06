package nz.co.yukich.brett.project.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterRequest {

  @NotNull
  @Size(min = 4, message = "{register.username.size}")
  private String username;

  @NotNull
  @Size(min = 8, message = "{register.password.size}")
  private String password;

  @NotNull
  @Email(message = "{register.email}")
  private String email;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
