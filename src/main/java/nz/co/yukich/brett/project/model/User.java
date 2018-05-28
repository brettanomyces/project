package nz.co.yukich.brett.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @JsonIgnore
  private final Integer id;

  @Column(unique = true)
  @NotBlank
  private final String username;

  @NotBlank
  @JsonIgnore
  private final String password;

  public User() {
    this.id = null;
    this.username = null;
    this.password = null;
  }

  public User(String username, String password) {
    this.id = null;
    this.username = username;
    this.password = password;
  }

  public Integer getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
