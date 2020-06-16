package mapper;

import entity.Status;
import entry.StatusEntry;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class StatusMapper {
    
    public Status mapStatusEntryToStatus(StatusEntry entry) {
        Status entity = new Status();
        if (entry.getTitle() != null) {
            entity.setTitle(entry.getTitle());
        }
        if (entry.getDescription() != null) {
            entity.setDescription(entry.getDescription());
        }
        return entity;
    }
    
    public StatusEntry mapStatusToStatusEntry(Status entity) {
        StatusEntry entry = new StatusEntry();
        if (entity.getTitle() != null) {
            entry.setTitle(entity.getTitle());    
        }
        if (entity.getDescription() != null) {
            entry.setDescription(entity.getDescription());
        }
        return entry;
    }
    
    public List<StatusEntry> mapStatusListToStatusEntryList(List<Status> entities) {
        List<StatusEntry> entries = new ArrayList<>();
        for (Status entity : entities) {
            entries.add(mapStatusToStatusEntry(entity));
        }
        return entries;
    }
}
