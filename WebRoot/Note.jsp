<%@page import="model.PublishNote"%>
<%@ page language="java" import="java.util.*,vo.*,model.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  	<head>
    	<title>Note</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="/JQuery-Demo/JQuery/jquery.mobile-1.4.0.min.css" />
		<script src="/JQuery-Demo/JQuery/jquery-2.0.3.min.js" ></script>
		<script src="/JQuery-Demo/JQuery/jquery.mobile-1.4.0.js" ></script>
	</head>
  
  	<body>
    	<div data-role="page" data-theme="a" id="PageIndex">
			<div data-role="content">
				<%
				String NoteIdString = request.getParameter("NoteId");
				if (NoteIdString != null){
					Note note = (Note)PublishNote.GetById(Integer.valueOf(NoteIdString)).getObject();
					//图片
					String ImageFilePath = note.getImage_File_Path();
					if (ImageFilePath != null && !ImageFilePath.equals(""))
						out.println("<img width=\"100%\" alt=\"Loading\" src=\"./Image/" + ImageFilePath + ".jpg\">");
					out.println("<br /><hr />");
					//文字
					out.println(note.getContent());
					out.println("<br /><hr />");
					//人物
					out.println(Account.GetUserName(note.getUser_Id()) + "到此一游~<br />");
					//时间
					out.println(note.getDate());
					out.println("<br />");
					//位置
					out.println(Location.getLocationString(note.getLocation_Latitude(), note.getLocation_Longitude()));
					out.println("<br /><hr />");
				}
				%>
			</div>
		</div>
  	</body>
</html>
