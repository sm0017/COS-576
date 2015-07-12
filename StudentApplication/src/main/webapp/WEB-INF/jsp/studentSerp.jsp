<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/urls.jspf" %>

<c:url var="studentsJsUrl" value="/scripts/students.js" />
<c:url var="searchByEmailUrl" value="/students/search.html" />

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Search results for e-mail like "<c:out value="${param.email}" />"</title>
		<script type="text/javascript" src="${studentsJsUrl}"></script>
	</head>
	<body>
		<ul id="breadcrumbs">
			<li><a href="${homeUrl}">Home</a></li>
			<li><a href="${studentListUrl}">students</a></li>
		</ul>
	
		<h1>Search results for e-mail like "<c:out value="${param.email}" />"</h1>
		
		<c:choose>
			<c:when test="${empty studentList}">
				<p>No students found.</p>
			</c:when>
			<c:otherwise>
				<table id="studentList" class="sortable">
					<thead>
						<tr>
							<th>Name</th>
							<th>E-mail</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="student" items="${studentList}">
							<c:url var="studentUrl" value="/students/${student.studID}.html" />
							<tr id="student-${student.studID}">
								<td><span class="vcard icon"><a href="${studentUrl}">${student.fullName}</a></span></td>
								<td><c:if test="${not empty student.email}"><span class="email icon"><a href="mailto:${student.email}">${student.email} </a></span></c:if></td>
								<td>
									<span class="vcardDelete icon"><a class="deleteStudent" href="#">Delete</a></span>
									<form class="deleteForm" action="${studentUrl}" method="post">
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
		<p>&laquo; Back to <a href="${studentListUrl}">Students</a></p>
	</body>
</html>
