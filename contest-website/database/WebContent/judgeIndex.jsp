<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Judge Index</title>
	<style>
		/* CSS styles */
		body {
			font-family: Arial, sans-serif;
			font-size: 14px;
			background-color: #F2F2F2;
		}
		.container {
			margin: 20px auto;
			padding: 20px;
			background-color: #FFF;
			border: 1px solid #CCC;
			border-radius: 5px;
			width: 800px;
		}
		h1 {
			font-size: 24px;
			margin-bottom: 20px;
		}
		table {
			border-collapse: collapse;
			width: 100%;
		}
		th, td {
			padding: 8px;
			text-align: left;
			border-bottom: 1px solid #CCC;
		}
		th {
			background-color: #EEE;
		}
		a.button {
			display: inline-block;
			padding: 6px 12px;
			margin-bottom: 0;
			font-size: 14px;
			font-weight: 400;
			line-height: 1.42857143;
			text-align: center;
			white-space: nowrap;
			vertical-align: middle;
			cursor: pointer;
			background-image: none;
			border: 1px solid #337ab7;
			border-radius: 4px;
			color: #FFF;
			background-color: #337ab7;
			text-decoration: none;
		}
		a.button:hover {
			background-color: #286090;
			border-color: #204d74;
		}
	</style>
</head>
<body>
	<div class="container">
		<h1>Judge Index</h1>
		<p>Wallet Address: ${ walletAddress }</p>
		<p>Balance: ${ balance }</p>
		<table>
			<thead>
				<tr>
					<th>Contest Name</th>
					<th>Begin time</th>
					<th>End time</th>
					<th>Status</th>
					<th>Score</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="contest" items="${contests}">
					<tr>
						<td><a href="contestDetailsJudge?id=${contest.getContestID()}&judgeID=${userID}">${contest.getTitle() }</a></td>
						<td>${ contest.getBeginTime()}</td>
						<td>${ contest.getEndTime()}</td>
						<td>${contest.getStatus() }</td>
						<c:if test="${contest.status == 'closed'}">
						<td><a href="contestDetailsJudge?id=${contest.getContestID()}&judgeID=${walletAddress}"}">Score</a></td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>
