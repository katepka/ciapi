package mapper;

import entity.ImplementationInfo;
import entry.ImplementationInfoEntry;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 * Сессионный EJB. Служит для преобразования ImplementationInfoEntry
 * в ImplementationInfo и обратно.
 * @author Теплякова Е.А.
 */
@Stateless
@LocalBean
public class ImplementationInfoMapper {

    public ImplementationInfo mapImplementationInfoEntryToImplementationInfo(ImplementationInfoEntry entry) {
        ImplementationInfo entity = new ImplementationInfo();
        if (entry.getDescription() != null) {
            entity.setDescription(entry.getDescription());
        }
        return entity;
    }
    
    public ImplementationInfoEntry mapImplementationInfoToImplementationInfoEntry(ImplementationInfo entity) {
        ImplementationInfoEntry entry = new ImplementationInfoEntry();
        if (entity.getId() != null) {
            entry.setId(entity.getId());
        }
        if (entity.getDescription() != null) {
            entry.setDescription(entity.getDescription());
        }
        return entry;
    }
    
    public List<ImplementationInfoEntry> mapImplementationInfoListToImplementationInfoEntryList(
            List<ImplementationInfo> entities) {
        List<ImplementationInfoEntry> entries = new ArrayList<>();
        for (ImplementationInfo entity : entities) {
            entries.add(mapImplementationInfoToImplementationInfoEntry(entity));
        }
        return entries;
    }
}
