<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
<head>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <style>
.custom {
    width:180px !important;
    height:45px !important;
    border-width: 1px;
    border-style:solid;
    border-color:black;
}
  </style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>YAM</title>
</head>
<body style="background-image:url(doga2.jpg); background-size:100%; background-repeat: no-repeat;">

<div  style=" width:300px; height:300px; position:absolute; top:16%; left:72%; ">
<button type="button" class="btn btn-success custom"  onclick="location.href='deneme.jsp'" >KELİME SAYMA</button><br><br><br>
<button type="button" class="btn btn-warning  custom" onclick="location.href='urlsirala.jsp'" >URL SIRALAMA</button><br><br><br>
<button type="button" class="btn btn-primary  custom " onclick="location.href='websitesirala.jsp'"  >WEB SİTE SIRALAMA</button><br><br><br>
<button type="button" class="btn btn-danger custom"  onclick="location.href='semantikanaliz.jsp'"   >SEMANTİK ANALİZ</button><br><br><br>
</div>


</body>
</html>