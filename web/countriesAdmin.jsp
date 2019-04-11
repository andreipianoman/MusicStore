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
        <sql:query dataSource="${snapshot}" var="countries">
            SELECT METAL.COUNTRIES.ID , METAL.COUNTRIES.NAME
            FROM METAL.COUNTRIES 
        </sql:query>
        <h1>Admin Countries</h1>
        <form action="${pageContext.request.contextPath}/AdminCountriesServlet" method="POST"">
        <table border="1">
            <thead>
                <tr>
                    <td></td>
                    <td>Name</td>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="row" varStatus="loop" items="${countries.rows}">
                    <tr>
                        <td><input type="checkbox" name="countryIdCheckbox" value="${row.id}"></td>
                        <td>${row.name}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <span>Country name: </span><input type="text" name="Country name"><br>
        
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
