<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/urls.jspf" %>

<c:url var="coursesJsUrl" value="/scripts/courses.js" />
<c:url var="searchByNameUrl" value="/courses/search.html" />

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Search results for course name like "<c:out value="${param.courseName}" />"</title>
		<script type="text/javascript" src="${coursesJsUrl}"></script>
	</head>
	<body>
		<ul id="breadcrumbs">
			<li><a href="${homeUrl}">Home</a></li>
			<li><a href="${studentListUrl}">Students</a></li>
			<li><a href="${courseListUrl}">Courses</a></li>
		</ul>
	
		<h1>Search results for course Name like "<c:out value="${param.courseName}" />"</h1>
		
		<c:choose>
			<c:when test="${empty courseList}">
				<p>No courses found.</p>
			</c:when>
			<c:otherwise>
				<table id="courseList" class="sortable">
					<thead>
						<tr>
							<th>CourseName</th>
							<th>courseStartDate</th>
							<th>Faculty email</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="course" items="${courseList}">
							<c:url var="courseUrl" value="/courses/${course.courseName}.html" />
							<tr id="course-${course.courseName}">
								<td><span class="vcard icon"><a href="${courseUrl}">${course.courseName}</a></span></td>
								<td><c:if test="${not empty course.facultyEmail}"><span class="email icon"><a href="mailto:${course.facultyEmail}">${course.facultyEmail} </a></span></c:if></td>
								<td>
									<span class="vcardDelete icon"><a class="deleteCourse" href="#">Delete</a></span>
									<form class="deleteForm" action="${courseUrl}" method="post">
										<div>
										<input type="hidden" name="_method" value="DELETE" />
										</div>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
		<p>&laquo; Back to <a href="${courseListUrl}">Courses</a></p>
	</body>
</html>
