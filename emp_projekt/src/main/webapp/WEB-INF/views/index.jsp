<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Početna stranica</title>

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
	<header>
		<div class="container">
		<h1>Početna stranica</h1>
		<%@ include file="zaglavlje.jsp"%>
		</div>
	</header>
	
	<main>
		<div class="container">
		<a href="${pageContext.servletContext.contextPath}/mvc/registracija">Registracija</a><br/>
		<a href="${pageContext.servletContext.contextPath}/mvc/prijava">Prijava</a><br/>
		</div>
	</main>

</body>
</html>