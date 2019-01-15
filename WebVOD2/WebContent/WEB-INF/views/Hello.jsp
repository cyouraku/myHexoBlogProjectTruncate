
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String videoPath = basePath + "video";
session.setAttribute("basePath", basePath);
session.setAttribute("videoPath", videoPath);
%>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>H5-03</title>
</head>
<body>
<video id="videoTest" width="160" height="90" controls="controls">
  <source src="${videoPath}/test.mp4" type="video/mp4">
</video>
</body>
</html>