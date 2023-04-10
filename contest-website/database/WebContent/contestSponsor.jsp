<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contest - <%= request.getAttribute("contestName") %></title>
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
        .button {
            display: inline-block;
            background-color: #4CAF50;
            color: #FFF;
            font-size: 18px;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 4px;
            margin: 0 0 20px;
        }
        .button:hover {
            background-color: #3e8e41;
        }
        .contest-list {
			margin: 0 auto;
			width: 60%;
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
    </style>
</head>
<body>
    <div class="container">
        <div class="title">${contestName}</div>
        <div class="info">Begin time: ${beginTime}</div>
        <div class="info">End time: ${endTime}</div>
        <div class="description">${requirements}</div>
       <form action="submitpage" method="post">
            <input type="hidden" name="userID" value="${userID}" />
            <input type="hidden" name="contestID" value="${contestID}" />
            
        </form>

        <c:if test="${status == 'closed'}">
        	<a href="distributeSubmission?id=${contestID}&sponsorID=${userID}">Distribute submissions to judges</a><br>
        	
        </c:if>
        
        <c:if test="${completed == 'True'}">
        <a href="distributeBonus?id=${contestID}&sponsorID=${userID}">Distribute bonus</a>
        </c:if>
        
        
        <h2>Assigned Judges:</h2>
		<ul class="judge-list">
			<c:forEach items="${judgeList}" var="judge">

				<c:if test="${status == 'past'}"><li><a href="reviewJudge?id=${judge.getId()}&userID=${userID}">Click to review the judge ${judge.getLoginID()}</a></li></c:if>
				<c:if test="${status != 'past'}"><li><a href="judgeprofile?judgeID=${judge.getId()}&sponsorID=${userID}">Click to see the judge ${judge.getLoginID()}'s profile</a></li></c:if>

			</c:forEach>
		</ul>
		
    </div>
    <div class="contest-list">
	
		<div class="contest-header">Contestants of this contest</div>
		
		<table>
			<thead>
				<tr>
					<th>User ID</th>
					<th>Reward</th>
					
				</tr>
			</thead>
			<tbody>
				<c:forEach var="contestant" items="${contestants}">
					<tr>
						<td>${contestant.getLoginID()}</td>
						<td>${contestant.getRewardBalance()}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>
