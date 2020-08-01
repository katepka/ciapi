package entry;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Класс-модель категории.
 * Содержит атрибуты и методы доступа, а также ограничения, 
 * которые накладываются на атрибуты.
 * @author Теплякова Е.А.
 */
public class CategoryEntry extends AbstractDataEntry {
    @NotNull(message = "title cannot be null")
    @Size(min = 1, max = 255)
    private String title;
    
    private String description;
    
    private String iconRef;

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

    public String getIconRef() {
        return iconRef;
    }

    public void setIconRef(String iconRef) {
        this.iconRef = iconRef;
    }
    
}
