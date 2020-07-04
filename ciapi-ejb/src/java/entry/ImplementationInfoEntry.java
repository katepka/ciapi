package entry;

/**
 * Класс-модель информации о реализации.
 * Содержит атрибуты и методы доступа, а также ограничения, 
 * которые накладываются на атрибуты.
 * @author Теплякова Е.А.
 */
public class ImplementationInfoEntry extends AbstractDataEntry {
    
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
