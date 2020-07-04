package validation;

/**
 * Исключение типа ValidationEntryException выбрасывается методом 
 * void validate(AbstractDataEntry entry) класса EntryValidator при
 * обнаружении несоответствия entry заданным ограничениям.
 * @author Теплякова Е.А.
 */
public class ValidationEntryException extends RuntimeException {
    String message = "Validation entry error!";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
