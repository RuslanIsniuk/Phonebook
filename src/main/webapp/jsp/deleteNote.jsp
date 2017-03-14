<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Web Phonebook</title>
</head>
<body>
<h4>Do You really want to delete note with: <c:out value="${phoneNote.firstName}"></c:out> <c:out
        value="${phoneNote.secondName}"></c:out>?</h4>
<p>
<form method="post" action="${pageContext.request.contextPath}/Servlet">
    <input type="hidden" value="${clientID}" name="clientID"/>
    <input type="hidden" value="${phoneNote.noteID}" name="noteID"/>
    <input type="hidden" value="deleteNoteConfirm" name="actionType"/>
    <input type="submit" value="Delete" name="chooseButton" align="center"/>
</form>
</p>
<p>
<form method="post" action="${pageContext.request.contextPath}/Servlet">
    <input type="hidden" value="${clientID}" name="clientID"/>
    <input type="hidden" value="returnToMainPage" name="actionType"/>
    <input type="submit" value="Return to homepage" name="chooseButton" align="center"/>
</form>
</p>
</body>
</html>
