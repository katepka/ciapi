package mapper;

import entity.Role;
import entity.User;
import entry.UserEntry;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import repository.RoleFacadeLocal;

@Stateless
@LocalBean
public class UserMapper {

    @EJB
    private RoleMapper roleMapper;
    
    @EJB
    private RoleFacadeLocal roleFacade;

    public User mapUserEntryToUser(UserEntry entry) {
        User entity = new User();
        Long roleId = entry.getRole().getId();
        Role role = roleFacade.find(roleId);
        
        if (entry.getEmail() != null) {
            entity.setEmail(entry.getEmail());
        }
        if (entry.getPassword() != null) {
            entity.setPassword(entry.getPassword());
        }
        if (entry.getName() != null) {
            entity.setName(entry.getName());
        }
        if (role != null) {
            entity.setRole(role);
        }
        return entity;
    }
    
    public UserEntry mapUserToUserEntry(User entity) {
        UserEntry entry = new UserEntry();
        if (entity.getId() != null) {
            entry.setId(entity.getId());
        }
        if (entity.getEmail() != null) {
            entry.setEmail(entity.getEmail());
        }
        if (entity.getPassword() != null) {
            entry.setPassword(entity.getPassword());
        }
        if (entity.getName() != null) {
            entry.setName(entity.getName());
        }
        Role role = entity.getRole();
        if (role != null) {
            entry.setRole(roleMapper.mapRoleToRoleEntry(role));
        }
        return entry;
    } 
    
    public List<UserEntry> mapUserListToUserEntryList(List<User> entities) {
        List<UserEntry> entries = new ArrayList<>();
        for (User user : entities) {
            entries.add(mapUserToUserEntry(user));
        }
        return entries;
    }
}
