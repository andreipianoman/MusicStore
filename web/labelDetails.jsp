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
        <title>JSP Page</title>
    </head>
    <body>
        <h1>${label}</h1>
        <c:set var = "label" value ="${label}" />
        <sql:setDataSource 
        var="snapshot" 
        driver="org.apache.derby.jdbc.ClientDriver"
        url="jdbc:derby://localhost:1527/metal;create=true;"
        user="metal"  
        password="metal"/>
        <sql:query dataSource="${snapshot}" var="currentLabel">
            SELECT METAL.LABELS.NAME, METAL.LABELS.WEBSITE, METAL.IMAGES.ADDRESS AS IMAGE, METAL.COUNTRIES.NAME AS COUNTRY
            FROM METAL.LABELS
            INNER JOIN METAL.IMAGES ON METAL.LABELS.IMAGE_ID=METAL.IMAGES.ID
            INNER JOIN METAL.COUNTRIES ON METAL.LABELS.COUNTRY_ID=METAL.COUNTRIES.ID
            WHERE METAL.LABELS.NAME=?
            <sql:param value = "${label}" />
        </sql:query>
        <c:forEach var="row" varStatus="loop" items="${currentLabel.rows}">
            <img src="${row.image}"><br>
            <a href="${row.website}">Visit Label Website</a><br>
            <span>Country: </span><c:out value="${row.country}"/><br>
        </c:forEach>
        <sql:query dataSource="${snapshot}" var="bandsFromLabel">
            SELECT METAL.BANDS.NAME AS BAND, METAL.LABELS.NAME
            FROM METAL.BANDS 
            INNER JOIN METAL.LABELS ON METAL.BANDS.LABEL_ID=METAL.LABELS.ID
            WHERE METAL.LABELS.NAME=?
            <sql:param value = "${label}" />
        </sql:query>
            <h3>Bands from this label:</h3><br>
        <c:forEach var="bandRow" varStatus="loop" items="${bandsFromLabel.rows}">
            <form action="${pageContext.request.contextPath}/BandDetailServlet" method="GET"><input class="submitLink" type="submit" value="${bandRow.band}" name="band"/></form><br>
        </c:forEach>
            
        <sql:query dataSource="${snapshot}" var="itemsFromLabel">
            SELECT METAL.ITEMS.NAME AS ITEM, METAL.LABELS.NAME
            FROM METAL.ITEMS 
            INNER JOIN METAL.LABELS ON METAL.ITEMS.LABEL_ID=METAL.LABELS.ID
            WHERE METAL.LABELS.NAME=?
            <sql:param value = "${label}" />
        </sql:query>
            <h3>Available items from this label:</h3><br>
        <c:forEach var="itemRow" varStatus="loop" items="${itemsFromLabel.rows}">
            <form action="${pageContext.request.contextPath}/ItemDetailServlet" method="GET"><input class="submitLink" type="submit" value="${itemRow.item}" name="item"/></form><br>
        </c:forEach>
    </body>
</html>
