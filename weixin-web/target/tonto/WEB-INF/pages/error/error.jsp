<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>错误</title>
<link href="<%=path%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/css/bootstrap/bootstrap-theme.min.css" rel="stylesheet">
</head>
<body>
	<div class="row-fluid">
	  <div class="span4">处理异常</div>
	  <div class="span8">${error}</div>
	</div>
</body>
</html>