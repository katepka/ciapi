<%-- 
    Document   : category
    Created on : 16.06.2020, 14:32:26
    Author     : mi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entry.IdeaEntry"%>
<%@page import="entry.StatusEntry"%>
<%@page import="entry.LocationEntry"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>City Ideas - Идеи для города</title>
    </head>
    <body>
        <table border="1" width="100%">
            <tr>
                <td><h1>Идеи для города</h1></td>
                <td>
                    <form action="authorization" method="GET">
                        <input type="submit" value="ВОЙТИ">
                    </form>
                </td>
            </tr>
            <tr>
                <td>
                    <h1>${categoryTitle}</h1>
                    <h3>${categoryDescription}</h3>
                </td>
                <td width="33%">
                    
                    Идей в категории: ${numIdeas} <br>
                    Реализовано идей: ${numImplementedIdeas} <br>
                    <a href="http://localhost:8080/ciapi-war/rating">Посмотреть статистику</a>
                </td>
            </tr>
            </tr>
            <td colspan="2">
                <table border="1" width="100%">
                    <tr>
                        <td width="33%">
                            <form action="ideas" method="GET">
                                <input type="submit" name="createIdea" value="Предложить идею">
                            </form>
                        </td>
                        <td>
                            Сортировка по:
                            <br> новым
                            <br> популярным
                        </td>
                        <td width="33%">
                            Фильтр по статусу
                        </td>
                    </tr>
                            <c:forEach var="idea" items="${ideas}">
                                <tr>
                                    <td>
                                        <form action="ideas" method="GET">
                                            <br>${idea.status.title}
                                            <br>${idea.created} | ${idea.location.name}
                                            <input type="hidden" name="ideaId" value="${idea.id}">
                                            <input type="submit" name="go" value="${idea.title}">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            
                        
                        <td>
                            <h2>Идея 2</h2>
                        </td>
                        <td>
                            <h2>Идея 3</h2>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <h2>Идея 4</h2>
                        </td>
                        <td>
                            <h2>Идея 5</h2>
                        </td>
                        <td>
                            <h2>Идея 6</h2>
                        </td>
                    </tr>
                </table>
            </td>
            <tr>
                <td colspan="2" align="center">
                    Футер со всякой полезной и не очень полезной информацией о сайте. All rights 2020 (c)
                </td>
            </tr>
        </table>
    </body>
</html>
