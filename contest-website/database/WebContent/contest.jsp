<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    </style>
</head>
<body>
    <div class="container">
        <div class="title"><%= request.getAttribute("contestName") %></div>
        <div class="info">Begin time: <%= request.getAttribute("beginTime") %></div>
        <div class="info">End time: <%= request.getAttribute("endTime") %></div>
        <div class="description"><%= request.getAttribute("requirements") %></div>
       <form action="submit" method="post">
            <input type="hidden" name="userID" value="${userID}" />
            <input type="hidden" name="contestID" value="${contest.contestID}" />
            <button type="submit" class="button">Submit Contest</button>
        </form>
    </div>
</body>
</html>
