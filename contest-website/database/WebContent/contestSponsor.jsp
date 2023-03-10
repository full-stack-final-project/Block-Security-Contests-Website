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
        	<a href="distributeSubmission?id=${contestID}">Distribute submissions to judges</a><br>
        	<a href="distributeBonus?id=${contestID}">Distribute bonus</a>
        </c:if>
        

    </div>
</body>
</html>
