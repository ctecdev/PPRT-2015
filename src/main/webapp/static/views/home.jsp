<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>

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
			<a href="<c:url value='/home' />">
				<img id="activitiImg" alt="activiti" src="<c:url value='/static/img/activiti.png' />">
				<img id="springbootImg" alt="spring_boot" src="<c:url value='/static/img/springboot.png' />">
			</a>
			<a href="<c:url value='/logout' />">
				<button id="logoutButton" type="button"> 
					<img id="logoutImg" src="<c:url value='/static/img/logout.png' />">
				</button>
			</a>
			<!-- <a href="<c:url value='/updateUser/${loggedUserId}' />">
				<button id="updateUserButton" type="button">
					<img id="editUserImg" alt="Odjavi se" src="<c:url value='/static/img/edituser.png' />">
				</button>
			</a> -->
			<h4 id="user">
				<span class="userspan">Ulogovan Korisnik:</span> ${loggedUserId} <!-- (${loggedUserType}) -->
			</h4>
		</div>
		<!-- End TOPBAR -->
		
	</div>
	<!-- End HEADER -->
	
	<div id="slick-login">
	
		<c:if test="${error !=null}">
			<h4 class="loginError" >Prijava neuspesna! </h4>                        		
		</c:if>
		
		<a href="<c:url value='/process' />">
			<button id="processButton" type="button">
				<img id="processImg" src="<c:url value='/static/img/process.png' />">
				Pokreni Proces
			</button>
		</a>
		<a href="<c:url value='/tasklist' />">
			<button id="tasklistButton" type="button">
				<img id="taskImg" src="<c:url value='/static/img/tasklist.png' />">
				Lista Zadataka
			</button>
		</a>
	
	</div>


</body>
</html>