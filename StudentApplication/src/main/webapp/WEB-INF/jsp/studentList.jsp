
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
		<title>Students</title>
		<script type="text/javascript" src="${studentsJsUrl}"></script>
	</head>
	<body>
		<ul id="breadcrumbs">
			<li><a href="${homeUrl}">Home</a></li>
		</ul>
	
		<h1>Students</h1>
		
		<c:if test="${param.saved}">
			<div class="info alert">Student details saved.</div>
		</c:if>
		<c:if test="${param.deleted}">
			<div class="info alert">Student details deleted.</div>
		</c:if>
		
		<c:choose>
			<c:when test="${empty studentList}">
				<p>Your Student list is empty. <a href="${newStudentUrl}">Add new Student.</a></p>
			</c:when>
			<c:otherwise>
				<div class="tableActionBar">
					${fn:length(studentList)} students |
					<span class="vcardAdd icon"><a href="${newStudentUrl}">Add new Student</a></span>
				</div>
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
								<td><span class="vcard icon"><a href="${studentsUrl}">${student.fullName}</a></span></td>
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
		
		<div class="panel" style="padding: 10px 20px">
			<form action="${searchByEmailUrl}" method="get">
				<div>
				Search by e-mail (partial OK):&nbsp;
				</div>
				<div>
				<input type="text" name="email" class="medium" />&nbsp;
				<input type="submit" value="Search" />
				</div>
				
			</form>
		</div>
	</body>
</html>
