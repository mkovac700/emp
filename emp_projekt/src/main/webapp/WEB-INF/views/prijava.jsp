<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Prijava</title>

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
	%>
	<header>
		<div class="container">
			<a href="${pageContext.servletContext.contextPath}">Početna
				stranica</a><br>
			<h1>Prijava</h1>
			<%@ include file="zaglavlje.jsp"%>
		</div>
	</header>
	<div class="container">
		<form action="" method="post">
			<label for="korisnicko_ime">Korisničko ime:</label><br> <input
				type="text" id="korisnicko_ime" name="korisnicko_ime"><br>
			<label for="lozinka">Lozinka:</label><br> <input type="password"
				id="lozinka" name="lozinka"><br> <label
				for="verification_code">Kod za verifikaciju:</label><br> <input
				type="text" id="verification_code" name="verification_code"><br>
			<br>
			<button type="submit">Prijavi se</button>
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

</body>
</html>