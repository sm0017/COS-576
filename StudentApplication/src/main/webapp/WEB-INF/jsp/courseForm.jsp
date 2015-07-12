
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="/WEB-INF/jsp/urls.jspf"%>

<c:url var="formAction" value="${requestScope.action}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Course details</title>
</head>
<body>
	<ul id="breadcrumbs">
		<li><a href="${homeUrl}">Home</a></li>
		<li><a href="${studentListUrl}">Students</a></li>
		<li><a href="${courseListUrl}">Courses</a></li>
	</ul>

	<h1 class="vcard icon">Courses detail</h1>

	<form:form action="${formAction}" method="${requestScope.method}"
		modelAttribute="course" cssClass="main">

		<div class="warning">
			<form:errors />
		</div>


		<div class="panel grid">

			

			<div class="gridRow yui-gb">
				
				<div class="yui-u">
					<div>
						<form:input path="courseName" cssClass="short" />
					</div>
					<div class="errorMessage">
						<form:errors path="courseName" />
					</div>
				</div>
				<div class="fieldLabel">Course name:</div>
			</div>
			<div class="gridRow yui-gf">
				<div class="fieldLabel yui-u first">Course Start Date:</div>
				<div class="yui-u">
					<div>
						<form:input path="courseStartDate" cssClass="short" />
					</div>

					<div class="errorMessage">
						<form:errors path="courseStartDate" />
					</div>

				</div>
			</div>
			<div class="gridRow yui-gf">
				<div class="fieldLabel yui-u first">Course End Date:</div>
				<div class="yui-u">
					<div>
						<form:input path="courseEndDate" cssClass="short" />
					</div>

					<div class="errorMessage">
						<form:errors path="courseEndDate" />
					</div>

				</div>
			</div>
			<div class="gridRow yui-gf">
				<div class="fieldLabel yui-u first">Faculty Name:</div>
				<div class="yui-u">
					<div>
						<form:input path="facultyFirstName" cssClass="medium" />
					</div>

					<div class="errorMessage">
						<form:errors path="facultyFirstName" />
					</div>

				</div>
			</div>

			<div class="gridRow yui-gf">
				<div class="fieldLabel yui-u first">Faculty Last Name:</div>
				<div class="yui-u">
					<div>
						<form:input path="facultyLastName" cssClass="medium" />
					</div>

					<div class="errorMessage">
						<form:errors path="facultyLastName" />
					</div>

				</div>
			</div>

			<div class="gridRow yui-gf">
				<div class="fieldLabel yui-u first">Faculty Email :</div>
				<div class="yui-u">
					<div>
						<form:input path="facultyEmail" cssClass="medium" />
					</div>

					<div class="errorMessage">
						<form:errors path="facultyEmail" />
					</div>

				</div>
			</div>


			<div class="gridRow yui-gf">
				<div class="yui-u first"></div>
				<div class="yui-u">
					<input type="submit" value="Save" />
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>
