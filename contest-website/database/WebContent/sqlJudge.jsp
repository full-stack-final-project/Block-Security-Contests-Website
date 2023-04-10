<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${queryName}</title>
	<style>
		/* Style for the search bar */
		.search-container {
			margin-top: 20px;
			margin-bottom: 20px;
			display: flex;
			justify-content: center;
		}
		
		.search-box {
			padding: 10px;
			border: 1px solid #ccc;
			border-radius: 4px;
			margin-right: 10px;
			width: 300px;
		}
		
		.search-button {
			background-color: #4CAF50;
			color: white;
			padding: 10px;
			border: none;
			border-radius: 4px;
			cursor: pointer;
		}
		
		/* Style for the contest list */
		.contest-list {
			margin: 0 auto;
			width: 80%;
			padding: 20px;
			border: 1px solid #ccc;
			border-radius: 4px;
		}
		
		.contest-header {
			font-weight: bold;
			font-size: 18px;
			margin-bottom: 10px;
		}
		
		.contest-item {
			padding: 10px;
			border-bottom: 1px solid #ccc;
			display: flex;
			justify-content: space-between;
			align-items: center;
		}
		
		.contest-name {
			font-size: 16px;
		}
		
		.contest-dates {
			font-size: 14px;
			color: #666;
		}
		
		.contest-link {
			font-size: 14px;
			color: #4CAF50;
			text-decoration: none;
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
		.btn {
		background-color: #4CAF50;
		color: white;
		padding: 10px 20px;
		border: none;
		border-radius: 5px;
		cursor: pointer;
	}

	.btn:hover {
		background-color: #3e8e41;
	}
	</style>
</head>
<body>
	
	
	<div class="contest-list">
	
		<div class="contest-header">${queryName}</div>
		
		<table>
			<thead>
				<tr>
					<th>User ID</th>
					<th>Reward</th>
					<th>Avg review Score</th>
					
				</tr>
			</thead>
			<tbody>
				<c:forEach var="judge" items="${judges}">
					<tr>
						<td>${judge.getLoginID()}</td>
						<td>${judge.getRewardBalance()}</td>
						<td>${judge.getAvgScore() }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table><br><br>
		<a href="newRoot.jsp" class="btn">Root Page</a>
		
	</div>
</body>	
</html>
	
