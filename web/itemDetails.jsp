<%-- 
    Document   : labelDetails
    Created on : Feb 22, 2019, 3:47:15 PM
    Author     : Turbotwins
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./css/metalStore.css">
        <title>Item Details Page</title>
    </head>
    <body>
        <h1>${item}</h1>
        <c:set var = "item" value ="${item}" />
        <sql:setDataSource 
        var="snapshot" 
        driver="org.apache.derby.jdbc.ClientDriver"
        url="jdbc:derby://localhost:1527/metal;create=true;"
        user="metal"  
        password="metal"/>
        <sql:query dataSource="${snapshot}" var="currentItem">
            SELECT METAL.ITEMS.NAME, METAL.ITEMS.PRICE, METAL.ITEMS.STOCK, METAL.CATEGORIES.NAME AS CATEGORY, METAL.IMAGES.ADDRESS AS IMAGE,
            METAL.BANDS.NAME AS BAND, METAL.GENRES.NAME AS GENRE, METAL.LABELS.NAME AS LABEL
            FROM METAL.ITEMS
            INNER JOIN METAL.CATEGORIES ON METAL.ITEMS.CATEGORY_ID=METAL.CATEGORIES.ID
            INNER JOIN METAL.IMAGES ON METAL.ITEMS.IMAGE_ID=METAL.IMAGES.ID
            INNER JOIN METAL.BANDS ON METAL.ITEMS.BAND_ID=METAL.BANDS.ID
            INNER JOIN METAL.GENRES ON METAL.ITEMS.GENRE_ID=METAL.GENRES.ID
            INNER JOIN METAL.LABELS ON METAL.ITEMS.LABEL_ID=METAL.LABELS.ID
            WHERE METAL.ITEMS.NAME=?
            <sql:param value = "${item}" />
        </sql:query>
        <c:forEach var="row" varStatus="loop" items="${currentItem.rows}">
            <img src="${row.image}"><br>
            <span>Item type: </span><c:out value="${row.category}"/><br>
            <span>Price: </span><c:out value="${row.price}"/><br>
            <span>Band: </span><form action="${pageContext.request.contextPath}/BandDetailServlet" method="GET"><input class="submitLink" type="submit" value="${row.band}" name="band"/></form><br><br>
            <span>Label: </span><form action="${pageContext.request.contextPath}/LabelDetailServlet" method="GET"><input class="submitLink" type="submit" value="${row.label}" name="label"/></form><br><br>
        </c:forEach>
    </body>
</html>
