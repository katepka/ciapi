package activity;

import entity.ImplementationInfo;
import entry.ImplementationInfoEntry;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import mapper.ImplementationInfoMapper;
import repository.ImplementationInfoFacadeLocal;

@Stateless
@LocalBean
public class ImplementationInfoActivity {

    @EJB
    private ImplementationInfoMapper implementationInfoMapper;

    @EJB
    private ImplementationInfoFacadeLocal implementationInfoFacade;

    public ImplementationInfo createImplementationInfo(ImplementationInfoEntry implementationInfoEntry) {
        ImplementationInfo implementationInfo = implementationInfoMapper
                .mapImplementationInfoEntryToImplementationInfo(implementationInfoEntry);
        implementationInfoFacade.create(implementationInfo);
        return implementationInfo;
    }

    public ImplementationInfoEntry findById(long id) {
        ImplementationInfo implementationInfo = implementationInfoFacade.find(id);
        ImplementationInfoEntry entry = implementationInfoMapper
                .mapImplementationInfoToImplementationInfoEntry(implementationInfo);
        return entry;
    }
    
    public List<ImplementationInfoEntry> findAll() {
        List<ImplementationInfo> implementationInfoList = implementationInfoFacade.findAll();
        List<ImplementationInfoEntry> entryList = implementationInfoMapper
                .mapImplementationInfoListToImplementationInfoEntryList(implementationInfoList);
        return entryList;
    }
    
    public ImplementationInfoEntry updateStatus(long id, ImplementationInfoEntry entry) {
        ImplementationInfo entity = implementationInfoFacade.find(id);
        if (entity != null) {
            if (entry.getDescription() != null) {
                entity.setDescription(entry.getDescription());
            }
            implementationInfoFacade.edit(entity);
            return entry;
        } else {
            return null;
        }
    }
}
