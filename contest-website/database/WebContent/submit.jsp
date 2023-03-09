<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Submit</title>
	<style>
		body {
			font-family: Arial, sans-serif;
			background-color: #f2f2f2;
			padding: 20px;
		}
		h1 {
			font-size: 36px;
			margin-bottom: 20px;
		}
		form {
			background-color: #fff;
			border: 1px solid #ccc;
			border-radius: 5px;
			padding: 20px;
			max-width: 800px;
			margin: 0 auto;
		}
		label {
			display: block;
			font-size: 18px;
			margin-bottom: 10px;
		}
		textarea {
			width: 95%;
			height: 300px;
			padding: 10px;
			font-size: 16px;
			border-radius: 5px;
			border: 1px solid #ccc;
			margin-bottom: 20px;
		}
		input[type="submit"] {
			background-color: #4CAF50;
			color: white;
			padding: 10px 20px;
			border: none;
			border-radius: 5px;
			font-size: 18px;
			cursor: pointer;
		}
		input[type="submit"]:hover {
			background-color: #45a049;
		}
	</style>
</head>
<body>
	
	<form method="post" action="submitContest">
		<label for="context">Please submit your context here for this contest ${contestName} :</label>
		<textarea id="context" name="context"></textarea>
		<input type="hidden" id="userID" name="userID" value="${userID}" />
		<input type="hidden" id="contestID" name="contestID" value="${contestID}" />
		<input type="submit" value="Submit" />
	</form>
</body>
</html>
