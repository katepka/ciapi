package validation;

public class ValidationEntryException extends RuntimeException {
    String message = "Validation entry error!";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
