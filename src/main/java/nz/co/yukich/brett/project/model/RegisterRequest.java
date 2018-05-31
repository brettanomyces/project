package nz.co.yukich.brett.project.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterRequest {

  public static final String INVALID_USERNAME_SIZE_MESSAGE = "Username must be at least {min} characters";
  public static final String INVALID_PASSWORD_SIZE_MESSAGE = "Password must be at least {min} characters";
  public static final String INVALID_EMAIL_MESSAGE = "Email is not valid";

  @NotNull
  @Size(min = 4, message = INVALID_USERNAME_SIZE_MESSAGE)
  private String username;

  @NotNull
  @Size(min = 8, message = INVALID_PASSWORD_SIZE_MESSAGE)
  private String password;

  @NotNull
  @Email(message = INVALID_EMAIL_MESSAGE)
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
