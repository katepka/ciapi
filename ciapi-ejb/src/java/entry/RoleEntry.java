package entry;

import javax.validation.constraints.NotNull;

/**
 * Класс-модель роли пользователей.
 * Содержит атрибуты и методы доступа, а также ограничения, 
 * которые накладываются на атрибуты.
 * @author Теплякова Е.А.
 */
public class RoleEntry extends AbstractDataEntry {
    
    @NotNull(message = "title cannot be null")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
