<%-- 
    Document   : searchResults
    Created on : Apr 17, 2019, 1:40:40 PM
    Author     : Turbotwins
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@ include file="./utils/menu.jsp" %>
        <sql:setDataSource 
        var="snapshot" 
        driver="org.apache.derby.jdbc.ClientDriver"
        url="jdbc:derby://localhost:1527/metal;create=true;"
        user="metal"  
        password="metal"/>
        <c:choose>
            <c:when test="${type == 'BANDS'}">
                <sql:query dataSource="${snapshot}" var="results">
                    SELECT METAL.BANDS.NAME
                    FROM METAL.BANDS
                    WHERE LOWER(METAL.BANDS.NAME) LIKE LOWER('%' || ? || '%')
                    <sql:param value = "${search}" />
                </sql:query>
                <c:forEach var="row" varStatus="loop" items="${results.rows}">
                    <form action="${pageContext.request.contextPath}/BandDetailServlet" method="GET"><input class="submitLink" type="submit" value="${row.name}" name="band"/></form>
                </c:forEach>
                <h1>${search}</h1>
                <h1>${type}</h1>
                <h1>BAND</h1>
            </c:when>
                    
            <c:when test="${type == 'ITEMS'}">
                <sql:query dataSource="${snapshot}" var="results">
                    SELECT METAL.ITEMS.NAME
                    FROM METAL.ITEMS
                    WHERE LOWER(METAL.ITEMS.NAME) LIKE LOWER('%' || ? || '%')
                    <sql:param value = "${search}" />
                </sql:query>
                <c:forEach var="row" varStatus="loop" items="${results.rows}">
                    <form action="${pageContext.request.contextPath}/ItemDetailServlet" method="GET"><input class="submitLink" type="submit" value="${row.name}" name="item"/></form>
                </c:forEach>
                <h1>${search}</h1>
                <h1>${type}</h1>
                <h1>ITEM</h1>
            </c:when>
                    
            <c:when test="${type == 'LABELS'}">
                <sql:query dataSource="${snapshot}" var="results">
                    SELECT METAL.LABELS.NAME
                    FROM METAL.LABELS
                    WHERE LOWER(METAL.LABELS.NAME) LIKE LOWER('%' || ? || '%')
                    <sql:param value = "${search}" />
                </sql:query>
                <c:forEach var="row" varStatus="loop" items="${results.rows}">
                    <form action="${pageContext.request.contextPath}/LabelDetailServlet" method="GET"><input class="submitLink" type="submit" value="${row.name}" name="label"/></form>
                </c:forEach>
                <h1>${search}</h1>
                <h1>${type}</h1>
                <h1>LABEL</h1>
            </c:when>
        </c:choose>
        
    </body>
</html>
