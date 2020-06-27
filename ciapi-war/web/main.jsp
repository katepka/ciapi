<%-- 
    Document   : main
    Created on : 16.06.2020, 14:32:04
    Author     : mi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="entry.CategoryEntry"%>
<%@page import="entry.UserEntry"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                    <c:if test="${loginedUser == null}">
                        <form action="login" method="GET">
                            <input type="submit" name="login" value="ВОЙТИ">
                        </form>
                    </c:if>
                    <c:if test="${loginedUser != null}">
                        <input type="submit" name="loginedUser" value="${loginedUser.name}">
                        <form action="logout" method="GET">
                            <input type="submit" name="logout" value="ВЫЙТИ">
                        </form>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>
                    <h1>Есть идея, как сделать жизнь города лучше?</h1>
                    <h3>Предлагай, обсуждай, голосуй, выбирай лучшие</h3>
                    <h1>Знаешь, как реализовать?</h1>
                    <h3>Стань координатором! Координатором реализации может стать местная администрация, 
                        отдельный политик, общественный деятель или бизнес</h3>
                </td>
                <td>
                    
                    Всего идей: ${numIdeas} <br>
                    Реализовано идей: ${numImplementedIdeas} <br>
                    <a href="http://localhost:8080/ciapi-war/rating">Посмотреть статистику</a>
                </td>
            </tr>
            </tr>
            <td colspan="2">
                <table border="1" width="100%">
                    <tr>
                        <td width="33%"></td>
                        <td align="center">
                            <form action="createidea" method="GET">
                                <input type="submit" name="createIdea" value="Предложить идею">
                            </form>
                                
                        </td>
                        <td width="33%"></td>
                    </tr>
                    
                    <c:forEach var="category" items="${categories}" >
                    <tr>
                        <td>
                            <form action="category" method="GET">
                                <input type="submit" name="go" value="${category.title}">
                                <input type="hidden" name="categoryId" value="${category.id}">
                                <br>${category.description}
                                <br>${category.iconRef}
                            </form>
                        </td>
                    </tr>
                    </c:forEach>
                </table>
            </td>
            <tr>
                <td colspan="2">
                    Футер со всякой полезной и не очень полезной информацией о сайте. All rights 2020 (c)
                </td>
            </tr>
        </table> 
    </body>  
</html>