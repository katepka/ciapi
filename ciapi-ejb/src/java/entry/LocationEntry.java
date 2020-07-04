package entry;

import javax.validation.constraints.NotNull;

/**
 * Класс-модель локации.
 * Содержит атрибуты и методы доступа, а также ограничения, 
 * которые накладываются на атрибуты.
 * @author Теплякова Е.А.
 */
public class LocationEntry extends AbstractDataEntry {
    
    @NotNull(message = "lat cannot be null")
    private Float lat;
    
    @NotNull(message = "lon cannot be null")
    private Float lon;
    
    private Float radius;
    
    private String name;

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public Float getRadius() {
        return radius;
    }

    public void setRadius(Float radius) {
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
