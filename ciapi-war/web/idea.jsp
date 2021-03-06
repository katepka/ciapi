<%-- 
    Document   : idea
    Created on : 16.06.2020, 14:32:36
    Author     : mi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entry.CommentEntry"%>
<%@page import="entry.UserEntry"%>
<%@page import="entry.IdeaEntry"%>
<%@page import="entry.ImplementationInfoEntry"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>City Ideas - Идеи для города</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <table border="0" width="100%" height="100%">
            <tr>
                <td><h1>Идеи для города</h1></td>
                <td width="33%" align="right">
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
                <td colspan="2" align="left">
                    <hr>
                    <h2>${idea.title}</h2>
                </td>
            </tr>
            <tr>
                <td>
                    <div><strong>${idea.category.title}</strong></div>
                    <div>Автор: ${idea.author.name}</div>
                    <div>
                        Координатор: ${ideaCoordinatorName}
                        <c:if test="${ideaCoordinatorName == null}">
                            <form action="becomeCoordinator" method="GET">
                                <input type="hidden" name="ideaId" value="${idea.id}">
                                Разыскивается! <input type="submit" name="becomeACoordinator" value="Стать координатором">
                            </form>
                        </c:if>
                    </div>
                </td>
                <td align="right">
                    <div>
                        ${idea.status.title}
                        <c:if test="${loginedUser.role.id == 1 || loginedUser.role.id == 3}">
                            <form action="ideas" method="POST">
                                <select name="newStatus">
                                    <option value="1">Новая</option>
                                    <option value="2">На голосовании</option>
                                    <option value="3">Реализована</option>
                                    <option value="4">Закрыта</option>
                                </select>
                                <input type="hidden" name="ideaId" value="${idea.id}">
                                <input type="submit" name="changeStatus" value="Поменять статус">
                            </form>
                        </c:if>
                    </div>
                    <div>${ideaLocationName} | Дата создания: ${idea.createdFormatted}</div>
                </td>
            </tr>
            <tr><td colspan="2"><hr></td></tr>
            
            <tr>
                <td align="left">
                    
                    <div>
                        ${idea.description}
                    </div>
                </td>
                <td>
                    <c:if test="${idea.implementationInfo != null}">
                        <p><strong>Отчет о реализации:</strong></p>
                        ${idea.implementationInfo.description}
                    </c:if>
                    
            </tr>
            <tr>
                <td>
                    <c:if test="${idea.photoRef != null}">
                        <img src="${pageContext.request.contextPath}/${idea.photoRef}" alt="photo" height="220">
                    </c:if>
                </td>
                <td>
                    <form action="vote" method="POST">
                        <input type="hidden" name="ideaId" value="${idea.id}">
                        <input type="submit" name="voteFor" value="За идею:"> ${votesFor} | 
                        <input type="submit" name="voteAgainst" value="Против идеи:"> ${votesAgainst}
                    </form>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="left">
                    <form action="comment" method="POST">
                        <div>
                            <p><strong>Обсуждение идеи</strong></p>
                        </div>
                        <div>
                            Что Вы думаете об этой идее? Поделитесь своим мнением
                        </div>
                        <input type="text" name="commentText">
                        <input type="hidden" name="ideaId" value="${idea.id}">
                        <input type="submit" name="comment" value="Прокомментировать">
                        <br><br>
                    </form>
                        <div><strong>Список комментариев:</strong></div><br>
                    <c:forEach var="comment" items="${comments}">
                        <div>${comment.author.name} | ${comment.createdFormatted}</div>
                        <div>${comment.text}</div>
                        <br>
                    </c:forEach>
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
