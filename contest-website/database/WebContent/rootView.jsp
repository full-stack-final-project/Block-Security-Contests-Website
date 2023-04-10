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



</body>
</html>