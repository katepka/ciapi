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
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
        <link rel="stylesheet" href="style.css">
        <script type="text/javascript"
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCkdGj98GKwKdu_x85bhv21r49C55Ro_gk">
        </script>
        <script type="text/javascript">
            var map;
            var marker;

            function get_click_position(event) {
                var location = event.latLng;
                var lat = location.lat();
                var lng = location.lng();
                setMarkerPosition(lat, lng);
                app.handle(lat, lng);
            }

            function setMarkerPosition(lat, lng) {
                var clickLatLng = new google.maps.LatLng(lat, lng);
                marker.setPosition(clickLatLng);
            }

            function startJumping() {
                marker.setAnimation(google.maps.Animation.BOUNCE);
            }

            function stopJumping() {
                marker.setAnimation(google.maps.Animation.BOUNCE);
            }

            function setMapCenter(lat, lng) {
                var latlng = new google.maps.LatLng(lat, lng);
                map.setCenter(latlng);
            }

            function initialize() {
                var defLatLng = new google.maps.LatLng(59.93863, 30.31413);
                var mapOptions = {
                    center: defLatLng,
                    zoom: 7,
                    mapTypeId: google.maps.MapTypeId.ROADMAP,
                    disableDefaultUI: true,
                    panControl: false
                };
                map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);

                marker = new google.maps.Marker({
                    position: defLatLng,
                    map: map,
                    icon: "img/Pin.png"
                });
            }
        </script>
    </head>
    <body onload="initialize()">

        <table border="0" width="100%">
            <tr>
                <td><h1>Идеи для города</h1></td>
                <td align="right">
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

            <table border="0" width="100%">
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
                                    <option value="noPlace">Без места</option>
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
                            <div id="map_canvas" style="width:500px; height:200px"></div>
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
                            <p style="color: teal">Загружено фото</p>
                        </c:if>
                    </div>
                    <p>
                    <form action="upload" method="POST" enctype="multipart/form-data">
                        <input type="file" name="fileToUpload"  size="30" /></BR></BR>
                        <input type="submit" value="Отправить" />
                    </form>
                    </p>
                </td>
                </tr>
            </table>

            <tr>
                <td colspan="2" align="center">
                    <hr>
                    Футер со всякой полезной и не очень полезной информацией о сайте. All rights 2020 (c)
                </td>
            </tr>
        </table>
    </body>
</html>
