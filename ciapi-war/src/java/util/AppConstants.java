package util;

public interface AppConstants {
    String BASE_URI = "http://localhost:8080/ciapi-war/webresources";
    String PATH_TO_PHOTO_STORAGE = "C:\\java_dev\\java_study\\ciapi\\ciapi-war\\web\\images";
    
    String URL_PATTERN_LOGIN = "/login";
    String URL_PATTERN = "/registrate";
    String URL_PATTERN_MAIN = "/start";
    String URL_PATTERN_CATEGORY = "/category";
    String URL_PATTERN_IDEA = "/ideas";
    String URL_PATTERN_CREATE_NEW_IDEA = "/ideas?createIdea";
    String URL_PATTERN_NEW_IDEA = "/createidea";
    String URL_PATTERN_ADD_COMMENT = "/comment";
    String URL_PATTERN_VOTE = "/vote";
}
