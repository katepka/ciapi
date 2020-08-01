package entry;

import java.io.Serializable;

/**
 * Класс-абстрактная модель данных.
 * Содержит ссылку на общий атрибут всех Entry - уникальный идентификатор.
 * @author Теплякова Е.А.
 */
public abstract class AbstractDataEntry implements Serializable {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
