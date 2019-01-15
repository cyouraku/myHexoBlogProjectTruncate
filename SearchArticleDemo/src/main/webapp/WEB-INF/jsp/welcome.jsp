<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String favicon = basePath + "static/images/favicon/default_site_favicon.png";
session.setAttribute("favicon", favicon);
%>
<html lang="en">

<body>
	<c:url value="/resources/test.txt" var="url"/>
	<spring:url value="/resources/test.txt" htmlEscape="true" var="springUrl" />
	Spring URL: ${springUrl} at ${time}
	<br>
	JSTL URL: ${url}
	<br>
	Message: ${favicon}
	<br>
	<select id="car">
  		<option value="volvo">Volvo</option>
  		<option value="saab">Saab</option>
  		<option value="vw">VW</option>
  		<option value=""></option>
	</select>
	<br>
</body>

</html>
