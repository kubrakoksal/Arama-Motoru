<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>YAM</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

</head>
<body style="background-image:url(balonlu.jpg); background-repeat: no-repeat;">

<a style="color:black; position:absolute; top:1%; left:2%" href="anasayfa.jsp">ANASAYFA</a>



<h1 id="d" style="color:#b94646; width:1050px; height:300px; position:absolute; top:28%; left:40%"> 


                                         <b>KELİME SAYMA</b></br>

</h1>



<div style=" width:1050px; height:300px; position:absolute; top:40%; left:15%;">
<form type="get" action="kou" method="GET" >
<pre><b> Kelime:</b></b><input value='<%if(request.getAttribute("kelime")==null){
    out.print("");  
   }
   else {%><%=request.getAttribute("kelime") %><% } %>' style=" width:400px; height:30px;"  name="kelime" />   <b>Url:</b><input value='<%if(request.getAttribute("url")==null){
    out.print("");  
   }
   else {%><%=request.getAttribute("url") %><% } %>' style=" width:400px; height:30px;" name="url"/>   <button type="submit" class="btn btn-primary">Ara</button>
                                                         <b>Kelime Sayısı:</b></b><input style=" width:50px; height:30px;" readonly value='<%if(request.getAttribute("kelimesayisi")==null){out.print("0");  
   }
   else {%><%=request.getAttribute("kelimesayisi") %><% } %>'  name="kelimesayisi"/></pre>
</form>

</div>
</body>
</html>