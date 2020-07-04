package activity;

import entity.Role;
import entry.RoleEntry;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import mapper.RoleMapper;
import repository.RoleFacadeLocal;

/**
 * Класс обеспечивает совершение операций создания, поиска по идентификатору 
 * и выборки всех ролей пользователей.
 * Методы принимают в качестве параметров и возвращают объекты типа RoleEntry
 * @author Теплякова Е.А.
 */
@Stateless
@LocalBean
public class RoleActivity {

    @EJB
    private RoleMapper roleMapper;
    @EJB
    private RoleFacadeLocal roleFacade;
    
    public Role createRole(RoleEntry roleEntry) {
        Role role = roleMapper.mapRoleEntryToRole(roleEntry);
        roleFacade.create(role);
        return role;
    }

    public RoleEntry findById(long id) {
        Role role = roleFacade.find(id);
        RoleEntry entry = roleMapper.mapRoleToRoleEntry(role);
        return entry;
    }
    
    public List<RoleEntry> findAll() {
        List<Role> roleList = roleFacade.findAll();
        List<RoleEntry> entryList = roleMapper.mapRoleListToRoleEntryList(roleList);
        return entryList;
    }

}
