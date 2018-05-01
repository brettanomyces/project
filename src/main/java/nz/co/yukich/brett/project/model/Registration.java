package nz.co.yukich.brett.project.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Registration {

  @NotBlank
  private final String username;

  @NotBlank
  @Size(min = 8)
  private final String password;

  @NotBlank
  private final String confirmPassword;

  @NotBlank
  private final String email;

  @NotBlank
  private final String confirmEmail;

  public Registration(String username, String password, String confirmPassword, String email, String confirmEmail) {
    this.username = username;
    this.password = password;
    this.confirmPassword = confirmPassword;
    this.email = email;
    this.confirmEmail = confirmEmail;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public String getEmail() {
    return email;
  }

  public String getConfirmEmail() {
    return confirmEmail;
  }
}
