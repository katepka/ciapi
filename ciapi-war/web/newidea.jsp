<%-- 
    Document   : newidea
    Created on : 16.06.2020, 14:33:27
    Author     : mi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entry.CategoryEntry"%>
<%@page import="entry.UserEntry"%>
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
                
                    <table border="1" width="100%">
                <tr>
                    <td colspan="2" align="left">
                        <h2>Есть идея, как сделать жизнь города лучше?</h2>
                    </td>
                </tr>
                <form action="createidea" method="POST">
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
                        <input type="hidden" name="filename" value="${filename}">
                        <input type="submit" value="ПРЕДЛОЖИТЬ ИДЕЮ" name="create">
                        <input type="submit" value="ОТМЕНА" name="cancel">
                    </td>
                </form>

                    <td>
                        <div>
                            <c:if test="${filename == null}">
                                <p>Загрузите фотографию:</p>
                            </c:if>
                            <c:if test="${filename != null}">
                                <p style="color: teal">Загружено фото: ${filename}</p>
                            </c:if>
                        </div>
                        <p>
                            <!--<input type="file" name="foto" multiple draggable="true" accept="image/*,image/jpeg,image/png">-->
                                <form action="upload" method="POST" enctype="multipart/form-data">
                                    <input type="file" name="fileToUpload"  size="30" /></BR></BR>
                                    <input type="submit" value="Отправить" />
                                </form>
                        </p>
                    </td>
                </tr>
            </table>

                <tr>
                    <td colspan="2">
                        Футер со всякой полезной и не очень полезной информацией о сайте. All rights 2020 (c)
                    </td>
                </tr>
            </table>
    </body>
</html>
