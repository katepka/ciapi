<%-- 
    Document   : registration
    Created on : 16.06.2020, 14:32:50
    Author     : mi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            </tr>
            <tr >
                <td>
                    <form action="registrate" method="POST">
                        <table border="0" align="center">
                            <tr>
                            <div align="center" style="color: crimson">${errorMessage}</div>
                            <br>
                            <td align="center">
                                <h4>РЕГИСТРАЦИЯ</h4>
                            </td>
                            </tr>
                            <tr>
                                <td>Ваш email:</td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="text" name="email" value="${emailMustBeValid}">
                                </td>
                            </tr>
                            <tr>
                                <td>Ваше имя:</td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="text" name="name" value="${nameMustBeValid}">
                                </td>
                            </tr>
                            <tr>
                                <td>Придумайте пароль:</td>
                            </tr>
                            <tr>
                                <td>
                                    <c:if test="${passwordMustBeValid != null}">
                                        <p style="color: tomato">${passwordMustBeValid}</p>
                                    </c:if>
                                    <input type="password" name="password">
                                </td>
                            </tr>
                            <tr>
                                <td>Введите пароль еще раз:</td>
                            </tr>
                            <tr>
                                <td>
                                    <c:if test="${passwordRepeatMustBeValid != null}">
                                        <p style="color: tomato">${passwordRepeatMustBeValid}</p>
                                    </c:if>
                                    <input type="password" name="passwordRepeat">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="submit" value="ЗАРЕГИСТРИРОВАТЬСЯ" name="registrate">
                                </td>
                            </tr>
                        </table>
                    </form>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <hr>
                    Футер со всякой полезной и не очень полезной информацией о сайте. All rights 2020 (c)
                </td>
            </tr>
        </table>
    </body>
</html>
