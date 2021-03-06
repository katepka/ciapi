package validation;

import entry.AbstractDataEntry;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Класс проверяет полученный в качестве параметра экземпляр AbstractDataEntry
 * на соответствие заданным для полей ограничениям.
 * При наличии несоответствий выбрасывается исключение типа ValidationEntryException
 * @author Теплякова Е.А.
 */
public class EntryValidator {
    
    private EntryValidator() {}
    
    public static void validate(AbstractDataEntry entry) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<AbstractDataEntry>> violations = validator.validate(entry);
        
        if (!violations.isEmpty()) {
            StringBuilder message = new StringBuilder();

            for (ConstraintViolation<AbstractDataEntry> violation : violations) {
                message.append(" - ").append(violation.getMessage()).append(";\n");
            }
            ValidationEntryException validationEntryException = new ValidationEntryException();
            validationEntryException.setMessage(message.toString());

            throw validationEntryException;
        }
        
    }
}
