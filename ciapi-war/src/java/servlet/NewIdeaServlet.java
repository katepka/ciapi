package servlet;

import activity.CategoryActivity;
import activity.IdeaActivity;
import activity.LocationActivity;
import entry.CategoryEntry;
import entry.IdeaEntry;
import entry.LocationEntry;
import entry.StatusEntry;
import entry.UserEntry;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.AppUtils;
import validation.EntryValidator;

/**
 * Обрабатывает POST-запрос на добавление новой идеи. 
 * Сервлет обращается к подсистеме взаимодействия с базой данных и отправляет 
 * переданное через форму пользовательского ввода представление об идее. 
 * Делается новая запись в базу данных. Осуществляется переход к главной странице.
 * 
 * @author Теплякова Е.А.
 */
@WebServlet(name = "NewIdeaServlet", urlPatterns = {"/createidea"})
public class NewIdeaServlet extends HttpServlet {

    @EJB
    private IdeaActivity ideaActivity;
    @EJB
    private CategoryActivity categoryActivity;
    @EJB
    private LocationActivity locationActivity;
    private IdeaEntry newIdea = null;
    private List<CategoryEntry> categories = new ArrayList<>();
    private List<LocationEntry> locations = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // ================= Формирование страницы newidea.jsp ======================
        String createIdea = request.getParameter("createIdea");
        if (createIdea != null && !createIdea.isEmpty()) {
            try {
                categories = categoryActivity.findAll();
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                }
            }
            try {
                locations = locationActivity.findAll();
            } catch (Exception ex) {
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                }
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
            }
            request.setAttribute("categories", categories);
            request.setAttribute("locations", locations);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/newidea.jsp");
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // ======= Отправка данных со страницы newidea.jsp на сервер ==========
        if (request.getParameter("create") != null) {
            newIdea = new IdeaEntry();
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String categoryName = request.getParameter("categorySelected");
            String categoryId = request.getParameter("categoryId");
            String locationId = request.getParameter("locationId");
            // TODO: get coordinates from map
            String lat = "34.9";
            String lon = "67.0";
            String radius = "99";

            UserEntry author = AppUtils.getLoginedUser(request.getSession());
            newIdea.setAuthor(author);

            StatusEntry status = new StatusEntry();
            status.setId(1L);
            newIdea.setStatus(status);

            if (title != null) {
                newIdea.setTitle(title);
            }
            if (description != null) {
                newIdea.setDescription(description);
            }
            if (categoryId != null) {
                CategoryEntry category = categoryActivity.findById(Long.parseLong(categoryId));
                if (category != null) {
                    newIdea.setCategory(category);
                } else {
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/notfound.jsp");
                    if (requestDispatcher != null) {
                        requestDispatcher.forward(request, response);
                    }
                }
            } else {
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/notfound.jsp");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                }
            }
            if (!"noPlace".equals(locationId)) {
                LocationEntry location = new LocationEntry();
                if (locationId != null) {
                    location.setId(Long.parseLong(locationId));
                } else {
                    location.setLat(Float.parseFloat(lat));
                    location.setLon(Float.parseFloat(lon));
                    location.setRadius(Float.parseFloat(radius));
                }
                newIdea.setLocation(location);
            }

            //======== Сохранение ссылки на фото ==========
            String photoRef = request.getParameter("filename");
            if (photoRef != null) {
                newIdea.setPhotoRef(photoRef);
            }
            newIdea.setCreated(Date.valueOf(LocalDate.now()));
            try {
                EntryValidator.validate(newIdea);
                ideaActivity.createIdea(newIdea);
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/start");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                }
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                }
            }
        }

        // ================== Отмена создания новой идеи =======================
        if (request.getParameter("cancel") != null) {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/start");
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        }
    }

}
