<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Create Contest</title>
	
</head>
	
	<title>Create Contest</title>
<style>

		body {
			background-color: #f2f2f2;
			font-family: Arial, Helvetica, sans-serif;
		}
		h1 {
			color: #333;
			font-size: 32px;
			font-weight: bold;
			text-align: center;
			margin-top: 50px;
		}
		form {
			background-color: #fff;
			border-radius: 10px;
			box-shadow: 0 0 10px rgba(0,0,0,0.3);
			margin: 50px auto;
			max-width: 500px;
			padding: 30px;
		}
		
		input[type=text], input[type=password], select {
			border: 1px solid #ccc;
			border-radius: 5px;
			font-size: 16px;
			padding: 10px;
			width: 100%;
			margin-bottom: 20px;
			box-sizing: border-box;
			color: #333;
		}
		input[type=submit] {
			background-color: #4CAF50;
			color: white;
			font-size: 16px;
			font-weight: bold;
			border: none;
			border-radius: 5px;
			cursor: pointer;
			padding: 10px 20px;
		}
		input[type=submit]:hover {
			background-color: #3e8e41;
		}
		.error {
			color: red;
			font-size: 14px;
			margin-top: 5px;
		}
		.success {
			color: green;
			font-size: 14px;
			margin-top: 5px;
		}
		.scroll-box { border:2px solid #ccc; width:500px; height: 300px; overflow-y: scroll;}
		
		textarea {
			width: 95%;
			height: 300px;
			padding: 10px;
			font-size: 16px;
			border-radius: 5px;
			border: 1px solid #ccc;
			margin-bottom: 20px;
	
</style>	
<script>
function validateForm() {
    var sponsorID = document.getElementById("sponsorID").value;
    console.log(sponsorID);
    if (!sponsorID){
    	alert("Please login first!");
    	return false;
    	
    }
    
	var checkboxes = document.getElementsByName('judges');
    var numChecked = 0;
    for (var i=0; i<checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            numChecked++;
        }
    }
    if ((numChecked < 5 || numChecked > 10) ) {
        alert("Please select between 5 and 10 judges.");
        console.log("Please select between 5 and 10 judges.")
        return false;
    }
    return true;
}
</script>

</head>
<body>
	<header>
		<h1>Create Contest</h1>
	</header>
	<main>
		<form method="post" action="createContest">
			<input type="hidden" id="sponsorID" name="sponsorID" value=${sponsor_id}>
			<label for="title">Contest Title:</label><br><br>
			<input type="text" id="title" name="title" required><br><br>

			<label for="wallet">Contest Wallet Address:</label><br><br>
			<input type="text" id="wallet" name="wallet" pattern="^0x[A-Fa-f0-9]{40}$" required><br><br>

			<label for="funding">Contest Funding:</label><br><br>
			<input type="number" id="funding" name="funding" min="1" required><br><br>

			<label for="begin">Begin Time:</label><br><br>
			<input type="datetime-local" id="begin" name="begin" required><br><br>

			<label for="end">End Time:</label><br><br>
			<input type="datetime-local" id="end" name="end" required><br><br>
			
			<div class="scroll-box">
				<br>
                <label for="judgeSelect">Choose 5-10 Judges:</label><br><br>
                <c:forEach items="${judgesList}" var="judge">
        			<input type="checkbox" name="judges" value="${judge}" id="${judge}" />
        			<label for="${judge}">${judge}</label><br />
   				 </c:forEach>
            </div>
            
            <br><br>
            <label for="requirement">Requirement list:</label><br><br>
            <textarea id="requirement" name="requirement"></textarea><br><br>

			<input type="submit" id="submit" name="submit" value="Create Contest" onclick="validateForm()">
		</form>
	</main>
	
</body>
</html>



