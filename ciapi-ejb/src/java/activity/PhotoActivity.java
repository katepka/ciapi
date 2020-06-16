package activity;

import entity.Idea;
import entity.ImplementationInfo;
import entity.Photo;
import entry.PhotoEntry;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import mapper.IdeaMapper;
import mapper.ImplementationInfoMapper;
import mapper.PhotoMapper;
import repository.IdeaFacadeLocal;
import repository.ImplementationInfoFacadeLocal;
import repository.PhotoFacadeLocal;

@Stateless
@LocalBean
public class PhotoActivity {

    @EJB
    private ImplementationInfoFacadeLocal implementationInfoFacade;
    @EJB
    private ImplementationInfoMapper implementationInfoMapper;
    @EJB
    private IdeaFacadeLocal ideaFacade;
    @EJB
    private IdeaMapper ideaMapper;
    @EJB
    private PhotoMapper photoMapper;
    @EJB
    private PhotoFacadeLocal photoFacade;
    
    
    
    public Photo createPhoto(PhotoEntry photoEntry) {
        Photo photo = photoMapper.mapPhotoEntryToPhoto(photoEntry);
        photoFacade.create(photo);
        return photo;
    }

    public PhotoEntry findById(long id) {
        Photo photo = photoFacade.find(id);
        PhotoEntry entry = photoMapper.mapPhotoToPhotoEntry(photo);
        return entry;
    }
    
    public List<PhotoEntry> findAll() {
        List<Photo> photoList = photoFacade.findAll();
        List<PhotoEntry> entryList = photoMapper.mapPhotoListToPhotoEntryList(photoList);
        return entryList;
    }
    
    public PhotoEntry updatePhoto(long id, PhotoEntry entry) {
        Photo entity = photoFacade.find(id);
        if (entity != null) {
            if (entry.getPhotoRef()!= null) {
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
            photoFacade.edit(entity);
            return entry;
        } else {
            return null;
        }
    }
    
}
