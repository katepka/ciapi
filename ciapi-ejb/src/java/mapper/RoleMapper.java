package mapper;

import entity.Role;
import entry.RoleEntry;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class RoleMapper {

    public Role mapRoleEntryToRole(RoleEntry entry) {
        Role entity = new Role();
        if (entry.getTitle() != null) {
            entity.setTitle(entry.getTitle());
        }
        return entity;
    }
    
    public RoleEntry mapRoleToRoleEntry(Role entity) {
        RoleEntry entry = new RoleEntry();
        if (entity.getTitle() != null) {
            entry.setTitle(entity.getTitle());
        }
        return entry;
    }
    
    public List<RoleEntry> mapRoleListToRoleEntryList(List<Role> entities) {
        List<RoleEntry> entries = new ArrayList<>();
        for (Role entity : entities) {
            entries.add(mapRoleToRoleEntry(entity));
        }
        return entries;
    }
}
