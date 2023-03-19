<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Root page</title>
 <style>
        /* Styles for the page */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        a {
			color: #337ab7;
			text-decoration: none;
		}
        .title {
            font-size: 32px;
            font-weight: bold;
            margin: 0 0 20px;
        }
        .info {
            font-size: 18px;
            margin: 0 0 20px;
        }
        .description {
            font-size: 16px;
            margin: 0 0 20px;
        }
       input[type="submit"] {
			background-color: #4CAF50;
			border: none;
			color: white;
			padding: 12px 20px;
			text-decoration: none;
			margin-top: 20px;
			margin-bottom: 20px;
			cursor: pointer;
			border-radius: 4px;
			width: 100%;
			font-size: 16px;
		}
		input[type="submit"]:hover {
			background-color: #45a049;
		}
    </style>
</head>
<body>

<div align = "center">
	
	<form action = "initialize">
		<input type = "submit" id = button name = button value = "Initialize the Database"/>
	</form>
	<a href="index.jsp"target ="_self" > logout</a><br><br> 

<%-- <h1>List all users</h1>
    <div align="center">
        <table border="1" cellpadding="6">
            <caption><h2>List of Users</h2></caption>
            <tr>
                <th>Email</th>
                <th>First name</th>
                <th>Last name</th>
                <th>Adress</th>
                <th>Password</th>
                <th>Birthday</th>
                <th>cash_bal($)</th>
                <th>PPS_bal</th>
            </tr>
            <c:forEach var="users" items="${listUser}">
                <tr style="text-align:center">
                    <td><c:out value="${users.email}" /></td>
                    <td><c:out value="${users.firstName}" /></td>
                    <td><c:out value="${users.lastName}" /></td>
                    <td><c:out value= "${users.adress_street_num} ${users.adress_street} ${users.adress_city} ${users.adress_state} ${users.adress_zip_code}" /></td>
                    <td><c:out value="${users.password}" /></td>
                    <td><c:out value="${users.birthday}" /></td>
                    <td><c:out value="${users.cash_bal}"/></td>
                    <td><c:out value="${users.PPS_bal}" /></td>
            </c:forEach>
        </table>
	</div>
	</div> --%>

</body>
</html>