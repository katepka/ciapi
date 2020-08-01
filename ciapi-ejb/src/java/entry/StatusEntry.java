package entry;

import javax.validation.constraints.NotNull;

/**
 * Класс-модель статуса идей.
 * Содержит атрибуты и методы доступа, а также ограничения, 
 * которые накладываются на атрибуты.
 * @author Теплякова Е.А.
 */
public class StatusEntry extends AbstractDataEntry {
    
    @NotNull(message = "title cannot be null")
    private String title;
    
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
