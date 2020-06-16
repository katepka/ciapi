package activity;

import entity.Location;
import entry.LocationEntry;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import mapper.LocationMapper;
import repository.LocationFacadeLocal;

@Stateless
@LocalBean
public class LocationActivity {

    @EJB
    private LocationMapper locationMapper;

    @EJB
    private LocationFacadeLocal locationFacade;
    
    public Location createLocation(LocationEntry locationEntry) {
        Location location = locationMapper.mapLocationEntryToLocation(locationEntry);
        locationFacade.create(location);
        return location;
    }

    public LocationEntry findById(long id) {
        Location location = locationFacade.find(id);
        LocationEntry entry = locationMapper.mapLocationToLocationEntry(location);
        return entry;
    }
    
    public List<LocationEntry> findAll() {
        List<Location> locationList = locationFacade.findAll();
        List<LocationEntry> entryList = locationMapper.mapLocationListToLocationEntryList(locationList);
        return entryList;
    }
    
    public List<LocationEntry> findByName(String name) {
        List<Location> locationList = locationFacade.findByName(name);
        List<LocationEntry> entryList = locationMapper.mapLocationListToLocationEntryList(locationList);
        return entryList;
    }

}
