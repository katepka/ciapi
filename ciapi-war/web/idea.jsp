<%-- 
    Document   : idea
    Created on : 16.06.2020, 14:32:36
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
                <td>
                    <form action="authorization" method="GET">
                        <input type="submit" value="ВОЙТИ">
                    </form>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="left">
                    <h2>Название идеи</h2>
                </td>
            </tr>
            <tr>
                <td>
                    <div>Название категории</div>
                    <div>Автор: имя автора</div>
                    <div>Координатор: если есть - имя, если нет - разыскивается. Стать координатором</div>
                </td>
                <td align="right">
                    <div>Статус идеи</div>
                    <div>Место | Дата создания: дата создания</div>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="left">
                    <div>
                        <p>Описание идеи.</p>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
                            Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. 
                            Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</p> 
                        <p>Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
                            Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p> 
                        <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
                            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
                            Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. 
                            Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
                            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Фотографии</td>
                <td>За идею: "за" | Против идеи: "против"</td>
            </tr>
            <tr>
                <td colspan="2" align="left">
                    <form action="comment" method="POST">
                        <div>Обсуждение идеи</div>
                        <input type="text" value="Что Вы думаете об этой идее? Поделитесь своим мнением">
                        <input type="submit" name="comment" value="Прокомментировать">
                        <br><br>
                        <div>Список комментариев:</div><br>
                        <div>Имя автора | Дата</div>
                        <div>Текст комментария</div>
                        <br>
                        <div>Имя автора | Дата</div>
                        <div>Текст комментария</div>
                    </form>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    Футер со всякой полезной и не очень полезной информацией о сайте. All rights 2020 (c)
                </td>
            </tr>
        </table>
    </body>
</html>
