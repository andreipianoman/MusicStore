<%-- 
    Document   : labels
    Created on : Feb 19, 2019, 7:34:24 PM
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
        <title>Labels page</title>
    </head>
    <body>
        <%@ include file="./utils/menu.jsp" %>
        <h1>Labels Page</h1>
        <sql:setDataSource 
        var="snapshot" 
        driver="org.apache.derby.jdbc.ClientDriver"
        url="jdbc:derby://localhost:1527/metal;create=true;"
        user="metal"  
        password="metal"/>
        <sql:query dataSource="${snapshot}" var="labels">
            SELECT METAL.LABELS.NAME, METAL.LABELS.WEBSITE, METAL.COUNTRIES.NAME AS COUNTRY_NAME
            FROM METAL.LABELS
            INNER JOIN COUNTRIES ON METAL.LABELS.COUNTRY_ID=COUNTRIES.ID
        </sql:query>
            <table border="1">
                <thead>
                    <tr>
                        <td>Name</td>
                        <td>Website</td>
                        <td>Country</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="row" varStatus="loop" items="${labels.rows}">
                        <tr>
                            <td><form action="${pageContext.request.contextPath}/LabelDetailServlet" method="GET"><input class="submitLink" type="submit" value="${row.name}" name="label"/></form></td>
                            <td><c:out value="${row.website}"/></td>
                            <td><c:out value="${row.country_name}"/></td>
                        </tr>
                    </c:forEach> 
                </tbody>
            </table>
    </body>
</html>
