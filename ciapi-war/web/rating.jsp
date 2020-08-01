<%-- 
    Document   : rating
    Created on : 04.07.2020, 14:03:12
    Author     : mi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entry.IdeaEntry"%>
<%@page import="entry.UserEntry"%>
<%@page import="entry.CategoryEntry"%>
<%@page import="entry.StatusEntry"%>
<%@page import="entry.LocationEntry"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>City Ideas - Идеи для города</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <table border="0" width="100%">
            <tr>
                <td><h1>Идеи для города</h1></td>
                <td>
                    <c:if test="${loginedUser == null}">
                        <form action="login" method="GET">
                            <input type="submit" name="login" value="ВОЙТИ">
                        </form>
                    </c:if>
                    <c:if test="${loginedUser != null}">
                        <form action="account" method="GET">
                            <input type="submit" name="loginedUser" value="${loginedUser.name}">
                        </form>
                        <form action="logout" method="GET">
                            <input type="submit" name="logout" value="ВЫЙТИ">
                        </form>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>
                    <h2>Самые популярные идеи</h2>
                    <h4>В этом разделе можно посмотреть топ-10 наиболее популярных идей</h4>
                </td>
                <td>
                    Всего идей: ${numIdeas} <br>
                    Реализовано идей: ${numImplementedIdeas} <br>
                </td>
            </tr>
            <td colspan="2">
                
            <tr>  
                <td colspan="2" align="center">
                    <ol>
                        <hr>
                        <c:forEach var="idea" items="${ideas}">

                            <li>
                                <form action="ideas" method="GET">
                                    <br>${idea.status.title}
                                    <br>${idea.created} | 
                                    <c:if test="${idea.location.name != null}">
                                        ${idea.location.name}
                                    </c:if>
                                    <br>"За": ${idea.votesFor} | "Против": ${idea.votesAgainst}<br>
                                    <input type="hidden" name="ideaId" value="${idea.id}">
                                    <input type="submit" name="go" value="${idea.title}">
                                </form>
                            </li>
                        </c:forEach>
                    </ol>
                </td>
            </tr>      
            <tr>
                <td colspan="2" align="center">
                    <hr>
                    Футер со всякой полезной и не очень полезной информацией о сайте. All rights 2020 (c)
                </td>
            </tr>
        </table>
    </body>
</html>
