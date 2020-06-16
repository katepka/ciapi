package controller;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(controller.CategoryController.class);
        resources.add(controller.IdeaController.class);
        resources.add(controller.RatingController.class);
        resources.add(controller.RegistrationController.class);
        resources.add(controller.RoleController.class);
        resources.add(controller.StatusController.class);
        resources.add(controller.UserController.class);
        resources.add(security.AuthenticationController.class);
        resources.add(security.AuthenticationFilter.class);
    }
    
}
