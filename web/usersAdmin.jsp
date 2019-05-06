<%-- 
    Document   : usersAdmin
    Created on : Feb 19, 2019, 7:56:41 PM
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
        <%@ include file="./utils/admin.jsp" %>
        <c:choose>
            <c:when test="${currentUserRole == 2}">
                <sql:setDataSource 
                var="snapshot" 
                driver="org.apache.derby.jdbc.ClientDriver"
                url="jdbc:derby://localhost:1527/metal;create=true;"
                user="metal"  
                password="metal"/>
                <sql:query dataSource="${snapshot}" var="users">
                    SELECT METAL.USERS.ID , METAL.USERS.USERNAME, METAL.USERS.PASSWORD, METAL.ROLES.NAME AS ROLE
                    FROM METAL.USERS
                    INNER JOIN  METAL.ROLES ON METAL.USERS.ROLE_ID=METAL.ROLES.ID 
                </sql:query>
                <h1>Admin Users</h1>
                <form action="${pageContext.request.contextPath}/AdminUsersServlet" method="POST"">
                <table border="1">
                    <thead>
                        <tr>
                            <td></td>
                            <td>Username</td>
                            <td>Password</td>
                            <td>Role</td>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="row" varStatus="loop" items="${users.rows}">
                            <tr>
                                <td><input type="checkbox" name="userIdCheckbox" value="${row.id}"></td>
                                <td>${row.username}</td>
                                <td>${row.password}</td>
                                <td>${row.role}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <span>Username: </span><input type="text" name="username"><br>
                <span>Password: </span><input type="text" name="password"><br>

                <sql:query dataSource="${snapshot}" var="roles">
                    SELECT METAL.ROLES.ID, METAL.ROLES.NAME
                    FROM METAL.ROLES
                </sql:query>
                <span>Role: </span>
                <select name="role">
                    <c:forEach var="row" varStatus="loop" items="${roles.rows}">
                        <option value="${row.id}">${row.name}</option>
                    </c:forEach>
                </select>
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
