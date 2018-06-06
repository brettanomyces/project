package nz.co.yukich.brett.project;

import nz.co.yukich.brett.project.model.RegisterRequest;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegisterRequestTest {

  private Validator validator;

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void whenRequestValid_thenRequestValid() {
    Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(validRequest());
    assertTrue(violations.isEmpty());
  }

  @Test
  public void whenUsernameToSmall_thenRequestInvalid() {
    RegisterRequest request = validRequest();
    request.setUsername("dum");

    Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
    assertEquals(1, violations.size());
    assertEquals("{register.username.size}", violations.iterator().next().getMessageTemplate());
  }

  @Test
  public void whenEmailContainsPlus_thenRequestInvalid()
  {
    RegisterRequest request = validRequest();
    request.setEmail("dummy+plus@example.com");
    Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
    assertTrue(violations.isEmpty());
  }

  @Test
  public void whenEmailInvalid_thenRequestInvalid()
  {
    RegisterRequest request = validRequest();
    request.setEmail("dummy+example.com");
    Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
    assertEquals(1, violations.size());
    assertEquals("{register.email}", violations.iterator().next().getMessageTemplate());
  }

  private RegisterRequest validRequest() {
    RegisterRequest request = new RegisterRequest();
    request.setUsername("dummy");
    request.setPassword("password");
    request.setEmail("dummy@example.com");
    return request;
  }
}
