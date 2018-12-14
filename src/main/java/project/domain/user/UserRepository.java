package project.domain.user;

import java.util.Optional;

public interface UserRepository {

  void create(User user);

  Optional<User> findByUsername(String username);
}
