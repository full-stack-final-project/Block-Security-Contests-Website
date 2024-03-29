<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Contestant Page</title>
	<style>
		body {
			background-color: #f2f2f2;
			font-family: Arial, sans-serif;
		}
		
		h1 {
			text-align: center;
			margin-top: 50px;
			margin-bottom: 30px;
		}
		
		.container {
			background-color: white;
			padding: 20px;
			margin: 0 auto;
			max-width: 800px;
			box-shadow: 0 0 5px rgba(0,0,0,0.1);
		}
		
		table {
			border-collapse: collapse;
			width: 100%;
			margin-top: 30px;
		}
		
		th, td {
			padding: 10px;
			text-align: left;
			border-bottom: 1px solid #ddd;
		}
		
		tr:hover {
			background-color: #f5f5f5;
		}
		
		a {
			color: #337ab7;
			text-decoration: none;
		}
		
		.search-container {
			margin-top: 30px;
			display: flex;
			align-items: center;
		}
		
		.search-box {
			flex: 1;
			padding: 10px;
			font-size: 16px;
			border: none;
			border-radius: 5px;
			background-color: #f2f2f2;
			margin-right: 10px;
		}
		
		.search-button {
			padding: 10px;
			background-color: #4CAF50;
			color: white;
			border: none;
			border-radius: 5px;
			cursor: pointer;
		}
		
		.search-button:hover {
			background-color: #3e8e41;
		}
	</style>
</head>
<body>
	<div class="container">
		<h1>Contestant Page</h1>
		
		<p><strong>Wallet Address:</strong> ${walletAddress}</p>
		<p><strong>Balance:</strong> ${balance}</p>
		<p><strong>Contests you participated </strong></p>
		<a href="openContest?id=${walletAddress}">All open contests</a>
		<table>
			<thead>
				<tr>
					<th>Contest Name</th>
					<th>Begin Time</th>
					<th>End Time</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="contest" items="${contests}">
					<tr>
						<td>${contest.getTitle()}</td>
						<td>${contest.getBeginTime()}</td>
						<td>${contest.getEndTime()}</td>
						<td><a href="contestDetails?id=${contest.getContestID()}&userID=${walletAddress}">Details</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>
