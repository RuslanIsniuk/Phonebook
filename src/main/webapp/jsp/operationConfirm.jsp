<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Web Phonebook</title>
</head>
<body>
<table width="30%" cellspacing="0" cellpadding="4">
    <tr>
        <td align="right" width="100">
            <h3><font color="#dc143c"><c:out value="${errorMessage}"></c:out></font></h3></p>
            <h3><font color="#228b22"><c:out value="${infoMessage}"></c:out></font></h3></p>
        </td>
    </tr>

    <tr>
        <td align="right" width="100">
            <form method="post" action="/Servlet">
                <input type="hidden" value="${clientID}" name="clientID"/>
                <input type="hidden" value="returnToMainPage" name="actionType"/>
                <input type="submit" value="Return to homepage" name="chooseButton" align="center"/>
            </form>
        </td>
    </tr>
</table>

</body>
</html>
