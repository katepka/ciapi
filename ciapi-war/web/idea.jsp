<%-- 
    Document   : idea
    Created on : 16.06.2020, 14:32:36
    Author     : mi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entry.CommentEntry"%>
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
                <td width="33%" align="right">
                    <form action="login" method="GET">
                        <input type="submit" value="ВОЙТИ">
                    </form>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="left">
                    <h2>${idea.title}</h2>
                </td>
            </tr>
            <tr>
                <td>
                    <div>${idea.category.title}</div>
                    <div>Автор: ${idea.author.name}</div>
                    <div>Координатор: ${ideaCoordinatorName}</div>
                </td>
                <td align="right">
                    <div>${idea.status.title}</div>
                    <div>${ideaLocationName} | Дата создания: ${idea.created}</div>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="left">
                    <div>
                        ${idea.description}
                    </div>
                </td>
            </tr>
            <tr>
                <td>Фотографии</td>
                <td>
                    <form action="ideas" method="GET">
                        <input type="hidden" name="ideaId" value="${idea.id}">
                        <input type="submit" name="voteFor" value="За идею:"> ${votesFor} | 
                        <input type="submit" name="voteAgainst" value="Против идеи:"> ${votesAgainst}
                    </form>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="left">
                    <form action="ideas" method="POST">
                        <div>Обсуждение идеи</div>
                        <input type="text" name="commentText" value="Что Вы думаете об этой идее? Поделитесь своим мнением">
                        <input type="hidden" name="ideaId" value="${idea.id}">
                        <input type="submit" name="comment" value="Прокомментировать">
                        <br><br>
                        <div>Список комментариев:</div><br>
                        <c:forEach var="comment" items="${comments}">
                            <div>${comment.author.name} | ${comment.created}</div>
                            <div>${comment.text}</div>
                            <br>
                        </c:forEach>
                    </form>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    Футер со всякой полезной и не очень полезной информацией о сайте. All rights 2020 (c)
                </td>
            </tr>
        </table>
    </body>
</html>
