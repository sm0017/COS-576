<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url var="submitCourseRegistrationUrl" value="/courses.html" />

<spring:message var="pageTitleCourse" code="newCourseRegistration.pageTitleCourse" />
<spring:message var="msgAllFieldsRequired" code="newCourseRegistration.message.allFieldsRequired" />
<spring:message var="register" code="newCourseRegistration.label.add" />

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><c:out value="${pageTitleCourse}" /></title>
	</head>
	<body>
		<h1><c:out value="${pageTitleCourse}" /></h1>
		
		<form:form cssClass="main" action="${submitCourseRegistrationUrl}" modelAttribute="courseAccount">
			
			<form:errors path="*">													
				<div class="warning alert"><spring:message code="error.global" /></div>
			</form:errors>  
			
			<p><spring:message code="newCourseRegistration.message.allFieldsRequired" /></p>
			
			<div class="panel grid">
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="newCourseRegistration.label.coursename" /></div>
					<div class="yui-u">
						<div><form:input path="coursename" cssClass="short" cssErrorClass="short error" /></div>
						
							<div class="errorMessage"><form:errors path="coursename" htmlEscape="false" /></div>
				
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="newCourseRegistration.label.facultyFirstName" /></div>
					<div class="yui-u">
						<div><form:input path="facultyFirstName" showPassword="false" cssClass="short" cssErrorClass="short error" /></div>
						
							<div class="errorMessage"><form:errors path="facultyFirstName" htmlEscape="false" /></div>
					
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="newCourseRegistration.label.facultyLastName" /></div>
					<div class="yui-u">
						<div><form:input path="facultyLastName" showPassword="false" cssClass="short" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="newCourseRegistration.label.startDate" /></div>
					<div class="yui-u">
						<div><form:input path="startDate" cssClass="short" cssErrorClass="short error" /></div>
						
							<div class="errorMessage"><form:errors path="startDate" htmlEscape="false" /></div>
						
					</div>
				</div>
				
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="newCourseRegistration.label.endDate" /></div>
					<div class="yui-u">
						<div><form:input path="endDate" cssClass="medium" cssErrorClass="medium error" /></div>
												<div class="errorMessage"><form:errors path="endDate" htmlEscape="false" /></div>
					
					</div>
				</div>
				
	
				
				<div class="gridRow yui-gf">
					<div class="yui-u first"></div>
					<div class="yui-u"><input type="submit" value="${register}"></input></div>
				</div>
			</div>
		</form:form>
	</body>
</html>
