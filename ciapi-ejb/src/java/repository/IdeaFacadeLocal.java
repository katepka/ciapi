package repository;

import entity.Idea;
import java.util.List;
import javax.ejb.Local;

@Local
public interface IdeaFacadeLocal {

    void create(Idea idea);

    void edit(Idea idea);

    Idea remove(Object id);

    Idea find(Object id);

    List<Idea> findAll();
    
    List<Idea> findByCategory(long categoryId);
    
    List<Idea> findByStatus(long statusId);
    
    List<Idea> findByAuthor(long authorId);
    
    List<Idea> findByCoordinator(long coordinatorId);
    
    List<Idea> findByLocation(long locationId);
    
    List<Idea> findByTitle(String title);

    List<Idea> findRange(int[] range);

    long count();
    
}
