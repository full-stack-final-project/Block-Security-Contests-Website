<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sponsor Index</title>
<style>
	body {
		font-family: Arial, sans-serif;
		background-color: #f2f2f2;
	}

	.container {
		margin: 50px auto;
		padding: 20px;
		max-width: 800px;
		background-color: #fff;
		box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.3);
	}

	h1 {
		font-size: 32px;
		margin: 0 0 20px;
	}

	.form-field {
		margin-bottom: 20px;
	}

	.form-field label {
		display: block;
		margin-bottom: 5px;
	}

	.form-field input[type="text"] {
		padding: 5px;
		font-size: 16px;
		border-radius: 5px;
		border: 1px solid #ccc;
		width: 100%;
		box-sizing: border-box;
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

	.contest-list {
		list-style: none;
		padding: 0;
		margin-top: 30px;
	}

	.contest-list li {
		margin-bottom: 10px;
	}

	.contest-list li a {
		display: block;
		padding: 10px;
		background-color: #eee;
		border-radius: 5px;
		text-decoration: none;
		color: #333;
		font-weight: bold;
	}

	.contest-list li a:hover {
		background-color: #ccc;
	}

	.scroll-box {
		overflow-y: auto;
		max-height: 200px;
		border: 1px solid #ccc;
		padding: 10px;
	}
</style>
</head>
<body>
	<div class="container">
		<h3>${tips}</h1>
		<div class="form-field">
			
			<a href="newRoot.jsp" class="btn">Root Page</a>
			
		</div>
	</div>
</body>
</html>
