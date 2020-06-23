<%-- 
    Document   : category
    Created on : 16.06.2020, 14:32:26
    Author     : mi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                <td><h1>Идеи для города</h1></td>
                <td>
                    <form action="login" method="GET">
                        <input type="submit" value="ВОЙТИ">
                    </form>
                </td>
            </tr>
            <tr>
                <td>
                    <h1>${category.title}</h1>
                    <h3>${category.description}</h3>
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
                        <form action="category" method="GET">
                        <td>
                            <p><input type="radio" name="sortBy" value="new" checked>сначала новые<br>
                            <input type="radio" name="sortBy" value="popular">сначала популярные</p>
                            <p><input type="submit" name="sort" value="Отсортировать">
                        </td>
                        <td width="33%">
                            <select name="status" selected="${selectedStatus}">
                                <option value="all">Все</option>
                                <option value="1">Новая</option>
                                <option value="2">На голосовании</option>
                                <option value="3">Реализована</option>
                                <option value="4">Закрыта</option>
                            </select>
                            <input type="hidden" name="categoryId" value="${category.id}">
                            <input type="submit" name="filter" value="Отфильтровать">
                        </td>
                        </form>
                    </tr>
                            <c:forEach var="idea" items="${ideas}">
                                <tr>
                                    <td>
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
