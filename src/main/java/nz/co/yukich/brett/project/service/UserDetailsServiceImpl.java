package nz.co.yukich.brett.project.service;

import nz.co.yukich.brett.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Collections;

public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private EntityManager entityManager;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // HQL uses the class name rather than the table name
    Query query = entityManager.createQuery("from User u where u.username = :username");
    try {
      User user = (User) query.setParameter("username", username).getSingleResult();
      return new org.springframework.security.core.userdetails.User(
          user.getUsername(),
          user.getPassword(),
          Collections.emptyList()
      );
    } catch (NoResultException ex) {
      throw new UsernameNotFoundException("Invalid username or password!");
    }
  }
}
