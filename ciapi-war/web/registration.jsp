<%-- 
    Document   : registration
    Created on : 16.06.2020, 14:32:50
    Author     : mi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            </tr>
            <tr >
                <td>
                    <form action="registrate" method="POST">
                        <table border="1" align="center">
                            <tr>
                                <div align="center" style="color: crimson">${errorMessage}</div>
                                <br>
                                <td>РЕГИСТРАЦИЯ</td>
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
                                    <input type="password" name="password">
                                </td>
                            </tr>
                            <tr>
                                <td>Введите пароль еще раз:</td>
                            </tr>
                            <tr>
                                <td>
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
                <td>
                    Футер со всякой полезной и не очень полезной информацией о сайте. All rights 2020 (c)
                </td>
            </tr>
        </table>
    </body>
</html>
