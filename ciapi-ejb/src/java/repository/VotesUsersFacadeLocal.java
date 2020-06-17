package repository;

import entity.VotesUsers;
import java.util.List;
import javax.ejb.Local;

@Local
public interface VotesUsersFacadeLocal {

    void create(VotesUsers votesUsers);

    void edit(VotesUsers votesUsers);

    VotesUsers remove(Object id);

    VotesUsers find(Object id);

    List<VotesUsers> findAll();

    List<VotesUsers> findRange(int[] range);

    long count();
    
}
