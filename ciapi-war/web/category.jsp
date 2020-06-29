<%-- 
    Document   : category
    Created on : 16.06.2020, 14:32:26
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
                    <h2>${category.title}</h2>
                    <h3>${category.description}</h3>

                </td>

                <td width="33%">
                    Идей в категории: ${numIdeas} <br>
                    Реализовано идей: ${numImplementedIdeas} <br>
                    <a href="http://localhost:8080/ciapi-war/rating">Посмотреть статистику</a>
                </td>    
            </tr> 
            <td colspan="2">
                <hr>
                <table border="0" width="100%">
                    <tr>
                        <td width="33%">
                            <form action="createidea" method="GET">
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
                </table>
            <tr>  
                <td colspan="2" align="center">
                    <div class="row">
                        <hr>
                        <c:forEach var="idea" items="${ideas}">

                            <div class="column">
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
                            </div>
                        </c:forEach>
                    </div>
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
