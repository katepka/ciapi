package activity;

import entity.Status;
import entry.StatusEntry;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import mapper.StatusMapper;
import repository.StatusFacadeLocal;

@Stateless
@LocalBean
public class StatusActivity {

    @EJB
    private StatusFacadeLocal statusFacade;
    @EJB
    private StatusMapper statusMapper;

    public Status createStatus(StatusEntry statusEntry) {
        Status status = statusMapper.mapStatusEntryToStatus(statusEntry);
        statusFacade.create(status);
        return status;
    }

    public StatusEntry findById(long id) {
        Status status = statusFacade.find(id);
        StatusEntry entry = statusMapper.mapStatusToStatusEntry(status);
        return entry;
    }
    
    public List<StatusEntry> findAll() {
        List<Status> statusList = statusFacade.findAll();
        List<StatusEntry> entryList = statusMapper.mapStatusListToStatusEntryList(statusList);
        return entryList;
    }
    
    public StatusEntry updateStatus(long id, StatusEntry entry) {
        Status entity = statusFacade.find(id);
        if (entity != null) {
            if (entry.getDescription() != null) {
                entity.setDescription(entry.getDescription());
            }
            if (entry.getTitle() != null) {
                entity.setTitle(entry.getTitle());
            }
            statusFacade.edit(entity);
            return entry;
        } else {
            return null;
        }
    }
        
}
