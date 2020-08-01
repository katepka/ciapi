<%-- 
    Document   : authorization
    Created on : 16.06.2020, 14:33:03
    Author     : mi
--%>

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
                    <form action="login" method="POST">
                        <div align="center" style="color: crimson">${errorMessage}</div>
                        <br>
                        <table border="0" align="center">
                            <tr>
                                <td align="center">
                                    <h4>ВХОД НА ПЛАТФОРМУ</h4>
                                </td>
                            </tr>
                            <tr>
                                <td>Введите логин:</td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="text" name="userName" value="${emailMustBeValid}">
                                </td>
                            </tr>
                            <tr>
                                <td>Введите пароль:</td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="password" name="password" value="${passwordMustBeValid}">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="submit" value="ВОЙТИ" name="login">
                                </td>
                            </tr>
                            <tr>
                                <td>Нет аккаунта? <a href="http://localhost:8080/ciapi-war/registration.jsp">Зарегистрироваться</a></td>
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
