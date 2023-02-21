<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login Page</title>
	<style>
		body {
			background-color: #f1f1f1;
			font-family: Arial, sans-serif;
		}
		.container {
			background-color: #fff;
			margin: 0 auto;
			padding: 20px;
			width: 50%;
			max-width: 500px;
			box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
		}
		h1 {
			text-align: center;
		}
		
		input[type="text"],
		input[type="password"] {
			border: 2px solid #ccc;
			border-radius: 4px;
			padding: 8px;
			width: 100%;
			box-sizing: border-box;
			font-size: 16px;
			margin-bottom: 20px;
		}
		
		input[type="submit"] {
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
		input[type="submit"]:hover {
			background-color: #45a049;
		}
		.error {
			color: red;
			font-size: 14px;
			margin-bottom: 20px;
		}
		.success {
			color: green;
			font-size: 14px;
			margin-bottom: 20px;
		}
		#submitBtn:disabled {
  			background-color: #d3d3d3;
  			color: #a9a9a9;
  			cursor: not-allowed;
  		}
		
	</style>
	<script type="text/javascript">
		function checkWalletAddress() {
			var walletAddress = document.getElementById("walletAddress").value;
			if (walletAddress.length != 42) {
				document.getElementById("walletError").innerHTML = "Wallet address must be exactly 42 characters.";
				document.getElementById("submitBtn").disabled = true;
			} else {
				document.getElementById("walletError").innerHTML = "";
				//
				
				document.getElementById("submitBtn").disabled = false;
				
				
			}
		}
		document.querySelectorAll('input[type="radio"]').forEach((elem) => {
			  elem.addEventListener('change', checkForm);
			});

		function checkForm() {
			const radioButtons = document.getElementsByName("role");
			let radioSelected = false;
			for (let i = 0; i < radioButtons.length; i++) {
			  if (radioButtons[i].checked) {
			    radioSelected = true;
			    break;
			   }
			}
			 if (radioSelected && checkWalletAddress() && checkPasswords()) {
			    document.getElementById("submitBtn").disabled = false;
			  } else {
			    document.getElementById("submitBtn").disabled = true;
			  }
			}

	</script>
</head>
<body>
	<div class="container">
		<h1>Login in</h1>
		<form action="login" method="post">
		<label>Role:</label>
			<input type="radio" id="sponsor" name="role" value="sponsor" required onkeyup="checkForm()">
			<label for="sponsor">Sponsor</label>
			<input type="radio" id="judge" name="role" value="judge" onkeyup="checkForm()">
			<label for="judge">Judge</label>
			<input type="radio" id="contestant" name="role" value="contestant" onkeyup="checkForm()">
			<label for="contestant">Contestant</label>
			<input type="radio" id="root" name="role" value="root" onkeyup="checkForm()">
			<label for="root">Root</label><br><br>
			
			<label for="walletAddress">Wallet Address:</label>
			<input type="text" id="walletAddress" name="walletAddress" required maxlength="42" onkeyup="checkWalletAddress()">
			<span id="walletError" class="error"></span><br><br>

			<label for="password">Password:</label>
			<input type="password" id="password" name="password" required ><br>
			<span id="passwordError" class="error"></span><br>
			<input type="submit" id="submitBtn" value="Submit" disabled>
		</form>
	</div>
</body>
</html>



