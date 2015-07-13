<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>



<c:url var="jqueryTablesorterJsUrl" value="/scripts/jquery.tablesorter.min.js" />
<c:url var="biglogoUrl" value="/images/universitylogo.jpg" />
<c:url var="sipCssUrl" value="/styles/studcourse.css" />
<c:url var="sipJsUrl" value="/scripts/studcourse.js" />

<c:url var="sipCoverImgUrl" value ="/images/universitylogo.jpg" />

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><decorator:title /> - Welcome</title>
		<link rel="stylesheet" type="text/css" href="${baseGoogleUrl}/yui/2.9.0/build/reset-fonts-grids/reset-fonts-grids.css" />
		<link rel="stylesheet" type="text/css" href="${baseGoogleUrl}/yui/2.9.0/build/base/base.css" />
		<link rel="stylesheet" type="text/css" href="${baseGoogleUrl}/jqueryui/1.8.14/themes/vader/jquery-ui.css" />
		<link rel="stylesheet" type="text/css" href="${sipCssUrl}" />
		<script type="text/javascript" src="${baseGoogleUrl}/jquery/1.6.1/jquery.min.js"></script>
		<script type="text/javascript" src="${baseGoogleUrl}/jqueryui/1.8.14/jquery-ui.min.js"></script>
		<script type="text/javascript" src="${jqueryTablesorterJsUrl}"></script>
		<script type="text/javascript" src="${sipJsUrl}"></script>
		<decorator:head />
	</head>
	<body>
		<div id="doc3">
			<div id="outerHdSubhd">
				<div id="innerHdSubhd">
					<div id="hd">
						<div id="hdTitle"></div>
						<div id="hdAppName">
						

						<c:out value="University Of Southern Maine" /></div>
					</div>
					<%@ include file="/WEB-INF/jsp/skin/subhead.jspf" %>
					
				</div>
			</div>
			<div id="bd">
				<div class="regionInner">
					<decorator:body />
				</div>
		
		
			</div>
			</body>
			
</html>
