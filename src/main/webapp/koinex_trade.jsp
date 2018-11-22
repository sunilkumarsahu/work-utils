<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

.switch {
	position: relative;
	display: block;
	vertical-align: top;
	width: 110px;
	height: 30px;
	padding: 3px;
	margin: 0 10px 10px 0;
	background: linear-gradient(to bottom, #eeeeee, #FFFFFF 25px);
	background-image: -webkit-linear-gradient(top, #eeeeee, #FFFFFF 25px);
	border-radius: 18px;
	box-shadow: inset 0 -1px white, inset 0 1px 1px rgba(0, 0, 0, 0.05);
	cursor: pointer;
}

.switch-input {
	position: absolute;
	top: 0;
	left: 0;
	opacity: 0;
}

.switch-label {
	position: relative;
	display: block;
	height: inherit;
	font-size: 10px;
	text-transform: uppercase;
	background: #eceeef;
	border-radius: inherit;
	box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.12), inset 0 0 2px
		rgba(0, 0, 0, 0.15);
}

.switch-label:before, .switch-label:after {
	position: absolute;
	top: 50%;
	margin-top: -.5em;
	line-height: 1;
	-webkit-transition: inherit;
	-moz-transition: inherit;
	-o-transition: inherit;
	transition: inherit;
}

.switch-label:before {
	content: attr(data-off);
	right: 11px;
	color: #aaaaaa;
	text-shadow: 0 1px rgba(255, 255, 255, 0.5);
}

.switch-label:after {
	content: attr(data-on);
	left: 11px;
	color: #FFFFFF;
	text-shadow: 0 1px rgba(0, 0, 0, 0.2);
	opacity: 0;
}

.switch-input:checked ~ .switch-label {
	background: #4700f9;
	box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.15), inset 0 0 3px
		rgba(0, 0, 0, 0.2);
}

.switch-input:checked ~ .switch-label:before {
	opacity: 0;
}

.switch-input:checked ~ .switch-label:after {
	opacity: 1;
}

.switch-handle {
	position: absolute;
	top: 4px;
	left: 4px;
	width: 28px;
	height: 28px;
	background: linear-gradient(to bottom, #FFFFFF 40%, #f0f0f0);
	background-image: -webkit-linear-gradient(top, #FFFFFF 40%, #f0f0f0);
	border-radius: 100%;
	box-shadow: 1px 1px 5px rgba(0, 0, 0, 0.2);
}

.switch-handle:before {
	content: "";
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -6px 0 0 -6px;
	width: 12px;
	height: 12px;
	background: linear-gradient(to bottom, #eeeeee, #FFFFFF);
	background-image: -webkit-linear-gradient(top, #eeeeee, #FFFFFF);
	border-radius: 6px;
	box-shadow: inset 0 1px rgba(0, 0, 0, 0.02);
}

.switch-input:checked ~ .switch-handle {
	left: 74px;
	box-shadow: -1px 1px 5px rgba(0, 0, 0, 0.2);
}
/* Transition
        ========================== */
.switch-label, .switch-handle {
	transition: All 0.3s ease;
	-webkit-transition: All 0.3s ease;
	-moz-transition: All 0.3s ease;
	-o-transition: All 0.3s ease;
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
			<div class="w3-col w3-xlarge w3-padding-large">
				Principal amount in INR: <input type="text" name="principal_amount"
					value="${principal}">
				<button class="btn success" onClick="document.forms[0].submit();">Trade</button>
				<label class="switch w3-right"> <input class="switch-input"
					type="checkbox" checked name="is_real_time" /> <span
					class="switch-label" data-on="Real Time" data-off="Last Trade"></span>
					<span class="switch-handle"></span>
				</label>
			</div>

			<table class="w3-padding-table" border="4" style="width: 100%">
				<tr>
					<th>Coin1 (buy with INR)</th>
					<th>Coin1 Volume</th>
					<th>Coin2 (buy with Coin1)</th>
					<th>Coin2 Volume</th>
					<th>Sell Price</th>
					<th>Profit in INR</th>
				</tr>

				<c:forEach items="${trades}" var="trade">
					<tr>
						<td align="center">${trade.coinOneName}</td>
						<td align="center">${trade.coinOneVolume}</td>
						<td align="center">${trade.coinTwoName}</td>
						<td align="center">${trade.coinTwoVolume}</td>
						<td align="center">${trade.soldInInr}</td>
						<td align="center">${trade.profit}</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>

	<!-- Footer -->
	<footer class="w3-bottom w3-center w3-light-grey">
		<p>Copyright sks.bhp88@gmail.com 2018</p>
	</footer>

</body>
</html>