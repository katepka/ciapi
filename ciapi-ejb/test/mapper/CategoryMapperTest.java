package mapper;

import entity.Category;
import entry.CategoryEntry;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CategoryMapperTest {
    
    public CategoryMapperTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testMapCategoryEntryToCategory() throws Exception {
        System.out.println("mapCategoryEntryToCategory");
        CategoryEntry entry = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        CategoryMapper instance = (CategoryMapper)container.getContext().lookup("java:global/classes/CategoryMapper");
        Category expResult = null;
        Category result = instance.mapCategoryEntryToCategory(entry);
        assertEquals(expResult, result);
        container.close();
        fail("The test case is a prototype.");
    }

    @Test
    public void testMapCategoryToCategoryEntry() throws Exception {
        System.out.println("mapCategoryToCategoryEntry");
        Category entity = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        CategoryMapper instance = (CategoryMapper)container.getContext().lookup("java:global/classes/CategoryMapper");
        CategoryEntry expResult = null;
        CategoryEntry result = instance.mapCategoryToCategoryEntry(entity);
        assertEquals(expResult, result);
        container.close();
        fail("The test case is a prototype.");
    }

    @Test
    public void testMapCategoryListToCategoryEntryList() throws Exception {
        System.out.println("mapCategoryListToCategoryEntryList");
        List<Category> entities = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        CategoryMapper instance = (CategoryMapper)container.getContext().lookup("java:global/classes/CategoryMapper");
        List<CategoryEntry> expResult = null;
        List<CategoryEntry> result = instance.mapCategoryListToCategoryEntryList(entities);
        assertEquals(expResult, result);
        container.close();
        fail("The test case is a prototype.");
    }
    
}
