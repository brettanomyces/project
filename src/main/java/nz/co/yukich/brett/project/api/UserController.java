package nz.co.yukich.brett.project.api;

import nz.co.yukich.brett.project.model.Registration;
import nz.co.yukich.brett.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@Transactional
public class UserController {

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  EntityManager entityManager;

  @RequestMapping(method = RequestMethod.POST, value = "/api/user")
  public ResponseEntity register(@Valid Registration registration) {
    if (!registration.getPassword().equals(registration.getConfirmPassword())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    if (!registration.getEmail().equals(registration.getConfirmEmail())) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    String encodedPassword = encoder.encode(registration.getPassword());

    User user = new User(registration.getUsername(), encodedPassword, registration.getEmail());
    user = entityManager.merge(user);

    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/api/user/current")
  public User retrieveUser(Authentication authentication) throws NotFoundException {
    org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

    Query query = entityManager.createQuery("from User u where u.username = :username");
    try {
      return (User) query.setParameter("username", securityUser.getUsername()).getSingleResult();
    } catch (NoResultException e) {
      // this is an authorized endpoint so something has gone wrong if there is no current user!
      throw new NotFoundException();
    }
  }
}
