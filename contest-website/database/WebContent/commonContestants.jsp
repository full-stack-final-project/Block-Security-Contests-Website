<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contestant Selection</title>
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
            width: 400px;
        }

        h1 {
            font-size: 24px;
            margin-bottom: 20px;
        }

        form {
            margin-top: 20px;
        }

        label {
            display: block;
            margin-bottom: 10px;
        }

        select {
            width: 100%;
            padding: 6px 12px;
            margin-bottom: 10px;
            font-size: 14px;
            font-weight: 400;
            line-height: 1.42857143;
            text-align: left;
            white-space: nowrap;
            vertical-align: middle;
            cursor: pointer;
            background-image: none;
            border: 1px solid #CCC;
            border-radius: 4px;
            color: #555;
        }

        button {
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

        button:hover {
            background-color: #006400;
			cursor: pointer;
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

    <div class="container">
        <h1>Common Contestants Selection</h1>
        <form action="commonContest" method="post">
            <label for="contestant1">Contestant 1:</label>
            <select id="contestant1" name="contestant1">
                <option value="">-- Select Contestant 1 --</option>
                <c:forEach var="contestant" items="${contestants}">
                    <option value="${contestant.getLoginID()}">${contestant.getLoginID()}</option>
                </c:forEach>
            </select>

            <label for="contestant2">Contestant 2:</label>
            <select id="contestant2" name="contestant2">
                <option value="">-- Select Contestant 2 --</option>
                <c:forEach var="contestant" items="${contestants}">
                    <option value="${contestant.getLoginID()}">${contestant.getLoginID()}</option>
                </c:forEach>
            </select>

            <button type="submit">Submit</button><br><br>
            <a href="newRoot.jsp" class="btn">Root Page</a>
        </form>
    </div>
</body>
</html>
