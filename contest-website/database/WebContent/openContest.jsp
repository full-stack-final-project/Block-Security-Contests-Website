<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>All Contests</title>
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
	</style>
		<script>
		// Search function
		function searchContests() {
			var input, filter, contestList, contests, contestName, i;
			input = document.getElementById("search-box");
			filter = input.value.toUpperCase();
			contestList = document.getElementsByClassName("contest-list")[0];
			contests = contestList.getElementsByClassName("contest-item");
			for (i = 0; i < contests.length; i++) {
				contestName = contests[i].getElementsByClassName("contest-name")[0];
				if (contestName.innerHTML.toUpperCase().indexOf(filter) > -1) {
					contests[i].style.display = "";
				} else {
					contests[i].style.display = "none";
				}
			}
		}
		</script>
</head>
<body>
	<div class="search-container">
		<input type="text" id="search-box" class="search-box" placeholder="Search contests">
		<button onclick="searchContests()" class="search-button">Search</button>
	</div>
	
	<div class="contest-list">
	<input type="hidden" id="contestantID" name="contestantID" value=${contestantID}>
		<div class="contest-header">All Contests</div>
		<c:forEach items="${contests}" var="contest">
			<div class="contest-item">
				<div class="contest-name">${contest.title}</div>
				<div class="contest-dates">${contest.getBeginTime()} - ${contest.getEndTime()}</div>
				<a href="contestDetails?id=${contest.getContestID()}&userID=${contestantID}" class="contest-link">Details</a>
				
			</div>
		</c:forEach>
	</div>
</body>	
</html>
	
