<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Show an event</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
        integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<body>
	<div class="container w-75 d-flex justify-content-between">
		
		<div class="w-75 mr-4">
			<h3 style="color:#603F8B" ><c:out value="${event.name}"/></h3> 
				
			
			<br>
			<br>
			
			<p>Host: <c:out value="${event.user.firstname}"/> <c:out value="${event.user.lastname}"/></p>
			<p>Date: <fmt:formatDate type="date" dateStyle = "long" pattern=" MMM d',' y" value="${ event.date }"/></p>
			<p>Location: <c:out value="${event.location}"/>, <c:out value="${event.state}"/></p>
			<p>People who are attending this event ${fn:length(event.people)}</p>
			<table class="table">
				<thead>
					<tr>
						<th scope="col">Name</th>
						<th scope="col" >Location</th>
	
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${event.people}" var="person">
						<tr>	
							<td ><c:out value="${person.firstname}"/> <c:out value="${person.lastname}"/></td>
							<td ><c:out value="${person.location}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="w-75">
			
			<div class="container ">
				<h3 style="color:#603F8B" >Message Wall</h3> 
				<div style="overflow:auto; height:300px" class= " border p-3 mb-3">
					<c:forEach items="${messages}" var="item">
						<c:out value="${item.user.firstname}"/> <c:out value="${item.user.lastname}"/>:  
						<c:out value="${item.comment}"/>
						
						<hr>
					</c:forEach>
				</div>
				<form:form modelAttribute="message" action="/events/${event.id}/comment" method="post" >
					<form:label path="comment" class="form-label">Add Comment:</form:label>
					<form:input path="comment" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
					<form:errors  path="comment" cssClass="invalid-feedback"/>
					<input type="submit" value="Submit" class="btn btn-dark mt-3">
				</form:form>
			</div>
		</div>
    </div>
	
</body>
</html>