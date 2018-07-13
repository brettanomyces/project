package project.user;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class UserService {

  private EntityManager entityManager;

  private PasswordEncoder passwordEncoder;

  public UserService(EntityManager entityManager, PasswordEncoder passwordEncoder) {
    this.entityManager = entityManager;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public User createUser(String username, String password) {
    User user = new User(
        username,
        passwordEncoder.encode(password)
    );

    entityManager.persist(user);

    return user;
  }

  public User retrieveUser(String username) {
    Query query = entityManager.createQuery("from User u where u.username = :username");
    try {
      return (User) query.setParameter("username", username).getSingleResult();
    } catch (NoResultException ex) {
      return null;
    }
  }

}
