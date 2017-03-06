<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	
	<script type="text/javascript" src="<c:url value='/static/js/jquery-2.1.0.min.js' />"></script>
	<script src="<c:url value='/static/js/script.js' />"></script>
	<script src="<c:url value='/static/js/input.js' />"></script>
	
	
<%-- 	<link href="<c:url value='/static/css/theme.css' />" rel="stylesheet" type="text/css"></link> --%>
	<link href="<c:url value='/static/css/style.css' />" rel="stylesheet" type="text/css"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet" type="text/css"></link>
	
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
			<h4>${taskName}</h4>
			<form id="slick-data"
				action="<c:if test="${taskName == 'Unos Zahteva'}"> /exec/${taskId}/1 </c:if>
						<c:if test="${taskName == 'Odluka Agenta o Ponudi'}"> /exec/${taskId}/2 </c:if>
						<c:if test="${taskName == 'Agent Odgovara'}"> /exec/${taskId}/4 </c:if>
						<c:if test="${taskName == 'Klijent Donosi Odluku'}"> # </c:if>
						<c:if test="${taskName == 'Klijent Ocenjuje'}"> /exec/${taskId}/6 </c:if>" 
				method="POST">
				<table>
					<c:forEach items="${formProperties}" var="f">
					<tr  id="task_tr">
						<td><c:if test="${f.id!='izabranaFirmaID'}"> ${f.name } </c:if></td>
						<td>
							<c:if test="${f.type.name=='enum' && f.id=='kategorijaPosla' }">
								<select id="${f.id}" name="${f.id}">
									<option value="-1"> - Izaberite kategoriju - </option>
									<c:forEach items="${kategorijePoslova }" var="jc" >
										<option value="${jc.key}"> ${jc.value } </option>
									</c:forEach>
								</select>
							</c:if>
							<c:if test="${f.type.name=='enum' && f.id=='brojPonuda'}">
								<select id="${f.id}" name="${f.id}" >
									<option value="-1" > - Izaberite broj ponuda - </option>
									<c:forEach items="${brojPonuda}" var="p" >
										<option value="${p.key}"> ${p.value} </option>
									</c:forEach>
								</select> 
							</c:if>
							<c:if test="${f.type.name=='enum' && f.id=='ocenaPosla' }">
								<select id="${f.id}" name="${f.id}">
									<option value="-1"> - Izaberite ocenu - </option>
									<c:forEach items="${ocenePosla }" var="jc" >
										<option value="${jc.key}"> ${jc.value } </option>
									</c:forEach>
								</select>
							</c:if>   
							
							<c:if test="${f.type.name=='string'}">
								<input id="${f.id }" name="${f.id}"
								<c:if test="${f.readable!=true || f.id=='izabranaFirmaID'}"> type="hidden" </c:if>
								<c:if test="${f.required==true }"> required </c:if>
								<c:if test="${f.readable==true }"> type="text" </c:if> 
								<c:if test="${f.writable!=true }"> readonly="readonly" value="${f.value}" </c:if>>
							</c:if>
							   
							<c:if test="${f.type.name=='long' }">
								<input id="${f.id }" name="${f.id}"
								<c:if test="${f.readable!=true }"> type="hidden" </c:if>
								<c:if test="${f.required==true }"> required </c:if>
								<c:if test="${f.readable==true }"> type="text" </c:if>
								<c:if test="${f.writable!=true }"> readonly="readonly" </c:if>>
							</c:if>
							
							<c:if test="${f.type.name=='boolean' }">
								<input id="${f.id}" name="${f.id }" value="true" onclick="validate()"
									<c:if test="${f.readable==true }"> type="checkbox" </c:if>
									<c:if test="${f.readable!=true }"> type="hidden" </c:if>>
								<input id="${f.id}Hidden" name="${f.id }" type="hidden" value="false"
									<c:if test="${f.readable==true }"> type="checkbox" </c:if>>
								
								<!-- Start CheckBox JavaScript -->
								<script type="text/javascript">
									//checkbox
									function validate() {
									    if (document.getElementById('ponudaPrihvacena').checked) {
									    	document.getElementById('ponudaPrihvacenaHidden').setAttribute("disabled", "disabled");
									    	document.getElementById('ponudjenaCena').setAttribute("required", "required");
									    } else {
									       document.getElementById('ponudaPrihvacenaHidden').removeAttribute("disabled");
									       document.getElementById('ponudjenaCena').removeAttribute("required");
									    }
									}    
								</script>
								<!-- End CheckBox JavaScript -->
							</c:if>
							
						</td>
					</tr>
					</c:forEach>
					<tr  id="task_tr">
						<td></td>
						<td>
							<c:if test="${taskName != 'Klijent Donosi Odluku'}">
								<button id="registerButton" type="submit" form="slick-data">
									<img id="addUserImg" src="<c:url value='/static/img/ok.png' />">
									Potvrdi
								</button>
							</c:if>
						<td>
					</tr>
					<c:if test="${taskName == 'Klijent Donosi Odluku'}">
						<tr id="task_tr">
							<td></td>
							<td>
								<a href="<c:url value='/exec/${taskId}/5/1' />">
									<button id="registerButton" type="button">
										<img id="addUserImg" src="<c:url value='/static/img/ok.png' />">
										Prihvati
									</button>
								</a>
							<td>
						</tr>
						<tr id="task_tr">
							<td>
								<a href="<c:url value='/exec/${taskId}/5/2' />">
									<button id="loginButton" type="button">
										<img id="addUserImg" src="<c:url value='/static/img/reload.png' />">
										Nazad na ponude
									</button>
								</a>
							</td>
							<td>
								<a href="<c:url value='/exec/${taskId}/5/3' />">
									<button id="cancelButton" type="button">
										<img id="addUserImg" src="<c:url value='/static/img/cancel.png' />">
										Odustani
									</button>
								</a>
							<td>
						</tr>
					</c:if>
				</table>
			</form>
		
		</div>
		<!-- End CONTENT -->
		
	</div>
	<!-- End CONTAINER -->
</body>
</html>