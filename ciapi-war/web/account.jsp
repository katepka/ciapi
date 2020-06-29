<%-- 
    Document   : account
    Created on : 29.06.2020, 19:12:47
    Author     : mi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entry.UserEntry"%>
<%@page import="entry.RoleEntry"%>
<%@page import="entry.IdeaEntry"%>
<%@page import="entry.CategoryEntry"%>
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
                <td>
                    <h3>Добрый день, ${user.name}!</h3>
                    <h3>Ваша личная информация:</h3>
                    <p>Email: ${user.email}</p>
                    <p>Имя пользователя: ${user.name}</p>
                    <p>Роль в системе: ${user.role.title}</p>
                    <h3>Ваши идеи:</h3>
                    <ul>
                        <c:forEach var="idea" items="${ideas}">
                            <li>
                                <form action="ideas" method="GET">
                                    <input type="hidden" name="ideaId" value="${idea.id}">
                                    <input type="submit" name="go" value="${idea.title}">
                                </form>
                                    <br>${idea.status.title}
                                    <br>${idea.created}
                            </li>
                        </c:forEach>    
                    </ul>

                    <h3>Идеи, которые Вы координируете:</h3>
                    <ul>
                        <c:forEach var="ideaImpl" items="${ideasImpl}">
                            <li>
                                <form action="ideas" method="GET">
                                    <input type="hidden" name="ideaId" value="${ideaImpl.id}">
                                    <input type="submit" name="go" value="${ideaImpl.title}">
                                </form>
                                    <br>${ideaImpl.status.title}
                                    <br>${ideaImpl.created}
                                <p> Добавить информацию о реализации:
                                    <form action="ideas" method="POST">
                                        <input type="hidden" name="ideaId" value="${ideaImpl.id}">
                                        <input type="text" name="implInfo">
                                        <input type="submit" name="setImplInfo" value="Опубликовать">
                                    </form>
                                </p>
                            </li>
                        </c:forEach>
                    </ul>
                                        
                </td>
            </tr>
            <tr>
                <td align="center">
                    Футер со всякой полезной и не очень полезной информацией о сайте. All rights 2020 (c)
                </td>
            </tr>
        </table>
    </body>
</html>
