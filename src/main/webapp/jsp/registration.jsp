<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Web Phonebook</title>
</head>
<body>
<blockquote>
    <blockquote>
        <h3>Registration Page</h3>

        <form method="post" action="/Servlet">
            <p>Please enter login:
            <p><input type="text" size="20" maxlength="30" name="username"></p></p>
            <p>Please enter password:
            <p><input type="password" size="20" maxlength="30" name="password"></p></p>
            <p>Please enter your full name:
            <p><input type="text" size="20" maxlength="45" name="clientFullName"></p></p>
            <input type="hidden" value="registration" name="actionType"/>
            <input type="submit" value="Confirm"/>
        </form>

        <p>
        <h3><font color="#dc143c"><c:out value="${errorMessage}"></c:out></font></h3></p>
    </blockquote>
</blockquote>
</body>
</html>