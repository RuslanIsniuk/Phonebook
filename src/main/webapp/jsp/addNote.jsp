<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Web Phonebook</title>
</head>
<body>
<blockquote>
    <blockquote>


        <table width="30%" cellspacing="0" cellpadding="4">
            <form method="post" action="${pageContext.request.contextPath}/Servlet">
                <tr>
                    <td colspan="2" align="center"><h3>Create New Note:</h3></td>
                </tr>

                <tr>
                    <td colspan="2" align="center"><h4>Please fill next fields:</h4></td>
                </tr>

                <tr>
                    <td align="right" width="100">First Name:</td>
                    <td><input type="text" size="30" maxlength="45" name="noteFirstName"
                               placeholder="min length: 4 symbols"/></td>
                </tr>

                <tr>
                    <td align="right">Second Name:</td>
                    <td><input type="text" size="30" maxlength="45" name="noteSecondName"
                               placeholder="min length: 4 symbols"/></td>
                </tr>

                <tr>
                    <td align="right">Additional Name:</td>
                    <td><input type="text" size="30" maxlength="45" name="noteAdditionalName"
                               placeholder="min length: 4 symbols"/></td>
                </tr>

                <tr>
                    <td align="right">Mobile Number:</td>
                    <td><input type="text" size="30" maxlength="45" name="noteMobileNum"
                               placeholder="Format: +380XXXXXXXXX "/></td>
                </tr>

                <tr>
                    <td align="right">Home Number:</td>
                    <td><input type="text" size="30" maxlength="45" name="noteHomeNum"
                               placeholder="Format: +38044XXXXXXX"/></td>
                </tr>

                <tr>
                    <td align="right">Location:</td>
                    <td><input type="text" size="30" maxlength="45" name="noteLocation"/></td>
                </tr>

                <tr>
                    <td align="right">Email:</td>
                    <td><input type="text" size="30" maxlength="45" name="noteEmail"/>
                    </td>
                </tr>

                <tr>
                    <td align="right">
                        <input type="hidden" value="addNoteConfirm" name="actionType"/>
                        <input type="hidden" value="${clientID}" name="clientID"/>
                        <input type="submit" cellpadding="4" value="Submit" name="chooseButton" align="center"/>
                    </td>
                </tr>

            </form>

            <tr>
                <td align="right">
                    <form method="post" action="${pageContext.request.contextPath}/Servlet">
                        <input type="hidden" value="${clientID}" name="clientID"/>
                        <input type="hidden" value="returnToMainPage" name="actionType"/>
                        <input type="submit" value="Return to homepage" name="chooseButton" align="center"/>
                    </form>
                </td>
            </tr>

            <tr>
                <td colspan="2" align="center"><h3><font color="#dc143c"><c:out value="${errorMessage}"></c:out></font></h3></td>
            </tr>
        </table>


    </blockquote>
</blockquote>
</body>
</html>