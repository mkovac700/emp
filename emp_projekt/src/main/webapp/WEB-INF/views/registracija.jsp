<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registracija</title>

<style>
.container {
	justify-content: center;
	text-align: center;
	width: max-content;
	margin: 0 auto;
}
</style>

</head>
<body>
	<%
	String poruka = (String) request.getAttribute("poruka");
	String secretKey = (String) request.getAttribute("secretKey");
	String imageAsBase64 = (String) request.getAttribute("imageAsBase64");
	%>
	<header>
		<div class="container">
			<a href="${pageContext.servletContext.contextPath}">Početna
				stranica</a><br>
			<h1>Registracija</h1>
			<%@ include file="zaglavlje.jsp"%>
		</div>
	</header>
	<div class="container">
		<!-- style=" display:flex;" -->
		<form action="" method="post">
			<label for="korisnicko_ime">Korisničko ime:</label><br> <input
				type="text" id="korisnicko_ime" name="korisnicko_ime"><br>
			<label for="lozinka">Lozinka:</label><br> <input type="password"
				id="lozinka" name="lozinka"><br> <input type="hidden"
				id="secret_key" name="secret_key" value='<%=secretKey%>'><br>
			<button type="submit">Registriraj se</button>
		</form>
		<br>
		<%
		if (poruka != null) {
		%>

		<%=poruka%>

		<%
		}
		%>
	</div>
	<br>
	<br>
	<div class="container">
		<!-- class="container" -->
		<img src="data:image/png;base64,<%=imageAsBase64%>" /> <br>
		<%=secretKey%>
		<br> <br>

		<ol>
			<li style="text-align: left">Instalirajte Google autentifikator
				na vaš mobilni uređaj</li>
			<li style="text-align: left">Skenirajte QR kod ili unesite ključ
				vidljiv na ekranu</li>
			<li style="text-align: left">Dodijelite naziv računa i spremite
				postavke</li>
			<li style="text-align: left">Prilikom svake prijave koristite
				vremenski kod iz autentifikatora</li>
		</ol>
	</div>

</body>
</html>