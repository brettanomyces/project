package project.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Builder
public class User {

  @Getter @Builder.Default
  private String id = UUID.randomUUID().toString();

  @NonNull
  @Getter
  private String username;

  @JsonIgnore
  @NonNull
  @Getter
  private String password;

  @NonNull
  @Getter
  private String email;
}
