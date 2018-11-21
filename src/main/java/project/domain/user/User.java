package project.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@RequiredArgsConstructor
public class User {

  @Getter
  @Id
  private String id;
  @NonNull
  @Getter
  private String username;
  @NonNull
  @Getter
  private String password;
  @NonNull
  @Getter
  private String email;

}
