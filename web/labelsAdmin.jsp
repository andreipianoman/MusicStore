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
        <sql:query dataSource="${snapshot}" var="labels">
            SELECT METAL.LABELS.ID, METAL.LABELS.NAME, METAL.LABELS.WEBSITE, METAL.COUNTRIES.NAME AS COUNTRY, METAL.IMAGES.ADDRESS AS IMAGE
            FROM METAL.LABELS
            INNER JOIN METAL.COUNTRIES ON METAL.LABELS.COUNTRY_ID = METAL.COUNTRIES.ID
            INNER JOIN METAL.IMAGES ON METAL.LABELS.IMAGE_ID = METAL.IMAGES.ID
        </sql:query>
        <h1>Admin Labels</h1>
        <form action="${pageContext.request.contextPath}/AdminLabelsServlet" method="POST"">
        <table border="1">
            <thead>
                <tr>
                    <td></td>
                    <td>Name</td>
                    <td>Website</td>
                    <td>Country</td>
                    <td>Image</td>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="row" varStatus="loop" items="${labels.rows}">
                    <tr>
                        <td><input type="checkbox" name="labelIdCheckbox" value="${row.id}"></td>
                        <td>${row.name}</td>
                        <td>${row.website}</td>
                        <td>${row.country}</td>
                        <td><img class="cartImage" src="${row.image}"></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <span>Label name: </span><input type="text" name="Label name"><br>
        <span>Website: </span><input type="text" name="website"><br>
            
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
        <input type="submit" value="insert" name="insert">
        <br>
        <input type="submit" value="update" name="update">
        <br>
        <input type="submit" value="delete" name="delete">
        </form>
    </body>
</html>