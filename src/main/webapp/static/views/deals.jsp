<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	
	<link href="<c:url value='/static/css/style.css' />" rel="stylesheet" type="text/css"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet" type="text/css"></link>
	
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script type="text/javascript" src="<c:url value='/static/js/placeholder.js' />"></script>
	<script type="text/javascript" src="<c:url value='/static/js/input.js' />"></script>
</head>
<body>

	<!-- Start CONTAINER -->
	<div id="container">
	
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
		
		<!-- Start CONTENT -->
		<div id="content">
			<h4>Prihvacene ponude</h4>
			<c:if test="${firme!=null }">
				<table>
					<c:forEach items="${firme}" var="f" >
						<tbody>
							<tr id="task_tr">
								<td>
									<table class="table_firma"><tbody>
										<tr><td>Firma: ${f.key.firstName} ${f.key.lastName}</td></tr>
										<tr><td>Cena: ${f.value} dinara</td></tr>
									</tbody></table>
								</td>
								<td>
									<a href="<c:url value='/exec/${taskId}/3/2/${f.key.id}' />">
										<button id="tasklistButton" type="button">
											<img id="addUserImg" src="<c:url value='/static/img/info.png' />">
											Obrazlozenje
										</button>
									</a>
									<a href="<c:url value='/exec/${taskId}/3/1/${f.key.id}' />">
										<button id="registerButton" type="button">
											<img id="addUserImg" src="<c:url value='/static/img/ok.png' />">
											Prihvati
										</button>
									</a>
								</td>
							</tr>
						</tbody>
					</c:forEach>
				</table>
			</c:if>
			<a href="<c:url value='/exec/${taskId}/3/3/null' />">
				<button id="loginButton" type="button">
					<img id="addUserImg" src="<c:url value='/static/img/reload.png' />">
					Zahtev za nove ponude
				</button>
			</a>
			<a href="<c:url value='/exec/${taskId}/3/4/null' />">
				<button id="cancelButton" type="button">
					<img id="addUserImg" src="<c:url value='/static/img/cancel.png' />">
					Odustani
				</button>
			</a>
		</div>
		<!-- End CONTENT -->

	
	</div>
	<!-- End CONTAINER -->

</body>
</html>