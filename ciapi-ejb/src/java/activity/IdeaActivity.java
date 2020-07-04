package activity;

import entity.Category;
import entity.Idea;
import entity.ImplementationInfo;
import entity.Location;
import entity.Status;
import entity.User;
import entry.IdeaEntry;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import mapper.CategoryMapper;
import mapper.IdeaMapper;
import mapper.ImplementationInfoMapper;
import mapper.LocationMapper;
import mapper.StatusMapper;
import mapper.UserMapper;
import repository.CategoryFacadeLocal;
import repository.IdeaFacadeLocal;
import repository.ImplementationInfoFacadeLocal;
import repository.LocationFacadeLocal;
import repository.StatusFacadeLocal;
import repository.UserFacadeLocal;

/**
 * Класс обеспечивает совершение операций создания, сохранения, 
 * поиска по различным атрибутам и выборки всех идей.
 * Методы принимают в качестве параметров и возвращают объекты типа IdeaEntry
 * @author Теплякова Е.А.
 */
@Stateless
@LocalBean
public class IdeaActivity {

    @EJB
    private IdeaMapper ideaMapper;

    @EJB
    private IdeaFacadeLocal ideaFacade;
    
    @EJB
    private LocationMapper locationMapper;

    @EJB
    private ImplementationInfoMapper implementationInfoMapper;
    
    @EJB
    private StatusMapper statusMapper;
    
    @EJB
    private UserMapper userMapper;
    
    @EJB
    private CategoryMapper categoryMapper;
    
    @EJB
    private LocationFacadeLocal locationFacade;

    @EJB
    private ImplementationInfoFacadeLocal implementationInfoFacade;
    
    @EJB
    private StatusFacadeLocal statusFacade;
    
    @EJB
    private UserFacadeLocal userFacade;
    
    @EJB
    private CategoryFacadeLocal categoryFacade;
    
    
    public Idea createIdea(IdeaEntry ideaEntry) {
        Idea idea = ideaMapper.mapIdeaEntryToIdea(ideaEntry);
        ideaFacade.create(idea);
        return idea;
    }

    public IdeaEntry findById(long id) {
        Idea idea = ideaFacade.find(id);
        IdeaEntry entry = ideaMapper.mapIdeaToIdeaEntry(idea);
        return entry;
    }
    
    public List<IdeaEntry> findAll() {
        List<Idea> ideaList = ideaFacade.findAll();
        List<IdeaEntry> entryList = ideaMapper.mapIdeaListToIdeaEntryList(ideaList);
        return entryList;
    }
    
    public List<IdeaEntry> findByCategory(long categoryId) {
        List<Idea> ideaList = ideaFacade.findByCategory(categoryId);
        List<IdeaEntry> entryList = ideaMapper.mapIdeaListToIdeaEntryList(ideaList);
        return entryList;
    }
    
    public List<IdeaEntry> findByStatus(long statusId) {
        List<Idea> ideaList = ideaFacade.findByStatus(statusId);
        List<IdeaEntry> entryList = ideaMapper.mapIdeaListToIdeaEntryList(ideaList);
        return entryList;
    }
    
    public List<IdeaEntry> findByAuthor(long authorId) {
        List<Idea> ideaList = ideaFacade.findByAuthor(authorId);
        List<IdeaEntry> entryList = ideaMapper.mapIdeaListToIdeaEntryList(ideaList);
        return entryList;
    }
    
    public List<IdeaEntry> findByCoordinator(long coordinatorId) {
        List<Idea> ideaList = ideaFacade.findByCoordinator(coordinatorId);
        List<IdeaEntry> entryList = ideaMapper.mapIdeaListToIdeaEntryList(ideaList);
        return entryList;
    }
    
    public List<IdeaEntry> findByLocation(long locationId) {
        List<Idea> ideaList = ideaFacade.findByLocation(locationId);
        List<IdeaEntry> entryList = ideaMapper.mapIdeaListToIdeaEntryList(ideaList);
        return entryList;
    }
    
    public List<IdeaEntry> findByTitle(String title) {
        List<Idea> ideaList = ideaFacade.findByTitle(title);
        List<IdeaEntry> entryList = ideaMapper.mapIdeaListToIdeaEntryList(ideaList);
        return entryList;
    }
    
    public IdeaEntry updateIdea(long id, IdeaEntry entry) {
        Idea entity = ideaFacade.find(id);
        if (entity != null) {
            if (entry.getTitle() != null) {
                entity.setTitle(entry.getTitle());
            }
            if (entry.getDescription() != null) {
                entity.setDescription(entry.getDescription());
            }
            if (entry.getCreated() != null) {
                entity.setCreated(entry.getCreated());
            }
            
            // Новая категория не создается
            Long categoryId = entry.getCategory().getId();
            Category category = categoryFacade.find(categoryId);
            if (category != null) {
                entity.setCategory(category);
            }
            
            // Новый автор не создается
            Long authorId = entry.getAuthor().getId();
            User author = userFacade.find(authorId);
            if (author != null) {
                entity.setAuthor(author);
            }
            
            // Новый координатор не создается
            if (entry.getCoordinator() != null) {
                Long coordinatorId = entry.getCoordinator().getId();
                User coordinator = userFacade.find(coordinatorId);
                if (coordinator != null) {
                    entity.setCoordinator(coordinator);
                }
            }
            
            // Новый статус не создается
            Long statusId = entry.getStatus().getId();
            Status status = statusFacade.find(statusId);
            if (status != null) {
                entity.setStatus(status);
            }
            
            // Новая локация - создается
            if (entry.getLocation() != null) {
                Long locationId = entry.getLocation().getId();
                if (locationId != null) {
                    Location location = locationFacade.find(locationId);
                    if (location != null) {
                        entity.setLocation(location);
                    } else {
                        Location newLocation = locationMapper.mapLocationEntryToLocation(entry.getLocation());
                        locationFacade.create(newLocation);
                        entity.setLocation(newLocation);
                    }
                }
            }
            
            // Новая информация о реализации - создается
            if (entry.getImplementationInfo() != null) {
                Long implementationInfoId = entry.getImplementationInfo().getId();
                if (implementationInfoId != null) {
                    ImplementationInfo implementationInfo = implementationInfoFacade.find(implementationInfoId);
                    if (implementationInfo != null) {
                        entity.setImplInfo(implementationInfo);
                    } else {
                        ImplementationInfo newImplInfo = implementationInfoMapper
                                .mapImplementationInfoEntryToImplementationInfo(entry.getImplementationInfo());
                        implementationInfoFacade.create(newImplInfo);
                        entity.setImplInfo(newImplInfo);
                    }
                } else {
                    ImplementationInfo newImplInfo = implementationInfoMapper
                            .mapImplementationInfoEntryToImplementationInfo(entry.getImplementationInfo());
                    implementationInfoFacade.create(newImplInfo);
                    entity.setImplInfo(newImplInfo);
                }
            }
            
            ideaFacade.edit(entity);
            return entry;
        } else {
            return null;
        }
    }
}
