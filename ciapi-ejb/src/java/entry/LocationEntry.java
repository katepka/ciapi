package entry;

import javax.validation.constraints.NotNull;

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

    public void setLat(float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public Float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
