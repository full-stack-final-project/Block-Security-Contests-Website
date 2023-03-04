<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>All Contests</title>
	<style>
		/* Add your CSS style here */
		body {
			font-family: Arial, sans-serif;
			background-color: #F9F9F9;
			margin: 0;
			padding: 0;
		}

		.container {
			width: 80%;
			margin: auto;
			padding: 20px;
			background-color: #FFFFFF;
			box-shadow: 0px 0px 10px #888888;
		}

		h1 {
			text-align: center;
			margin-top: 30px;
		}

		table {
			border-collapse: collapse;
			margin: 20px 0;
			width: 100%;
		}

		th, td {
			padding: 8px;
			text-align: center;
			border-bottom: 1px solid #ddd;
		}

		tr:hover {
			background-color: #f5f5f5;
		}

		input[type=text] {
			width: 100%;
			padding: 12px 20px;
			margin: 8px 0;
			box-sizing: border-box;
			border: 2px solid #ccc;
			border-radius: 4px;
			background-color: #f8f8f8;
			font-size: 16px;
			font-weight: bold;
			color: #555555;
		}

		input[type=submit] {
			background-color: #4CAF50;
			color: white;
			padding: 12px 20px;
			border: none;
			border-radius: 4px;
			cursor: pointer;
			width: 100%;
			font-size: 16px;
			font-weight: bold;
			margin-top: 10px;
		}

		input[type=submit]:hover {
			background-color: #45a049;
		}
	</style>
</head>
<body>
	<div class="container">
		<h1>All Contests</h1>
		<form action="${pageContext.request.contextPath}/contest/search" method="GET">
			<input type="text" name="q" placeholder="Search by Contest Name">
			<input type="submit" value="Search">
		</form>
		<table>
			<tr>
				<th>Contest Name</th>
				<th>Begin Time</th>
				<th>End Time</th>
				<th>Details</th>
			</tr>
			<c:forEach var="contest" items="${contests}">
				<tr>
					<td>${contest.name}</td>
					<td>${contest.beginTime}</td>
					<td>${contest.endTime}</td>
					<td><a href="${pageContext.request.contextPath}/contest/details?contestId=${contest.id}">Details</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
