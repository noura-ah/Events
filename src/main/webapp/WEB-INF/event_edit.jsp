<%@ page isErrorPage="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    



<!DOCTYPE html>
<head>
	<meta charset="UTF-8">
	<title>edit a book</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
        integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
	<div class="container w-75">
		
		
		<div >
			<div class="d-flex align-items-center justify-content-between">
				<h1 class="m-4"style="color:#603F8B">Change Your Entry</h1>
				
			</div>
			<form:form class="border border-3 p-4 border-dark" method="post" modelAttribute="event" action="/events/${event.id}/update">
			<input type="hidden" name="_method" value="put">
			<form:hidden path="id" />
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
			
			<input type="submit" value="Edit" class="btn btn-dark">
		</form:form>
		</div>
		
	</div>
    
	
</body>
</html>
