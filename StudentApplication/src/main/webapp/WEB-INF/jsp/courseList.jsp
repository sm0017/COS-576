
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/urls.jspf"%>

<c:url var="coursesJsUrl" value="/scripts/courses.js" />
<c:url var="searchByNameUrl" value="/courses/search.html" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Courses</title>
<script type="text/javascript" src="${coursesJsUrl}"></script>
</head>
<body>
	<ul id="breadcrumbs">
		<li><a href="${homeUrl}">Home</a></li>
	</ul>

	<h1>Courses</h1>

	<c:if test="${param.saved}">
		<div class="info alert">Course details saved.</div>
	</c:if>
	<c:if test="${param.deleted}">
		<div class="info alert">Course details deleted.</div>
	</c:if>

	<c:choose>
		<c:when test="${empty courseList}">
			<p>
				Your Course list is empty. <a href="${newCourseUrl}">Add new
					Course.</a>
			</p>
		</c:when>
		<c:otherwise>
			<div class="tableActionBar">
				${fn:length(courseList)} courses | <span class="vcardAdd icon"><a
					href="${newCourseUrl}">Add new Course</a></span>
			</div>
			<table id="courseList" class="sortable">
				<thead>
					<tr>
						<th>Course Name</th>
						<th>Course Start Date</th>
						<th>Faculty email</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="course" items="${courseList}">
						<c:url var="courseUrl" value="/courses/${course.courseID}.html" />
						<tr id="course-${course.courseID}">
							<td><span class="vcard icon">
							<a href="${coursesUrl}">${course.courseName}</a></span></td>
							
								<td><c:if test="${not empty course.courseStartDate}">
									<span class="vcard icon"> <a
										href="mailto:${course.courseStartDate}">${course.courseStartDate}
									</a></span>
								</c:if></td>
								
							<td><c:if test="${not empty course.facultyEmail}">
									<span class="email icon"> <a
										href="mailto:${course.facultyEmail}">${course.facultyEmail}
									</a></span>
								</c:if></td>
							<td><span class="vcardDelete icon"><a
									class="deleteCourse" href="#">Delete</a></span>
								<form class="deleteForm" action="${courseUrl}" method="POST">
									<div>
									<input type="hidden" name="_method" value="DELETE" />
									</div>
								</form></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>

	<div class="panel" style="padding: 10px 20px">
	<!--  	<form action="${searchByNameUrl}" method="get"> -->
		
			<form action="${searchByNameUrl}" method="get">
			
			<div>
			Search by Courses by Name (partial OK):&nbsp;
			
			<input type="text" name="courseName" class="medium" />&nbsp;
			<input type="submit" value="Search" />
			</div>
		</form>
	</div>
</body>
</html>
