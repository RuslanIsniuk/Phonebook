<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Web Phonebook</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<blockquote>
    <blockquote>
        <p><h4>Phonebook:</h4></p>

        <table border="1">
            <tr>
                <th>First Name</th>
                <th>Second Name</th>
                <th>Additional Name</th>
                <th>Mobile Number</th>
                <th>Home Number</th>
                <th>Location</th>
                <th>Email</th>
                <th></th>
            </tr>
            <c:if test="${phoneNoteList.size()>0}">
            <c:forEach var="i" begin="0" end="${phoneNoteList.size()-1}">
                <tr>
                    <td><c:out value="${phoneNoteList[i].firstName}"></c:out></td>
                    <td><c:out value="${phoneNoteList[i].secondName}"></c:out></td>
                    <td><c:out value="${phoneNoteList[i].additionalName}"></c:out></td>
                    <td><c:out value="${phoneNoteList[i].mobileNumber}"></c:out></td>
                    <td><c:out value="${phoneNoteList[i].homeNumber}"></c:out></td>
                    <td><c:out value="${phoneNoteList[i].location}"></c:out></td>
                    <td><c:out value="${phoneNoteList[i].email}"></c:out></td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/Servlet">
                            <input type="hidden" value="${phoneNoteList[i].noteID}" name="noteID"/>
                            <input type="hidden" value="${clientID}" name="clientID"/>
                            <input type="hidden" value="openEditNotePage" name="actionType"/>
                            <input type="submit" value="  Edit  " name="chooseButton" align="center"/>
                        </form>
                   
                        <form method="post" action="${pageContext.request.contextPath}/Servlet">
                            <input type="hidden" value="${phoneNoteList[i].noteID}" name="noteID"/>
                            <input type="hidden" value="${clientID}" name="clientID"/>
                            <input type="hidden" value="openDeleteNotePage" name="actionType"/>
                            <input type="submit" value="Delete" name="chooseButton" align="center"/>
                        </form>
                    </td>

                </tr>
            </c:forEach></c:if>
        </table>

        <h3><font color="#dc143c"><c:out value="${errorMessage}"></c:out></font></h3></p>

        <form method="post" action="${pageContext.request.contextPath}/Servlet">
            <p>Find by :</p>
            <p><h4><input type="radio" value="filterByFirstName" name="actionType"/> first name</h4>
            <h4><input type="radio" value="filterBySecondName" name="actionType"/> second name</h4>
            <h4><input type="radio" value="filterByMobNum" name="actionType"/> mobile number</h4></p>
            <input type="text" size="30" maxlength="40" name="subString">
            <input type="hidden" value="${clientID}" name="clientID"/>
            <input type="submit" value="Find" name="homeButton"/>
        </form>

        <form method="post" action="${pageContext.request.contextPath}/Servlet">
            <input type="hidden" value="${clientID}" name="clientID"/>
            <input type="hidden" value="returnToMainPage" name="actionType"/>
            <input type="submit" value="Return to homepage" name="chooseButton" align="center"/>
        </form>

    </blockquote>
</blockquote>
</body>
</html>
