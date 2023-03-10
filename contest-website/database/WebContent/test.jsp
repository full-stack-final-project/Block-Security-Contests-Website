<!DOCTYPE html>
<html>
<head>
	<title>Create Contest</title>
	<link rel="stylesheet" type="text/css" href="style.css">
</head>
<style>
.scroll-box { border:2px solid #ccc; width:300px; height: 100px; overflow-y: scroll;
</style>
<body>
	<div class="container">
		<h1>Create Contest</h1>
		<form method="post" action="submit_contest.jsp">
			<label for="title">Contest Title:</label>
			<input type="text" id="title" name="title" required>

			<label for="wallet">Contest Wallet Address (42 chars, starting with 0x and followed by 40 hex digits):</label>
			<input type="text" id="wallet" name="wallet" required pattern="0x[a-fA-F\d]{40}" title="Please enter a valid wallet address">

			<label for="funding">Contest Funding (int):</label>
			<input type="number" id="funding" name="funding" required>

			<label for="begin_time">Begin Time:</label>
			<input type="datetime-local" id="begin_time" name="begin_time" required>

			<label for="end_time">End Time:</label>
			<input type="datetime-local" id="end_time" name="end_time" required>

			<label for="judges">Choose 2-3 Judges:</label>
			<div class="scroll-box">
				<input type="checkbox" id="judge1" name="judge1" value="Judge 1">
				<label for="judge1">Judge 1</label><br>

				<input type="checkbox" id="judge2" name="judge2" value="Judge 2">
				<label for="judge2">Judge 2</label><br>

				<input type="checkbox" id="judge3" name="judge3" value="Judge 3">
				<label for="judge3">Judge 3</label><br>

				<input type="checkbox" id="judge4" name="judge4" value="Judge 4">
				<label for="judge4">Judge 4</label><br>

				<input type="checkbox" id="judge5" name="judge5" value="Judge 5">
				<label for="judge5">Judge 5</label><br>
				<input type="checkbox" id="judge1" name="judge1" value="Judge 1">
				<label for="judge1">Judge 1</label><br>

				<input type="checkbox" id="judge2" name="judge2" value="Judge 2">
				<label for="judge2">Judge 2</label><br>

				<input type="checkbox" id="judge3" name="judge3" value="Judge 3">
				<label for="judge3">Judge 3</label><br>

				<input type="checkbox" id="judge4" name="judge4" value="Judge 4">
				<label for="judge4">Judge 4</label><br>

				<input type="checkbox" id="judge5" name="judge5" value="Judge 5">
				<label for="judge5">Judge 5</label><br>
			</div>

			<button type="submit">Submit</button>
		</form>
	</div>
</body>
</html>
