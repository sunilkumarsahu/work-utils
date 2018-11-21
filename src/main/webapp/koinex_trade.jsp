<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>koinex_trade</title>
<link rel="stylesheet" href="css/styles.css" />
<style>
body {
	font-family: "Times New Roman", Georgia, Serif;
	background-color: #a2afbe;
}

h1, h2, h3, h4, h5, h6 {
	font-family: "Playfair Display";
	letter-spacing: 5px;
}
</style>
</head>
<body>

	<!-- Navbar (sit on top) -->
	<div class="w3-top">
		<div class="w3-bar w3-white w3-padding w3-card"
			style="letter-spacing: 4px;">
			<a href="index.html#home" class="w3-bar-item w3-button"><img
				alt="Logo" src="assert/img_logo.png" width="40" height="40"></a>
			<!-- Right-sided navbar links. Hide them on small screens -->
			<div class="w3-right w3-hide-small">
				<a href="index.html#menu" class="w3-bar-item w3-button">Menu</a> <a
					href="index.html#contact" class="w3-bar-item w3-button">Contact</a>
			</div>
		</div>
	</div>

	<!-- Page content -->
	<div class="w3-content" style="max-width: 1100px">
		<h1 class="w3-center w3-padding-64">Koinex Trade Window</h1>

		<hr>
		<form method="POST" action="/trade">
			<div class="w3-col w3-xlarge">
				Principal amount in INR: <input type="text" name="principal_amount"
					value="${principal}">
				<button class="btn success" onClick="document.forms[0].submit();">Trade</button>
			</div>
		</form>
	</div>

	<!-- Footer -->
	<footer class="w3-bottom w3-center w3-light-grey">
		<p>Copyright sks.bhp88@gmail.com 2018</p>
	</footer>

</body>
</html>