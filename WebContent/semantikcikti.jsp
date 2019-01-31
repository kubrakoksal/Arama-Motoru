<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>YAM</title>
</head>
<body style="background-color:#FFE5CC">
<a style="color:black; position:absolute; top:1%; left:2%" href="anasayfa.jsp">ANASAYFA</a>

<div style=" width:1110px; height:300px; position:absolute; top:5%; left:10%;">
<pre><b>Kelimeler: 
           
</b><textarea  style=" width:1200px; height:20px;"  name="kelime"/><%= request.getAttribute("kelime") %></textarea> <b>  
           	    
Sonuç: </b>     
     
<textarea  style=" width:1200px; height:500px;" name="sonuc"/><%= request.getAttribute("sonuc") %></textarea> <b>
</pre>
</div>
</body>
</html>