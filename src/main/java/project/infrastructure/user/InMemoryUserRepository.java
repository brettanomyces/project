package project.infrastructure.user;

import project.domain.user.User;
import project.domain.user.UserRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {

  private Collection<User> users = new HashSet<>();

  @Override
  public void create(User user) {
    users.add(user);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return users.stream().filter(user -> username.equals(user.getUsername())).findFirst();
  }
}
