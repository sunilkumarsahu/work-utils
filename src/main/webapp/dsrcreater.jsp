<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>dsr_creator</title>
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
		<h1 class="w3-center w3-padding-64">DSR Generator</h1>

		<hr>
		<div class="w3-col l6 w3-text-teal">Open Critique home, select
			all and paste in below text area as simple string.</div>
		<div class="w3-col l6 w3-text-teal">DSR mail</div>
		<form method="POST" action="/create">

			<textarea class="w3-col l5 w3-padding-large" rows="30"
				name="input_str"
				placeholder="Open Critique home, select all and paste here as text."> ${cr_input} </textarea>
			<div class="w3-col l1 w3-padding-large w3-dropdown-click"
				id="dsr_generate" onClick="document.forms[0].submit();">
				<img src="assert/img_right_arrow.png"
					class="w3-round w3-image w3-opacity-min" alt="Arrow"
					style="width: 100%">
			</div>
			<textarea class="w3-col l5 w3-padding-large" rows="30"> ${mail} </textarea>
		</form>

	</div>

	<!-- Footer -->
	<footer class="w3-bottom w3-center w3-light-grey">
		<p>Copyright sks.bhp88@gmail.com 2018</p>
	</footer>

</body>
</html>