<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Prijava</title>

	<link href="<c:url value='/static/css/style.css' />" rel="stylesheet" type="text/css"></link>
	
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script type="text/javascript" src="<c:url value='/static/js/placeholder.js' />"></script>
	<script type="text/javascript" src="<c:url value='/static/js/input.js' />"></script>
</head>
<body>
	
	<!-- Start HEADER -->
	<div id="header">
		
		<!-- Start TOPBAR -->
		<div id="topbar">
			<img id="activitiImg" alt="activiti" src="<c:url value='/static/img/activiti.png' />">
			<img id="springbootImg" alt="spring_boot" src="<c:url value='/static/img/springboot.png' />">
		</div>
		<!-- End TOPBAR -->
		
	</div>
	<!-- End HEADER -->

	<form id="slick-login" method="POST" action="login">
	
		<c:if test="${error !=null}">
			<h4 class="loginError" >Prijava neuspesna! </h4>                        		
		</c:if>
	
		<input type="text" id="username" name="username" 
					class="placeholder" placeholder="korisnicko_ime"
					onkeyup="limitText(this, 30)" autocomplete="off" required />
		
		<label for="password">password</label>
		<input type="password" id="password" 
					name="password" class="placeholder" placeholder="lozinka" 
					onkeyup="limitText(this, 30)" autocomplete="off" required />
		
		<button id="loginButton" type="submit" form="slick-login">
				<img id="loginImg" src="<c:url value='/static/img/key.png' />">
				Prijavi se
		</button>
		
		<a href="<c:url value='/list' />">
			<button id="registerButton" type="button">
				<img id="addUserImg" src="<c:url value='/static/img/adduser.png' />">
				Registruj se
			</button>
		</a>
	</form>


</body>
</html>