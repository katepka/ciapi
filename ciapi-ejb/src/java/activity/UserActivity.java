package activity;

import entity.User;
import entry.UserEntry;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import mapper.UserMapper;
import repository.UserFacadeLocal;

@Stateless
@LocalBean
public class UserActivity {

    @EJB
    private UserMapper userMapper;
    @EJB
    private UserFacadeLocal userFacade;
    
    
    public User createUser(UserEntry userEntry) {
        User user = userMapper.mapUserEntryToUser(userEntry);
        userFacade.create(user);
        return user;
    }

    public UserEntry findById(long id) {
        User user = userFacade.find(id);
        UserEntry entry = userMapper.mapUserToUserEntry(user);
        return entry;
    }
    
    public List<UserEntry> findAll() {
        List<User> userList = userFacade.findAll();
        List<UserEntry> entryList = userMapper.mapUserListToUserEntryList(userList);
        return entryList;
    }   
    
    public List<UserEntry> findByRole(long roleId) {        
        List<User> userList = userFacade.findByRole(roleId);
        List<UserEntry> entryList = userMapper.mapUserListToUserEntryList(userList);
        return entryList;
    }
    
    public UserEntry findUser(String email, String password) {
        User user = userFacade.findUser(email, password);
        UserEntry entry = userMapper.mapUserToUserEntry(user);
        return entry;
    }
    
    public UserEntry updateUser(long id, UserEntry entry) {
        User entity = userFacade.find(id);
        if (entity != null) {
            if (entry.getEmail() != null) {
                entity.setEmail(entry.getEmail());
            }
            if (entry.getPassword() != null) {
                entity.setPassword(entry.getPassword());
            }
            if (entry.getName() != null) {
                entity.setName(entry.getName());
            }
            userFacade.edit(entity);
            return entry;
        } else {
            return null;
        }
    }
    
}
