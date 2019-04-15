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
        <sql:query dataSource="${snapshot}" var="items">
            SELECT METAL.ITEMS.ID, METAL.ITEMS.NAME, METAL.ITEMS.PRICE, METAL.ITEMS.STOCK, METAL.CATEGORIES.NAME AS CATEGORY, METAL.IMAGES.ADDRESS AS IMAGE, METAL.BANDS.NAME AS BAND, METAL.GENRES.NAME AS GENRE, METAL.LABELS.NAME AS LABEL
            FROM METAL.ITEMS
            INNER JOIN METAL.CATEGORIES ON METAL.ITEMS.CATEGORY_ID = METAL.CATEGORIES.ID
            INNER JOIN METAL.IMAGES ON METAL.ITEMS.IMAGE_ID = METAL.IMAGES.ID
            INNER JOIN METAL.BANDS ON METAL.ITEMS.BAND_ID = METAL.BANDS.ID
            INNER JOIN METAL.GENRES ON METAL.ITEMS.GENRE_ID = METAL.GENRES.ID
            INNER JOIN METAL.LABELS ON METAL.ITEMS.LABEL_ID = METAL.LABELS.ID
        </sql:query>
        <h1>Admin Items</h1>
        <form action="${pageContext.request.contextPath}/AdminItemsServlet" method="POST"">
        <table border="1">
            <thead>
                <tr>
                    <td></td>
                    <td>Name</td>
                    <td>Price</td>
                    <td>Stock</td>
                    <td>Category</td>
                    <td>Image</td>
                    <td>Band</td>
                    <td>Genre</td>
                    <td>Label</td>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="row" varStatus="loop" items="${items.rows}">
                    <tr>
                        <td><input type="checkbox" name="itemIdCheckbox" value="${row.id}"></td>
                        <td>${row.name}</td>
                        <td>${row.price}</td>
                        <td>${row.stock}</td>
                        <td>${row.category}</td>
                        <td><img class="cartImage" src="${row.image}"></td>
                        <td>${row.band}</td>
                        <td>${row.genre}</td>
                        <td>${row.label}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table><br>
        
        <span>Item name: </span><input type="text" name="Item name"><br>
        <span>Price: </span><input type="number" step="0.01" name="price" min="0.01" value="0.01"><br>
        <span>Stock: </span><input type="number" name="stock" min="1" value="1"><br>
        
        <sql:query dataSource="${snapshot}" var="categories">
            SELECT METAL.CATEGORIES.ID, METAL.CATEGORIES.NAME
            FROM METAL.CATEGORIES
        </sql:query>
        <span>Category: </span>
        <select name="category">
            <c:forEach var="row" varStatus="loop" items="${categories.rows}">
                <option value="${row.id}">${row.name}</option>
            </c:forEach>
        </select><br>
        
        <sql:query dataSource="${snapshot}" var="bands">
            SELECT METAL.BANDS.ID, METAL.BANDS.NAME
            FROM METAL.BANDS
        </sql:query>
        <span>Band: </span>
        <select name="band">
            <c:forEach var="row" varStatus="loop" items="${bands.rows}">
                <option value="${row.id}">${row.name}</option>
            </c:forEach>
        </select><br>
        
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
        
        <sql:query dataSource="${snapshot}" var="labels">
            SELECT METAL.LABELS.ID, METAL.LABELS.NAME
            FROM METAL.LABELS
        </sql:query>
        <span>Label: </span>
        <select name="label">
            <c:forEach var="row" varStatus="loop" items="${labels.rows}">
                <option value="${row.id}">${row.name}</option>
            </c:forEach>
        </select><br><br>
            
        <sql:query dataSource="${snapshot}" var="images">
            SELECT METAL.IMAGES.ID, METAL.IMAGES.ADDRESS
            FROM METAL.IMAGES
        </sql:query>
            <span>Image: </span>
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
