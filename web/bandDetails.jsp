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
        <title>Band Details Page</title>
    </head>
    <body>
        <h1>${band}</h1>
        <c:set var = "band" value ="${band}" />
        <sql:setDataSource 
        var="snapshot" 
        driver="org.apache.derby.jdbc.ClientDriver"
        url="jdbc:derby://localhost:1527/metal;create=true;"
        user="metal"  
        password="metal"/>
        <sql:query dataSource="${snapshot}" var="currentBand">
            SELECT METAL.BANDS.NAME, METAL.BANDS.WEBSITE, METAL.GENRES.NAME AS GENRE, METAL.COUNTRIES.NAME AS COUNTRY, METAL.LABELS.NAME AS LABEL, METAL.IMAGES.ADDRESS AS IMAGE
            FROM METAL.BANDS
            INNER JOIN METAL.GENRES ON METAL.BANDS.GENRE_ID=METAL.GENRES.ID
            INNER JOIN METAL.COUNTRIES ON METAL.BANDS.COUNTRY_ID=METAL.COUNTRIES.ID
            INNER JOIN METAL.LABELS ON METAL.BANDS.LABEL_ID=METAL.LABELS.ID
            INNER JOIN METAL.IMAGES ON METAL.BANDS.IMAGE_ID=METAL.IMAGES.ID
            WHERE METAL.BANDS.NAME=?
            <sql:param value = "${band}" />
        </sql:query>
        <c:forEach var="row" varStatus="loop" items="${currentBand.rows}">
            <img src="${row.image}"><br>
            <a href="${row.website}">Visit band Website</a><br>
            <span>Genre: </span><c:out value="${row.genre}"/><br>
            <span>Country of Origin: </span><c:out value="${row.country}"/><br>
            <span>Current Label: </span><form action="${pageContext.request.contextPath}/LabelDetailServlet" method="GET"><input class="submitLink" type="submit" value="${row.label}" name="label"/></form><br>
        </c:forEach>
        <sql:query dataSource="${snapshot}" var="itemsFromBand">
            SELECT METAL.ITEMS.NAME AS ITEM, METAL.BANDS.NAME
            FROM METAL.ITEMS 
            INNER JOIN METAL.BANDS ON METAL.ITEMS.BAND_ID=METAL.BANDS.ID
            WHERE METAL.BANDS.NAME=?
            <sql:param value = "${band}" />
        </sql:query>
            <h3>Available items from this band:</h3><br>
        <c:forEach var="itemRow" varStatus="loop" items="${itemsFromBand.rows}">
            <form action="${pageContext.request.contextPath}/ItemDetailServlet" method="GET"><input class="submitLink" type="submit" value="${itemRow.item}" name="item"/></form><br>
        </c:forEach>
    </body>
</html>
