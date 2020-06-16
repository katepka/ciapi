package mapper;

import entity.Category;
import entry.CategoryEntry;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class CategoryMapper {
    
    public Category mapCategoryEntryToCategory(CategoryEntry entry) {
        Category entity = new Category();
        if (entry.getDescription() != null) {
            entity.setDescription(entry.getDescription());
        }
        if (entry.getIconRef() != null) {
            entity.setIconRef(entry.getIconRef());
        }
        if (entry.getTitle() != null) {
            entity.setTitle(entry.getTitle());
        }
        return entity;
    }
    
    public CategoryEntry mapCategoryToCategoryEntry(Category entity) {
        CategoryEntry entry = new CategoryEntry();
        if (entity.getTitle() != null) {
            entry.setTitle(entity.getTitle());
        }
        if (entity.getDescription() != null) {
            entry.setDescription(entity.getDescription());
        }
        if (entity.getIconRef() != null) {
            entry.setIconRef(entity.getIconRef());
        }
        return entry;
    }
    
    public List<CategoryEntry> mapCategoryListToCategoryEntryList(List<Category> entities) {
        List<CategoryEntry> entries = new ArrayList<>();
        for (Category entity : entities) {
            entries.add(mapCategoryToCategoryEntry(entity));
        }
        return entries;
    }

}
