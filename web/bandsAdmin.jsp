<%-- 
    Document   : genresAdmin
    Created on : Feb 19, 2019, 7:55:39 PM
    Author     : Turbotwins
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./css/metalStore.css">
        <script type="text/javascript" src="./JavaScript/script.js"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <body>
        <%@ include file="./utils/menu.jsp" %>
        <sql:setDataSource 
        var="snapshot" 
        driver="org.apache.derby.jdbc.ClientDriver"
        url="jdbc:derby://localhost:1527/metal;create=true;"
        user="metal"  
        password="metal"/>
        <sql:query dataSource="${snapshot}" var="bands">
            SELECT METAL.BANDS.ID, METAL.BANDS.NAME, METAL.GENRES.NAME AS GENRE, METAL.BANDS.WEBSITE, METAL.LABELS.NAME AS LABEL, METAL.COUNTRIES.NAME AS COUNTRY, METAL.IMAGES.ADDRESS AS IMAGE
            FROM METAL.BANDS
            INNER JOIN METAL.GENRES ON METAL.BANDS.GENRE_ID = METAL.GENRES.ID
            INNER JOIN METAL.COUNTRIES ON METAL.BANDS.COUNTRY_ID = METAL.COUNTRIES.ID
            INNER JOIN METAL.LABELS ON METAL.BANDS.LABEL_ID = METAL.LABELS.ID
            INNER JOIN METAL.IMAGES ON METAL.BANDS.IMAGE_ID = METAL.IMAGES.ID
        </sql:query>
        <h1>Admin Bands</h1>
        <form action="${pageContext.request.contextPath}/AdminBandsServlet" method="POST"">
        <table border="1">
            <thead>
                <tr>
                    <td></td>
                    <td>Name</td>
                    <td>Genre</td>
                    <td>Website</td>
                    <td>Label</td>
                    <td>Country</td>
                    <td>Image</td>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="row" varStatus="loop" items="${bands.rows}">
                    <tr>
                        <td><input type="checkbox" name="bandIdCheckbox" value="${row.id}"></td>
                        <td>${row.name}</td>
                        <td>${row.genre}</td>
                        <td>${row.website}</td>
                        <td>${row.label}</td>
                        <td>${row.country}</td>
                        <td><img class="cartImage" src="${row.image}"></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <span>Band name: </span><input type="text" name="Band name"><br>
        
        <sql:query dataSource="${snapshot}" var="genres">
            SELECT METAL.GENRES.ID, METAL.GENRES.NAME
            FROM METAL.GENRES
        </sql:query>
        <span>Genre: </span>
        <select name="genre">
            <c:forEach var="row" varStatus="loop" items="${genres.rows}">
                <option value="${row.id}">${row.name}</option>
            </c:forEach>
        </select><br>
        
        <span>Website: </span><input type="text" name="website"><br>
        
        <sql:query dataSource="${snapshot}" var="labels">
            SELECT METAL.LABELS.ID, METAL.LABELS.NAME
            FROM METAL.LABELS
        </sql:query>
        <span>Label: </span>
        <select name="label">
            <c:forEach var="row" varStatus="loop" items="${labels.rows}">
                <option value="${row.id}">${row.name}</option>
            </c:forEach>
        </select><br>
            
        <sql:query dataSource="${snapshot}" var="countries">
            SELECT METAL.COUNTRIES.ID, METAL.COUNTRIES.NAME
            FROM METAL.COUNTRIES
        </sql:query>
        <span>Country: </span>
        <select name="country">
            <c:forEach var="row" varStatus="loop" items="${countries.rows}">
                <option value="${row.id}">${row.name}</option>
            </c:forEach>
        </select><br>
            
        <sql:query dataSource="${snapshot}" var="images">
            SELECT METAL.IMAGES.ID, METAL.IMAGES.ADDRESS
            FROM METAL.IMAGES
        </sql:query>
            <span>Image: </span><br>
            <div class="adminImage">
                <c:forEach var="row" varStatus="loop" items="${images.rows}">
                    <input class="imageCheckbox" type="checkbox" id="myCheckbox${row.id}" value="${row.id}" onclick="check(${row.id})" name="imageCheckbox"/>
                    <label for="myCheckbox${row.id}"><img class="cartImage" src="${row.address}" onclick="check(${row.id})"/></label><br>
                </c:forEach>
            </div>
        <br>
        
        <br>
        <input type="submit" value="insert" name="insert">
        <br>
        <input type="submit" value="update" name="update">
        <br>
        <input type="submit" value="delete" name="delete">
        </form>
    </body>
    </body>
</html>
