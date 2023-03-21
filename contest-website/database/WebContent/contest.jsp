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
        .btn {
			display: block;
			width: 50%;
			height: 50px;
			margin: 10px auto;
			text-align: center;
			line-height: 50px;
			font-size: 24px;
			font-weight: bold;
			color: #FFFFFF;
			background-color: #008000;
			border-radius: 5px;
			text-decoration: none;
		}

		.btn:hover {
			background-color: #006400;
			cursor: pointer;
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
            <c:if test="${status == 'opened'}">
            	<button type="submit" class="button">Submit Contest</button>
            </c:if>
        </form>
        <a href="openContest?id=${userID}" class="btn">All open contests</a>
    </div>
</body>
</html>
