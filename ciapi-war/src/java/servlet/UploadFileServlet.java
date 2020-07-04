package servlet;

import activity.CategoryActivity;
import activity.LocationActivity;
import com.ibm.useful.http.FileData;
import com.ibm.useful.http.PostData;
import entry.CategoryEntry;
import entry.LocationEntry;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import util.AppConstants;

/**
 * Обрабатывает POST-запрос на загрузку на сервер содержимого формы типа 
 * «multipart/form-data» (файла). 
 * Ссылка на загруженный в папку проекта файл в виде атрибута запроса 
 * перенаправляется обратно на JSP страницу создания новой идеи.
 * 
 * @author Теплякова Е.А.
 */
@WebServlet(name = "UploadFileServlet", urlPatterns = {"/upload"})
public class UploadFileServlet extends HttpServlet {

    @EJB
    private LocationActivity locationActivity;
    @EJB
    private CategoryActivity categoryActivity;
    private String filename = null;
    private List<CategoryEntry> categories = new ArrayList<>();
    private List<LocationEntry> locations = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        if (request.getContentType().contains("multipart/form-data")) {
            PostData multidata = new PostData(request);
            FileData tempFile = multidata.getFileData("fileToUpload");
            if (tempFile != null) {
                filename = saveFile(tempFile, AppConstants.PATH_TO_PHOTO_STORAGE);
            }
            if (filename != null) {
                request.setAttribute("filename", filename);
            }
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

    private String saveFile(FileData tempFile, String path) {
        // TODO: нужно как-то генерировать filename, чтобы повторяющиеся имена сохранялись
        String filename = path + File.separator + tempFile.getFileName();
        File f = new File(filename);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewIdeaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (fos != null) {
            try {
                fos.write(tempFile.getByteData());
                fos.close();
                return "images/" + tempFile.getFileName();
            } catch (IOException ex) {
                Logger.getLogger(NewIdeaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
