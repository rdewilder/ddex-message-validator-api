package net.ddex.ern.exception;

public class ERNException extends Exception {

  private static final long serialVersionUID = 1L;
  private String status;
  private String errorMessage;

  public ERNException() {
    super();
  }
  
  public ERNException(String errorMessage) {
    super(errorMessage);
    this.errorMessage = errorMessage;
  }
  
  public ERNException(String status, String errorMessage) {
    super(errorMessage);
    this.status = status;
    this.errorMessage = errorMessage;
  }

  public ERNException(String errorMessage, Throwable throwable) {
    super(throwable);
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
  
  public String getStatus() {
    return status;
  }
}
