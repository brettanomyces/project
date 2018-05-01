package nz.co.yukich.brett.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final String id;

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    @NotBlank
    private final String email;

    public User()
    {
      this.id = null;
      this.username = null;
      this.password = null;
      this.email = null;
    }

    public User(String username, String password, String email) {
        this.id = null;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
