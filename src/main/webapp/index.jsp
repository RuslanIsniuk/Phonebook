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
        <h3>Login Page</h3>

        <h4>Please enter your login and password:</h4>
        <form action="/Servlet" method="post">
            <p>Username: <input type="text" name="username"/></p>
            <p>Password: <input type="password" name="password"/><br/></p>
            <input type="hidden" value="authentication" name="actionType"/>
            <input type="submit" value="Login in"/>
        </form>

        <p>Or sign up, if you don't have account. </p>
        <p>
        <form action="/Servlet" method="post">
            <input type="hidden" value="openRegistrationPage" name="actionType"/>
            <input type="submit" value="Sign up"/>
        </form>
        </p>
        <p>
        <h3><font color="#dc143c"><c:out value="${errorMessage}"></c:out></font></h3></p>
    </blockquote>
</blockquote>
</body>
</html>