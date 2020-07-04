package mapper;

import entity.Category;
import entity.Idea;
import entity.ImplementationInfo;
import entity.Location;
import entity.Status;
import entity.User;
import entry.IdeaEntry;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import repository.CategoryFacadeLocal;
import repository.ImplementationInfoFacadeLocal;
import repository.LocationFacadeLocal;
import repository.StatusFacadeLocal;
import repository.UserFacadeLocal;

/**
 * Сессионный EJB. Служит для преобразования IdeaEntry в Idea и обратно.
 * @author Теплякова Е.А.
 */
@Stateless
@LocalBean
public class IdeaMapper {
    
    @EJB
    private StatusMapper statusMapper;
    @EJB
    private UserMapper userMapper;
    @EJB
    private LocationMapper locationMapper;
    @EJB
    private CategoryMapper categoryMapper;
    @EJB
    private ImplementationInfoMapper implementationInfoMapper;
    @EJB
    private StatusFacadeLocal statusFacade;
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private LocationFacadeLocal locationFacade;
    @EJB
    private CategoryFacadeLocal categoryFacade;
    @EJB
    private ImplementationInfoFacadeLocal implementationInfoFacade;
    

    public Idea mapIdeaEntryToIdea(IdeaEntry entry) {
        Idea entity = new Idea();
        if (entry.getId() != null) {
            entity.setId(entry.getId());
        }
        if (entry.getTitle() != null) {
            entity.setTitle(entry.getTitle());
        }
        if (entry.getDescription() != null) {
            entity.setDescription(entry.getDescription());
        }
        if (entry.getCreated() != null) {
            entity.setCreated(entry.getCreated());
        }
        if (entry.getPhotoRef()!= null) {
            entity.setPhotoRef(entry.getPhotoRef());
        }
        
        Long statusId = entry.getStatus().getId();
        Status status = statusFacade.find(statusId);
        if (status != null) {
            entity.setStatus(status);
        }
        
        Long authorId = entry.getAuthor().getId();
        User author = userFacade.find(authorId);
        if (author != null) {
            entity.setAuthor(author);
        }
        
        Long categoryId = entry.getCategory().getId();
        Category category = categoryFacade.find(categoryId);
        if (category != null) {
            entity.setCategory(category);
        }
        
        if (entry.getPhotoRef() != null) {
            entity.setPhotoRef(entry.getPhotoRef());
        }
        
        if (entry.getLocation() != null) {

            Long locationId = entry.getLocation().getId();
            if (locationId != null) {
                Location location = locationFacade.find(locationId);
                if (location != null) {
                    entity.setLocation(location);
                }
            } else {
                Location location = new Location();
                if (entry.getLocation().getLat() != null && entry.getLocation().getLon() != null) {
                    location.setLat(entry.getLocation().getLat());
                    location.setLon(entry.getLocation().getLon());
                    if (entry.getLocation().getName() != null) {
                        location.setName(entry.getLocation().getName());
                    }
                    if (entry.getLocation().getRadius() != null) {
                        location.setRadius(entry.getLocation().getRadius());
                    }
                    entity.setLocation(location);
                } else {
                    // TODO: handle the situation when entry doesn't contain a correct location data
                }

            }
        }
        
        
        if (entry.getCoordinator() != null) {
            Long coordinatorId = entry.getCoordinator().getId();
            if (coordinatorId != null) {
                User coordinator = userFacade.find(coordinatorId);
                if (coordinator != null) {
                    entity.setCoordinator(coordinator);
                }
            }
        }

        if (entry.getImplementationInfo() != null) {
            Long implInfoId = entry.getImplementationInfo().getId();
            if (implInfoId != null) {
                ImplementationInfo implInfo = implementationInfoFacade.find(implInfoId);
                if (implInfo != null) {
                    entity.setImplInfo(implInfo);
                }
            }
        }
        
        return entity;
    }
    
    public IdeaEntry mapIdeaToIdeaEntry(Idea entity) {
        IdeaEntry entry = new IdeaEntry();
        
        if (entity.getId() != null) {
            entry.setId(entity.getId());
        }
        
        if (entity.getTitle() != null) {
            entry.setTitle(entity.getTitle());
        } 
        if (entity.getDescription() != null) {
            entry.setDescription(entity.getDescription());
        }
        if (entity.getCreated() != null) {
            entry.setCreated(entity.getCreated());
        }
        if (entity.getPhotoRef()!= null) {
            entry.setPhotoRef(entity.getPhotoRef());
        }

        Status status = entity.getStatus();
        if (status != null) {
            entry.setStatus(statusMapper.mapStatusToStatusEntry(status));
        }
        
        User author = entity.getAuthor();
        if (author != null) {
            entry.setAuthor(userMapper.mapUserToUserEntry(author));
        }
        
        Category category = entity.getCategory();
        if (category != null) {
            entry.setCategory(categoryMapper.mapCategoryToCategoryEntry(category));
        }
        
        Location location = entity.getLocation();
        if (location != null) {
            entry.setLocation(locationMapper.mapLocationToLocationEntry(location));
        }
        
        User coordinator = entity.getCoordinator();
        if (coordinator != null) {
            entry.setCoordinator(userMapper.mapUserToUserEntry(coordinator));
        }
        
        ImplementationInfo implInfo = entity.getImplInfo();
        if (implInfo != null) {
            entry.setImplementationInfo(implementationInfoMapper
                .mapImplementationInfoToImplementationInfoEntry(implInfo));
        }
        
        return entry;
    }
    
    public List<IdeaEntry> mapIdeaListToIdeaEntryList(List<Idea> entities) {
        List<IdeaEntry> entries = new ArrayList<>();
        for (Idea entity : entities) {
            entries.add(mapIdeaToIdeaEntry(entity));
        }
        return entries;
    }
}
