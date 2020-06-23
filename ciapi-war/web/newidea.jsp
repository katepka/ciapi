<%-- 
    Document   : newidea
    Created on : 16.06.2020, 14:33:27
    Author     : mi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entry.CategoryEntry"%>
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
                    <td align="right">
                        <form action="login" method="GET">
                            <input type="submit" value="ВОЙТИ">
                        </form>
                    </td>
                </tr>
                <form action="ideas" method="POST">
                    <table border="1" width="100%">
                <tr>
                    <td colspan="2" align="left">
                        <h2>Есть идея, как сделать жизнь города лучше?</h2>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p>Выберите категорию:</p>
                        <p>
                            <select name="categoryId">
                                <c:forEach var="category" items="${categories}">
                                    <option value="${category.id}">${category.title}</option>
                                </c:forEach> 
                            </select>
                        </p>
                    </td>
                    <td>
                        <p>Выберите город или отметьте место на карте:</p>
                        <p>
                            <select name="locationId">
                                <c:forEach var="location" items="${locations}">
                                    <c:if test="${location.name != null}">
                                        <option value="${location.id}">${location.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </p>
                        
                    </td>
                </tr>
                <tr>
                    <td>
                        <p>В чем заключается идея? Сформулируйте кратко суть:</p>
                        <p>
                            <input type="text" name="title" value="Заголовок идеи">
                        </p>
                    </td>
                    <td rowspan="2">
                        Место под карту
                    </td>
                </tr>
                <tr>
                    <td>
                        <p>Добавьте описание идеи:</p>
                        <p>
                            <input type="text" name="description" value="Описание">
                        </p>
                    </td>
                    
                </tr>
                <tr>
                    <td>
                        <p>Прикрепите фотографии:</p>
                        <p>
                            Иконки фоток
                        </p>
                        <p>
                            <input type="file" name="foto" multiple draggable="true" accept="image/*,image/jpeg,image/png">
                        </p>
                    </td>
                    <td>
                        <input type="submit" value="ПРЕДЛОЖИТЬ ИДЕЮ" name="create">
                        <input type="submit" value="ОТМЕНА" name="cancel">
                    </td>
                </tr>
                    </table>
                </form>

                <tr>
                    <td colspan="2">
                        Футер со всякой полезной и не очень полезной информацией о сайте. All rights 2020 (c)
                    </td>
                </tr>
            </table>
    </body>
</html>
