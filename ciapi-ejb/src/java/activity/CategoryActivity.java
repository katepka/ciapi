package activity;

import entity.Category;
import entry.CategoryEntry;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import mapper.CategoryMapper;
import repository.CategoryFacadeLocal;

@Stateless
@LocalBean
public class CategoryActivity {

    @EJB
    private CategoryMapper categoryMapper;

    @EJB
    private CategoryFacadeLocal categoryFacade;
    
    public Category createCategory(CategoryEntry categoryEntry) {
        Category category = categoryMapper.mapCategoryEntryToCategory(categoryEntry);
        categoryFacade.create(category);
        return category;
    }

    public CategoryEntry findById(long id) {
        Category category = categoryFacade.find(id);
        CategoryEntry entry = categoryMapper.mapCategoryToCategoryEntry(category);
        return entry;
    }
    
    public List<CategoryEntry> findAll() {
        List<Category> categoryList = categoryFacade.findAll();
        List<CategoryEntry> entryList = categoryMapper.mapCategoryListToCategoryEntryList(categoryList);
        return entryList;
    }
    
    public CategoryEntry updateCategory(long id, CategoryEntry entry) {
        Category entity = categoryFacade.find(id);
        if (entity != null) {
            if (entry.getDescription() != null) {
                entity.setDescription(entry.getDescription());
            }
            if (entry.getTitle() != null) {
                entity.setTitle(entry.getTitle());
            }
            if (entry.getIconRef() != null) {
                entity.setIconRef(entry.getIconRef());
            }
            categoryFacade.edit(entity);
            return entry;
        } else {
            return null;
        }
    }
}
