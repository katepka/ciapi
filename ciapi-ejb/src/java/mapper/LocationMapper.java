package mapper;

import entity.Location;
import entry.LocationEntry;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class LocationMapper {

        public Location mapLocationEntryToLocation(LocationEntry entry) {
        Location entity = new Location();
        if (entry.getLat() != null) {
            entity.setLat(entry.getLat());
        }
        if (entry.getLon() != null) {
            entity.setLon(entry.getLon());
        }
        if (entry.getRadius() != null) {
            entity.setRadius(entry.getRadius());
        }
        if (entry.getName() != null) {
           entity.setName(entry.getName()); 
        }
        return entity;
    }
    
    public LocationEntry mapLocationToLocationEntry(Location entity) {
        LocationEntry entry = new LocationEntry();
        if (entity.getLat() != null) {
            entry.setLat(entity.getLat());
        }
        if (entity.getLon() != null) {
            entry.setLon(entity.getLon());
        }
        if (entity.getRadius() != null) {
            entry.setRadius(entity.getRadius());
        }
        if (entity.getName() != null) {
            entry.setName(entity.getName());
        }
        return entry;
    }
    
    public List<LocationEntry> mapLocationListToLocationEntryList(List<Location> entities) {
        List<LocationEntry> entries = new ArrayList<>();
        for (Location entity : entities) {
            entries.add(mapLocationToLocationEntry(entity));
        }
        return entries;
    }
}
