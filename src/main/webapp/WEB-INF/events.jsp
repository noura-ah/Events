<%@ page isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<head>
	<meta charset="UTF-8">
	<title>events</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
        integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        
</head>
<body>
	<div class="container w-75">
		<c:if test="${not empty success}">
			<div class="alert alert-success mt-4"><c:out value="${success}"/></div>
		</c:if>
		
		<div class="d-flex align-items-center justify-content-between">
			<div class="mt-4">
				<h1 style="color:#603F8B">Welcome <c:out value="${user.firstname}"/></h1>
				<br>
				
			</div>
			<div class="d-flex flex-column align-items-end">
				<a style="color:#603F8B" href = "/logout">Logout</a>
				<br>
				
			</div>
			
		</div>
		<p>Here are some of the events in your state</p>
		<table class="table">
			<thead>
				<tr>
					<th scope="col">Name</th>
					<th scope="col">Date</th>
					<th scope="col" >Location</th>
					<th scope="col" >Host</th>
					<th scope="col" >Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${events}" var="event">
					<tr>	
						<td ><u><a style="color:#603F8B"  href='<c:url value="/events/${event.id}"/>' ><c:out value="${event.name}"/></a></u></td>
						<td ><fmt:formatDate type="date" dateStyle = "long" pattern="E ', the ' d ' of ' M', ' y" value="${ event.date }"/></td>
						<td ><c:out value="${event.location}"/></td>
						<td ><c:out value="${event.user.firstname}"/></td>
						<td class="col-2">
							<c:choose>
								<c:when test="${user_id == event.user.id}">
									<div class="d-flex align-items-center justify-content-between">
										<a  href='<c:url value="/events/${event.id}/edit"/>'>Edit</a> 
										<form action='/events/<c:out value="${event.id}"/>/delete' method="post">
											<input type="hidden" name="_method" value="delete">
											<input type="submit" class="btn-link border-0" value="Delete">
										</form>
									</div>
								</c:when>
								<c:otherwise>
									<c:set var="joining" value="false" />  
									<c:forEach items="${event.people}" var="person">
										<c:if test="${person.id == user_id}">
											<c:set var = "joining" value = "true"/> 
										</c:if>	
									</c:forEach>
									<c:choose>
										<c:when test="${joining == 'true'}">
											<div class="d-flex align-items-center justify-content-between">
												Joining
												<a href="<c:url value='/events/${event.id}/remove'/>">Cancel</a>
											</div>	 
										</c:when>
										<c:otherwise>
										<div class="d-flex align-items-center">
										<a href="<c:url value='/events/${event.id}/join'/>">Join</a>
										</div>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br>
		<p>Here are some of the events in other states</p>
		<table class="table">
			<thead>
				<tr>
					<th scope="col">Name</th>
					<th scope="col">Date</th>
					<th scope="col" >Location</th>
					<th scope="col" >Host</th>
					<th scope="col" >Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${eventsNotInState}" var="event">
					<tr>	
						<td ><u><a style="color:#603F8B"  href="<c:url value='/events/${event.id}'/>" ><c:out value="${event.name}"/></a></u></td>
						<td ><fmt:formatDate type="date" dateStyle = "long" pattern="E ', the ' d ' of ' M', ' y" value="${ event.date }"/></td>
						
						
						<td ><c:out value="${event.location}"/></td>
						<td ><c:out value="${event.user.firstname}"/></td>
						<td class="col-2">
							<c:set var="joining" value="false" />  
							<c:forEach items="${event.people}" var="person">
								<c:if test="${person.id == user_id}">
									<c:set var = "joining" value = "true"/> 
								</c:if>	
							</c:forEach>
							<c:choose>
								<c:when test="${joining == 'true'}">
									<div class="d-flex align-items-center justify-content-between">
										Joining
										<a href="<c:url value='/events/${event.id}/remove'/>">Cancel</a>
									</div>	 
								</c:when>
								<c:otherwise>
									<div class="d-flex align-items-center">
										<a href="<c:url value='/events/${event.id}/join'/>">Join</a>
									</div>
								</c:otherwise>
							</c:choose>
								
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>
			<div class="d-flex align-items-center justify-content-between">
					<h1 class="mb-2">Create an Event</h1>
			</div>
			<form:form class="border border-3 p-4 border-dark" method="post" modelAttribute="event" action="/events/new">
			<div class="mb-3"> 
				<form:label path="name" class="form-label">Name:</form:label>
				<form:input  path="name" cssClass="form-control" cssErrorClass="form-control is-invalid"  />
				<form:errors path="name" cssClass="invalid-feedback"/>
			</div>
			<div class="mb-3">
				<form:label path="date" class="form-label">Date:</form:label>
				<form:input  path="date" type="date" cssClass="form-control" cssErrorClass="form-control is-invalid"  />
				<form:errors path="date" cssClass="invalid-feedback"/>
			</div>
			<div class="mb-3">
				<form:label path="location" class="form-label">Location: </form:label>
				<form:input  path="location" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
				<form:errors  path="location" cssClass="invalid-feedback"/>
				<form:select path="state" cssClass="form-control" cssErrorClass="form-control is-invalid">
							<form:option value="Riyadh">Riyadh</form:option>
							<form:option value="Qassim">Qassim</form:option>
							<form:option value="Eastern Province">Eastern Province</form:option>
							<form:option value="Asir">Asir</form:option>
						</form:select>
			</div>
			
			<input type="submit" value="Add Listing" class="btn btn-dark">
		</form:form>
		</div>
		
		
		
	</div>
    
	
</body>
</html>
