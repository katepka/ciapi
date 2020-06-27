package servlet;

import client.CategoryClient;
import client.IdeaClient;
import client.LocationClient;
import client.UserClient;
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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ClientErrorException;
import util.AppConstants;

@WebServlet(name = "UploadFileServlet", urlPatterns = {"/upload"})
public class UploadFileServlet extends HttpServlet {
    
    private CategoryClient categoryClient;
    private LocationClient locationClient;
    private String filename = null;
    private List<CategoryEntry> categories = new ArrayList<>();
    private List<LocationEntry> locations = new ArrayList<>();
    
    @Override
    public void init() {
        categoryClient = new CategoryClient();
        locationClient = new LocationClient();
    }

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
                categories = categoryClient.getAllCategories_JSON();
            } catch (ClientErrorException cee) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                }
            }
            try {
                locations = locationClient.getAllLocations_JSON();
            } catch (ClientErrorException cee) {
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                }
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
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
    public String getServletInfo() {
        return "Short description";
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
                return filename;
            } catch (IOException ex) {
                Logger.getLogger(NewIdeaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    @Override
    public void destroy() {
        if (categoryClient != null) {
            categoryClient.close();
        }
        if (locationClient != null) {
            locationClient.close();
        }
    }

}
