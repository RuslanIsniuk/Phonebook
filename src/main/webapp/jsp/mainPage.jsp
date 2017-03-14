<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Web Phonebook</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<blockquote><blockquote>
    <p><h3>Hello, <c:out value="${clientName}"></c:out></h3></p>

    <p><h4>Operations</h4></p>
    <p>===============================</p>

    <form method="post" action="${pageContext.request.contextPath}/Servlet">
        <p><h4><input type="radio" value="viewPhonebook" name="actionType" checked="checked"/> Open phonebook</h4></p>
        <p><h4><input type="radio" value="openAddNotePage" name="actionType"/> Add new note</h4></p>
        <input type="hidden" value="${clientID}" name="clientID"/>
        <input type="submit" value="Submit" name="onButton"/>
    </form>

    <p><form action="${pageContext.request.contextPath}/Servlet" method="post">
        <input type="hidden" value="LogOut" name="actionType"/>
        <input type="submit" value="Log out" name="homeButton"/>
    </form></p>
</blockquote></blockquote>
</body>
</html>