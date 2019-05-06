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
        <%@ include file="./utils/admin.jsp" %>
        <c:choose>
            <c:when test="${currentUserRole == 2}">
                <sql:setDataSource 
                var="snapshot" 
                driver="org.apache.derby.jdbc.ClientDriver"
                url="jdbc:derby://localhost:1527/metal;create=true;"
                user="metal"  
                password="metal"/>
                <sql:query dataSource="${snapshot}" var="images">
                    SELECT METAL.IMAGES.ID, METAL.IMAGES.NAME, METAL.IMAGES.ADDRESS
                    FROM METAL.IMAGES
                </sql:query>
                <h1>Admin Images</h1>
                <form action="${pageContext.request.contextPath}/AdminImagesServlet" method="POST"">
                <table border="1">
                    <tbody>
                        <c:forEach var="row" varStatus="loop" items="${images.rows}">
                            <tr>
                                <td><input type="checkbox" name="imageIdCheckbox" value="${row.id}"></td>
                                <td>${row.name}</td>
                                <td><img class="cartImage" src="${row.address}"></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table><br>

                <span>Image Address: </span><input type="text" name="Image address"><br>
                <span>Image Name: </span><input type="text" name="Image name"><br>

                <br>
                <input type="submit" value="insert" name="insert">
                <br>
                <input type="submit" value="update" name="update">
                <br>
                <input type="submit" value="delete" name="delete">
                </form>
            </c:when>
        </c:choose>
    </body>
</html>
