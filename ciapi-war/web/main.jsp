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
                    <h2>Есть идея, как сделать жизнь города лучше?</h2>
                    <h4>Предлагай, обсуждай, голосуй, выбирай лучшие</h34>
                        <h2>Знаешь, как реализовать?</h2>
                        <h4>Стань координатором! Координатором реализации может стать местная администрация, 
                            отдельный политик, общественный деятель или бизнес</h3>
                            </td>
                            <td>

                                Всего идей: ${numIdeas} <br>
                                Реализовано идей: ${numImplementedIdeas} <br>
                                <a href="http://localhost:8080/ciapi-war/rating">Посмотреть статистику</a>
                            </td>
                            </tr>
                            <td colspan="2">
                                <hr>
                                <table border="0" width="100%">
                                    <tr>
                                        <td colspan="2" align="center">
                                            <form action="createidea" method="GET">
                                                <input type="submit" name="createIdea" value="Предложить идею">
                                            </form>
                                            <hr>    
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center">
                                            <div class="row">
                                                <c:forEach var="category" items="${categories}" >
                                                    <div class="column">
                                                        <form action="category" method="GET">
                                                            <input type="submit" name="go" value="${category.title}">
                                                            <input type="hidden" name="categoryId" value="${category.id}">
                                                            <br>${category.description}
                                                            <c:if test="${category.iconRef != null}">
                                                                <br>${category.iconRef}
                                                            </c:if>
                                                        </form>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <tr>
                                <td colspan="2" align="center">
                                    <hr>
                                    Футер со всякой полезной и не очень полезной информацией о сайте. All rights 2020 (c)
                                </td>
                            </tr>
                            </table> 
                            </body>  
                            </html>