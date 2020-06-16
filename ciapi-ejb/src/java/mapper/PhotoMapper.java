package mapper;

import entity.Idea;
import entity.ImplementationInfo;
import entity.Photo;
import entry.PhotoEntry;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import repository.IdeaFacadeLocal;
import repository.ImplementationInfoFacadeLocal;

@Stateless
@LocalBean
public class PhotoMapper {

    @EJB
    private IdeaMapper ideaMapper;
    @EJB
    private ImplementationInfoMapper implInfoMapper;
    @EJB
    private IdeaFacadeLocal ideaFacade;
    @EJB
    private ImplementationInfoFacadeLocal implementationInfoFacade;
    
    public Photo mapPhotoEntryToPhoto(PhotoEntry entry) {
        Photo entity = new Photo();
        if (entry.getPhotoRef() != null) {
            entity.setPhotoRef(entry.getPhotoRef());
        }
        Long ideaId = entry.getIdea().getId();
        Idea idea = ideaFacade.find(ideaId);
        if (idea != null) {
            entity.setIdea(idea);
        }
        
        Long implInfoId = entry.getImplementationInfo().getId();
        ImplementationInfo implInfo = implementationInfoFacade.find(implInfoId);
        if (implInfo != null) {
            entity.setImplInfo(implInfo);
        }
        
        return entity;
    }
    
    public PhotoEntry mapPhotoToPhotoEntry(Photo entity) {
        PhotoEntry entry = new PhotoEntry();
        if (entity.getPhotoRef() != null) {
            entry.setPhotoRef(entity.getPhotoRef());
        }
        Idea idea = entity.getIdea();
        if (idea != null) {
            entry.setIdea(ideaMapper.mapIdeaToIdeaEntry(idea));
        }
        
        ImplementationInfo implInfo = entity.getImplInfo();
        if (implInfo != null) {
            entry.setImplementationInfo(implInfoMapper
                .mapImplementationInfoToImplementationInfoEntry(implInfo));
        }
        return entry;
    }
    
    public List<PhotoEntry> mapPhotoListToPhotoEntryList(List<Photo> entities) {
        List<PhotoEntry> entries = new ArrayList<>();
        for (Photo entity : entities) {
            entries.add(mapPhotoToPhotoEntry(entity));
        }
        return entries;
    }
}
