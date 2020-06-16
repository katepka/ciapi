package repository;

import entity.VotesIdeas;
import java.util.List;
import javax.ejb.Local;

@Local
public interface VotesIdeasFacadeLocal {

    void create(VotesIdeas votesIdeas);

    void edit(VotesIdeas votesIdeas);

    VotesIdeas remove(Object id);

    VotesIdeas find(Object id);

    List<VotesIdeas> findAll();

    List<VotesIdeas> findRange(int[] range);

    int count();
    
}
