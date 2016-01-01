<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body bgcolor="CEF6F5">
	<form action="Dijkstra" method="POST">
		<br> <br> <br>
		<div align="center">
			<font color="blue"> Input File </font>
		</div>
		<br>
		<div align="center">
			<input type="file" name="file" size="20" &nbsp />
		</div>
		<br>
		<div align="center">
			<input type="submit" value="Open">
		</div>
		<br>
    </form>
    
	<%if(request.getSession().getAttribute("FAIL")!=null) {%>
	<font color="red" align="center"
		value="<%=request.getSession().getAttribute("FAIL")%>"><%=request.getSession().getAttribute("FAIL")%></font>
	<%} %>


</body>
</html>
