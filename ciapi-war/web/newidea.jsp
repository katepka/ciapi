<%-- 
    Document   : newidea
    Created on : 16.06.2020, 14:33:27
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
                <form action="ideas" method="POST">
                <tr>
                
                    <td colspan="2" align="left">
                        <h2>Есть идея, как сделать жизнь города лучше?</h2>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p>Выберите категорию:</p>
                        <p>
                            <select>
                                <option>Категория 1</option>
                                <option>Категория 2</option>
                                <option>Категория 3</option>
                                <option>Категория 4</option>
                            </select>
                        </p>
                    </td>
                    <td>
                        <p>Выберите город или отметьте место на карте:</p>
                        <p>
                            <select>
                                <option>Место 1</option>
                                <option>Место 2</option>
                                <option>Место 3</option>
                                <option>Место 4</option>
                            </select>
                        </p>
                        
                    </td>
                </tr>
                <tr>
                    <td>
                        <p>В чем заключается идея? Сформулируйте кратко суть:</p>
                        <p>
                            <input type="text" value="Заголовок идеи">
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
                            <input type="text" value="Описание">
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
                </form>

                <tr>
                    <td colspan="2">
                        Футер со всякой полезной и не очень полезной информацией о сайте. All rights 2020 (c)
                    </td>
                </tr>
            </table>
    </body>
</html>
