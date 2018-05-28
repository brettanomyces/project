package nz.co.yukich.brett.project.api;

public class BadRequestException extends Exception {
  public BadRequestException(String message) {
    super(message);
  }
}
